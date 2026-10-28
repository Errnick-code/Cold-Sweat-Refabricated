package com.momosoftworks.coldsweat.config.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TomlCodec
{
    private TomlCodec() {}

    public static CommentedConfig parse(Path file) throws IOException
    {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        return parseLines(lines);
    }

    public static CommentedConfig parseLines(List<String> lines)
    {
        CommentedConfig root = new CommentedConfig();
        CommentedConfig current = root;
        StringBuilder pendingComment = new StringBuilder();

        for (int i = 0; i < lines.size(); i++)
        {
            String line = stripTrailingComment(lines.get(i)).trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("#"))
            {
                if (pendingComment.length() > 0)
                {
                    pendingComment.append('\n');
                }
                pendingComment.append(line.substring(1).trim());
                continue;
            }
            if (line.startsWith("["))
            {
                List<String> path = parseTableHeader(line);
                current = resolveTable(root, path);
                current.setComment(lastKey(path), pendingComment.toString());
                pendingComment.setLength(0);
                continue;
            }

            int equals = findAssignment(line);
            if (equals < 0)
            {
                continue;
            }

            String rawKey = line.substring(0, equals).trim();
            String rawValue = line.substring(equals + 1).trim();
            List<String> keyPath = parseKeyPath(rawKey);

            StringBuilder valueBuilder = new StringBuilder(rawValue);
            while (!isBalanced(valueBuilder.toString()) && i + 1 < lines.size())
            {
                i++;
                valueBuilder.append(' ').append(lines.get(i).trim());
            }

            current.set(keyPath, parseValue(valueBuilder.toString()));
            current.setComment(lastKey(keyPath), pendingComment.toString());
            pendingComment.setLength(0);
        }
        return root;
    }

    public static void write(CommentedConfig config, Path file) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        writeTable(builder, config, List.of());
        Path parent = file.getParent();
        if (parent != null)
        {
            Files.createDirectories(parent);
        }
        Files.writeString(file, builder.toString(), StandardCharsets.UTF_8);
    }

    private static void writeTable(StringBuilder builder, CommentedConfig config, List<String> parentPath)
    {
        Map<String, Object> values = config.valueMap();
        for (Map.Entry<String, Object> entry : values.entrySet())
        {
            Object value = entry.getValue();
            if (value instanceof CommentedConfig || value instanceof Config)
            {
                continue;
            }
            appendComment(builder, config.getComment(entry.getKey()));
            builder.append(formatKey(entry.getKey())).append(" = ").append(formatValue(value, config.getComment(entry.getKey()))).append('\n');
        }

        for (Map.Entry<String, Object> entry : values.entrySet())
        {
            Object value = entry.getValue();
            if (!(value instanceof Config child) || !(child instanceof CommentedConfig commented))
            {
                continue;
            }
            List<String> path = new ArrayList<>(parentPath);
            path.add(entry.getKey());

            builder.append('\n');
            appendComment(builder, config.getComment(entry.getKey()));
            builder.append('[').append(formatPath(path)).append("]\n");
            writeTable(builder, commented, path);
        }
    }

    private static void appendComment(StringBuilder builder, String comment)
    {
        if (comment == null || comment.isEmpty())
        {
            return;
        }
        for (String line : comment.split("\n"))
        {
            builder.append('#').append(line).append('\n');
        }
    }

    private static String formatValue(Object value, String comment)
    {
        if (value instanceof List<?> list)
        {
            boolean vertical = comment != null && comment.contains("//v");
            return formatList(list, vertical);
        }
        if (value instanceof Boolean bool)
        {
            return bool ? "true" : "false";
        }
        if (value instanceof Double || value instanceof Float)
        {
            double number = ((Number) value).doubleValue();
            if (Double.isNaN(number))
            {
                return "\"NaN\"";
            }
            if (Double.isInfinite(number))
            {
                return number > 0 ? "\"inf\"" : "\"-inf\"";
            }
            String text = String.valueOf(number);
            return text.contains("E") || text.contains(".") ? text : text + ".0";
        }
        if (value instanceof Number)
        {
            return String.valueOf(value);
        }
        return formatString(String.valueOf(value));
    }

    private static String formatList(List<?> list, boolean vertical)
    {
        if (list.isEmpty())
        {
            return "[]";
        }
        if (!vertical)
        {
            StringBuilder builder = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
                builder.append(formatValue(list.get(i), null));
            }
            return builder.append(']').toString();
        }
        StringBuilder builder = new StringBuilder("[\n");
        for (int i = 0; i < list.size(); i++)
        {
            builder.append("    ").append(formatValue(list.get(i), null));
            builder.append(i < list.size() - 1 ? ",\n" : "\n");
        }
        return builder.append(']').toString();
    }

    private static String formatString(String value)
    {
        StringBuilder builder = new StringBuilder("\"");
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            switch (c)
            {
                case '"' -> builder.append("\\\"");
                case '\\' -> builder.append("\\\\");
                case '\n' -> builder.append("\\n");
                case '\r' -> builder.append("\\r");
                case '\t' -> builder.append("\\t");
                default -> builder.append(c);
            }
        }
        return builder.append('"').toString();
    }

    private static String formatKey(String key)
    {
        if (!key.isEmpty() && key.chars().allMatch(c -> Character.isLetterOrDigit(c) || c == '_' || c == '-'))
        {
            return key;
        }
        return formatString(key);
    }

    private static String formatPath(List<String> path)
    {
        List<String> formatted = new ArrayList<>();
        for (String part : path)
        {
            formatted.add(formatKey(part));
        }
        return String.join(".", formatted);
    }

    private static CommentedConfig resolveTable(CommentedConfig root, List<String> path)
    {
        CommentedConfig current = root;
        for (String key : path)
        {
            Object child = current.valueMap().get(key);
            if (child instanceof CommentedConfig commented)
            {
                current = commented;
            }
            else if (child instanceof Config)
            {
                CommentedConfig replacement = current.createSubCommentedConfig();
                current.valueMap().put(key, replacement);
                current = replacement;
            }
            else
            {
                CommentedConfig created = current.createSubCommentedConfig();
                current.valueMap().put(key, created);
                current = created;
            }
        }
        return current;
    }

    private static List<String> parseTableHeader(String line)
    {
        String content = line.trim();
        if (content.startsWith("[["))
        {
            content = content.substring(2);
        }
        else
        {
            content = content.substring(1);
        }
        if (content.endsWith("]]"))
        {
            content = content.substring(0, content.length() - 2);
        }
        else if (content.endsWith("]"))
        {
            content = content.substring(0, content.length() - 1);
        }
        return parseKeyPath(content.trim());
    }

    private static List<String> parseKeyPath(String raw)
    {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inString = false;
        char quote = 0;
        for (int i = 0; i < raw.length(); i++)
        {
            char c = raw.charAt(i);
            if (inString)
            {
                if (c == quote)
                {
                    inString = false;
                }
                else if (c == '\\' && quote == '"')
                {
                    current.append(unescape(raw.charAt(++i)));
                }
                else
                {
                    current.append(c);
                }
                continue;
            }
            if (c == '"' || c == '\'')
            {
                inString = true;
                quote = c;
                continue;
            }
            if (c == '.')
            {
                parts.add(current.toString().trim());
                current.setLength(0);
                continue;
            }
            current.append(c);
        }
        parts.add(current.toString().trim());
        parts.removeIf(String::isEmpty);
        return parts;
    }

    private static char unescape(char c)
    {
        return switch (c)
        {
            case 'n' -> '\n';
            case 'r' -> '\r';
            case 't' -> '\t';
            default -> c;
        };
    }

    private static String lastKey(List<String> path)
    {
        return path.isEmpty() ? "" : path.get(path.size() - 1);
    }

    private static int findAssignment(String line)
    {
        boolean inString = false;
        char quote = 0;
        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if (inString)
            {
                if (c == '\\')
                {
                    i++;
                }
                else if (c == quote)
                {
                    inString = false;
                }
                continue;
            }
            if (c == '"' || c == '\'')
            {
                inString = true;
                quote = c;
                continue;
            }
            if (c == '=')
            {
                return i;
            }
        }
        return -1;
    }

    private static String stripTrailingComment(String line)
    {
        boolean inString = false;
        char quote = 0;
        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if (inString)
            {
                if (c == '\\' && quote == '"')
                {
                    i++;
                }
                else if (c == quote)
                {
                    inString = false;
                }
                continue;
            }
            if (c == '"' || c == '\'')
            {
                inString = true;
                quote = c;
                continue;
            }
            if (c == '#')
            {
                return line.substring(0, i);
            }
        }
        return line;
    }

    private static boolean isBalanced(String text)
    {
        int depth = 0;
        boolean inString = false;
        char quote = 0;
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (inString)
            {
                if (c == '\\' && quote == '"')
                {
                    i++;
                }
                else if (c == quote)
                {
                    inString = false;
                }
                continue;
            }
            if (c == '"' || c == '\'')
            {
                inString = true;
                quote = c;
                continue;
            }
            if (c == '[')
            {
                depth++;
            }
            else if (c == ']')
            {
                depth--;
            }
        }
        return depth <= 0;
    }

    private static Object parseValue(String raw)
    {
        String value = raw.trim();
        if (value.isEmpty())
        {
            return "";
        }
        if (value.startsWith("\""))
        {
            return parseBasicString(value);
        }
        if (value.startsWith("'"))
        {
            int end = value.indexOf('\'', 1);
            return end < 0 ? value.substring(1) : value.substring(1, end);
        }
        if (value.startsWith("["))
        {
            return parseList(value);
        }
        if (value.equals("true"))
        {
            return Boolean.TRUE;
        }
        if (value.equals("false"))
        {
            return Boolean.FALSE;
        }
        return parseNumber(value);
    }

    private static String parseBasicString(String value)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < value.length(); i++)
        {
            char c = value.charAt(i);
            if (c == '\\' && i + 1 < value.length())
            {
                char next = value.charAt(++i);
                builder.append(switch (next)
                {
                    case 'n' -> '\n';
                    case 'r' -> '\r';
                    case 't' -> '\t';
                    case '"' -> '"';
                    case '\\' -> '\\';
                    default -> next;
                });
                continue;
            }
            if (c == '"')
            {
                break;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private static List<Object> parseList(String value)
    {
        List<Object> result = new ArrayList<>();
        String inner = value.trim();
        if (inner.startsWith("["))
        {
            inner = inner.substring(1);
        }
        if (inner.endsWith("]"))
        {
            inner = inner.substring(0, inner.length() - 1);
        }

        List<String> elements = splitTopLevel(inner);
        for (String element : elements)
        {
            String trimmed = element.trim();
            if (trimmed.isEmpty())
            {
                continue;
            }
            result.add(parseValue(trimmed));
        }
        return result;
    }

    private static List<String> splitTopLevel(String inner)
    {
        List<String> elements = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inString = false;
        char quote = 0;
        for (int i = 0; i < inner.length(); i++)
        {
            char c = inner.charAt(i);
            if (inString)
            {
                current.append(c);
                if (c == '\\' && quote == '"' && i + 1 < inner.length())
                {
                    current.append(inner.charAt(++i));
                }
                else if (c == quote)
                {
                    inString = false;
                }
                continue;
            }
            if (c == '"' || c == '\'')
            {
                inString = true;
                quote = c;
                current.append(c);
                continue;
            }
            if (c == '[')
            {
                depth++;
                current.append(c);
                continue;
            }
            if (c == ']')
            {
                depth--;
                current.append(c);
                continue;
            }
            if (c == ',' && depth == 0)
            {
                elements.add(current.toString());
                current.setLength(0);
                continue;
            }
            current.append(c);
        }
        if (!current.toString().trim().isEmpty())
        {
            elements.add(current.toString());
        }
        return elements;
    }

    private static Object parseNumber(String value)
    {
        String cleaned = value.replace("_", "").trim();
        if (cleaned.isEmpty())
        {
            return 0;
        }
        try
        {
            if (cleaned.indexOf('.') < 0 && cleaned.indexOf('e') < 0 && cleaned.indexOf('E') < 0)
            {
                long parsed = Long.parseLong(cleaned);
                if (parsed >= Integer.MIN_VALUE && parsed <= Integer.MAX_VALUE)
                {
                    return (int) parsed;
                }
                return parsed;
            }
            return Double.parseDouble(cleaned);
        }
        catch (NumberFormatException e)
        {
            return new LinkedHashMap<String, Object>();
        }
    }
}