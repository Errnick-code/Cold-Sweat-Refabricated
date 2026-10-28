package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSounds
{
    public static final Ref<SoundEvent> FREEZE = sound("entity.player.damage.freeze");
    public static final Ref<SoundEvent> SOUL_LAMP_ON = sound("item.soulspring_lamp.on");
    public static final Ref<SoundEvent> SOUL_LAMP_OFF = sound("item.soulspring_lamp.off");
    public static final Ref<SoundEvent> WATERSKIN_POUR = sound("item.waterskin.pour");
    public static final Ref<SoundEvent> WATERSKIN_FILL = sound("item.waterskin.fill");
    public static final Ref<SoundEvent> HEARTH_DEPLETE = sound("block.hearth.fuel_deplete");
    public static final Ref<SoundEvent> BOILER_DEPLETE = sound("block.boiler.fuel_deplete");
    public static final Ref<SoundEvent> ICEBOX_DEPLETE = sound("block.icebox.fuel_deplete");
    public static final Ref<SoundEvent> ICEBOX_OPEN = sound("block.icebox.open");
    public static final Ref<SoundEvent> ICEBOX_CLOSE = sound("block.icebox.close");
    public static final Ref<SoundEvent> CHAMELEON_AMBIENT = sound("entity.chameleon.ambient");
    public static final Ref<SoundEvent> CHAMELEON_HURT = sound("entity.chameleon.hurt");
    public static final Ref<SoundEvent> CHAMELEON_DEATH = sound("entity.chameleon.death");
    public static final Ref<SoundEvent> CHAMELEON_FIND = sound("entity.chameleon.find");
    public static final Ref<SoundEvent> CHAMELEON_TONGUE_IN = sound("entity.chameleon.tongue.in");
    public static final Ref<SoundEvent> CHAMELEON_TONGUE_OUT = sound("entity.chameleon.tongue.out");
    public static final Ref<SoundEvent> CHAMELEON_SHED = sound("entity.chameleon.shed");
    public static final Ref<SoundEvent> CHAMELEON_SHED_READY = sound("entity.chameleon.shed.ready");
    public static final Ref<SoundEvent> CHAMELEON_SHED_FAIL = sound("entity.chameleon.shed.fail");
    public static final Ref<SoundEvent> ARMOR_EQUIP_CHAMELEON = sound("item.armor.equip_chameleon_scale");
    public static final Ref<SoundEvent> BUCKET_FILL_SLUSH = sound("item.bucket.fill_slush");
    public static final Ref<SoundEvent> BUCKET_EMPTY_SLUSH = sound("item.bucket.empty_slush");

    private static Ref<SoundEvent> sound(String name)
    {   return new Ref<>(Registry.register(BuiltInRegistries.SOUND_EVENT, ColdSweat.createKey(name),
                                           SoundEvent.createVariableRangeEvent(ColdSweat.createKey(name))));
    }
}
