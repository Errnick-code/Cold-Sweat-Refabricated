package com.momosoftworks.coldsweat.data.tag;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ModDimensionTags
{
    public static final TagKey<DimensionType> SOUL_LAMP_VALID = createTag("soulspring_lamp_valid");

    public static final TagKey<DimensionType> HAS_CEILING = createForgeTag("has_ceiling");
    public static final TagKey<DimensionType> HAS_SKY = createForgeTag("has_sky");
    public static final TagKey<DimensionType> NATURAL = createForgeTag("natural");
    public static final TagKey<DimensionType> UNNATURAL = createForgeTag("unnatural");
    public static final TagKey<DimensionType> ULTRAWARM = createForgeTag("ultrawarm");
    public static final TagKey<DimensionType> BED_WORKS = createForgeTag("bed_works");
    public static final TagKey<DimensionType> RESPAWN_ANCHOR_WORKS = createForgeTag("respawn_anchor_works");
    public static final TagKey<DimensionType> PIGLIN_SAFE = createForgeTag("piglin_safe");
    public static final TagKey<DimensionType> HAS_SKYLIGHT = createForgeTag("has_skylight");
    public static final TagKey<DimensionType> HAS_RAIDS = createForgeTag("has_raids");
    public static final TagKey<DimensionType> OVERWORLD_LIKE = createForgeTag("overworld_like");

    private static TagKey<DimensionType> createTag(String name)
    {   return TagKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, name));
    }

    private static TagKey<DimensionType> createForgeTag(String name)
    {   return TagKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath("c", name));
    }

    private static final Map<TagKey<DimensionType>, Predicate<DimensionType>> DYNAMIC_TAGS = Map.ofEntries(
            Map.entry(HAS_CEILING, DimensionType::hasCeiling),
            Map.entry(HAS_SKY, dim -> !dim.hasCeiling()),
            Map.entry(NATURAL, ModDimensionTags::isNatural),
            Map.entry(UNNATURAL, dim -> !isNatural(dim)),
            Map.entry(ULTRAWARM, dim -> getAttribute(dim, EnvironmentAttributes.WATER_EVAPORATES)),
            Map.entry(BED_WORKS, dim -> !getAttribute(dim, EnvironmentAttributes.BED_RULE).explodes()),
            Map.entry(RESPAWN_ANCHOR_WORKS, dim -> getAttribute(dim, EnvironmentAttributes.RESPAWN_ANCHOR_WORKS)),
            Map.entry(PIGLIN_SAFE, dim -> !getAttribute(dim, EnvironmentAttributes.PIGLINS_ZOMBIFY)),
            Map.entry(HAS_SKYLIGHT, DimensionType::hasSkyLight),
            Map.entry(HAS_RAIDS, dim -> getAttribute(dim, EnvironmentAttributes.CAN_START_RAID)),
            Map.entry(OVERWORLD_LIKE, dim -> isNatural(dim) && !dim.hasCeiling() && dim.hasSkyLight()
                                             && !getAttribute(dim, EnvironmentAttributes.WATER_EVAPORATES))
    );

    private static boolean isNatural(DimensionType dimension)
    {   return dimension.hasSkyLight() && !dimension.hasCeiling() && !dimension.hasEnderDragonFight();
    }

    private static <V> V getAttribute(DimensionType dimension, EnvironmentAttribute<V> attribute)
    {   return dimension.attributes().applyModifier(attribute, attribute.defaultValue());
    }

    public static boolean isInTag(Holder<DimensionType> dimension, TagKey<DimensionType> tag)
    {
        if (dimension.is(tag)) return true;
        Predicate<DimensionType> predicate = DYNAMIC_TAGS.get(tag);
        return predicate != null && predicate.test(dimension.value());
    }

    public static List<Holder<DimensionType>> getTagContents(Registry<DimensionType> registry, TagKey<DimensionType> tag)
    {
        List<Holder<DimensionType>> contents = new ArrayList<>();
        registry.get(tag).ifPresent(holders -> holders.forEach(contents::add));
        Predicate<DimensionType> predicate = DYNAMIC_TAGS.get(tag);
        if (predicate != null)
        {
            for (Holder.Reference<DimensionType> dimension : registry.listElements().toList())
            {
                if (predicate.test(dimension.value()) && !contents.contains(dimension))
                {   contents.add(dimension);
                }
            }
        }
        return contents;
    }
}
