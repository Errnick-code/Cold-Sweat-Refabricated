package com.momosoftworks.coldsweat.core.advancement.trigger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.config.ConfigSettings;

import java.util.List;

public class TriggerHelper
{
    public static Either<Double, Pair<Double, Double>> getTempValueOrRange(JsonObject entry)
    {
        if (entry.has("value"))
        {
            double value = entry.get("value").getAsDouble();
            return Either.left(value);
        }
        else
        {
            JsonObject range = entry.getAsJsonObject("range");
            double below = Double.POSITIVE_INFINITY;
            double above = Double.NEGATIVE_INFINITY;

            if (range != null)
            {
                if (range.has("below"))
                {
                    try
                    {   below = range.get("below").getAsDouble();
                    }
                    catch (Exception e)
                    {
                        String builtinValue = range.get("below").getAsString();
                        if (builtinValue.equals("max_habitable"))
                            below = ConfigSettings.MAX_TEMP.get();
                        else if (builtinValue.equals("min_habitable"))
                            below = ConfigSettings.MIN_TEMP.get();
                    }
                }

                if (range.has("above"))
                {
                    try
                    {   above = range.get("above").getAsDouble();
                    }
                    catch (Exception e)
                    {
                        String builtinValue = range.get("above").getAsString();
                        if (builtinValue.equals("max_habitable"))
                            above = ConfigSettings.MAX_TEMP.get();
                        else if (builtinValue.equals("min_habitable"))
                            above = ConfigSettings.MIN_TEMP.get();
                    }
                }
            }

            return Either.right(new Pair<>(below, above));
        }
    }

    public static JsonArray serializeConditions(List<TempCondition> conditions)
    {
        JsonArray values = new JsonArray();

        for (TempCondition condition : conditions)
        {
            double above = condition.above();
            double below = condition.below();
            JsonObject value = new JsonObject();
            value.addProperty("type", condition.trait().getSerializedName());
            if (above == below)
            {   value.addProperty("value", above);
            }
            else
            {   JsonObject range = new JsonObject();
                range.addProperty("below", below);
                range.addProperty("above", above);
                value.add("range", range);
                values.add(value);
            }
        }
        return values;
    }

    record TempCondition(Temperature.Trait trait, double below, double above)
    {
        public static final Codec<TempCondition> CODEC = new Codec<>()
        {
            @Override
            public <T> DataResult<Pair<TempCondition, T>> decode(DynamicOps<T> ops, T input)
            {
                return ops.getMap(input).flatMap(map ->
                {
                    T traitElement = map.get("trait") != null ? map.get("trait") : map.get("type");
                    if (traitElement == null)
                    {   return DataResult.error(() -> "Missing temperature trait");
                    }
                    return Temperature.Trait.CODEC.parse(ops, traitElement).flatMap(trait ->
                    {
                        T valueElement = map.get("value");
                        if (valueElement != null)
                        {
                            return Codec.DOUBLE.parse(ops, valueElement)
                                    .map(value -> Pair.of(new TempCondition(trait, value, value), input));
                        }
                        T rangeElement = map.get("range");
                        if (rangeElement == null)
                        {   return DataResult.success(Pair.of(new TempCondition(trait, Double.MAX_VALUE, -Double.MAX_VALUE), input));
                        }
                        return ops.getMap(rangeElement).flatMap(range ->
                        {
                            DataResult<Double> below = parseBound(ops, range.get("below"), Double.MAX_VALUE);
                            DataResult<Double> above = parseBound(ops, range.get("above"), -Double.MAX_VALUE);
                            return below.flatMap(b -> above.map(a -> Pair.of(new TempCondition(trait, b, a), input)));
                        });
                    });
                });
            }

            @Override
            public <T> DataResult<T> encode(TempCondition input, DynamicOps<T> ops, T prefix)
            {
                var builder = ops.mapBuilder();
                builder.add("trait", input.trait(), Temperature.Trait.CODEC);
                if (input.below() == input.above())
                {   builder.add("value", input.below(), Codec.DOUBLE);
                }
                else
                {
                    if (input.below() == Double.NEGATIVE_INFINITY)
                    {   builder.add("below", "min_habitable", Codec.STRING);
                    }
                    else builder.add("below", input.below(), Codec.DOUBLE);
                    if (input.above() == Double.POSITIVE_INFINITY)
                    {   builder.add("above", "max_habitable", Codec.STRING);
                    }
                    else builder.add("above", input.above(), Codec.DOUBLE);
                }
                return builder.build(prefix);
            }
        };

        private static <T> DataResult<Double> parseBound(DynamicOps<T> ops, T element, double defaultValue)
        {
            if (element == null) return DataResult.success(defaultValue);
            DataResult<Double> numeric = Codec.DOUBLE.parse(ops, element);
            if (numeric.result().isPresent()) return numeric;
            return Codec.STRING.parse(ops, element).flatMap(name -> switch (name)
            {
                case "min_habitable" -> DataResult.success(Double.NEGATIVE_INFINITY);
                case "max_habitable" -> DataResult.success(Double.POSITIVE_INFINITY);
                default -> DataResult.error(() -> "Unknown temperature bound: " + name);
            });
        }

        private static double resolveBound(double bound)
        {
            if (bound == Double.NEGATIVE_INFINITY) return ConfigSettings.MIN_TEMP.get();
            if (bound == Double.POSITIVE_INFINITY) return ConfigSettings.MAX_TEMP.get();
            return bound;
        }

        public boolean matches(double value)
        {
            double below = resolveBound(this.below);
            double above = resolveBound(this.above);
            return below > above
                 ? value >= above && value <= below
                 : value >= above || value <= below;
        }
    }
}
