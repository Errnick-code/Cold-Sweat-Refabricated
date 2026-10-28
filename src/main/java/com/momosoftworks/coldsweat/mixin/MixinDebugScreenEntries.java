package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.client.debug.HearthDebugEntry;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DebugScreenEntries.class)
public class MixinDebugScreenEntries
{
    @Inject(method = "allEntries", at = @At("RETURN"), cancellable = true)
    private static void coldsweat$addHearthEntry(CallbackInfoReturnable<Map<Identifier, DebugScreenEntry>> cir)
    {
        Map<Identifier, DebugScreenEntry> entries = new HashMap<>(cir.getReturnValue());
        entries.put(HearthDebugEntry.ID, HearthDebugEntry.INSTANCE);
        cir.setReturnValue(entries);
    }

    @Inject(method = "getEntry", at = @At("HEAD"), cancellable = true)
    private static void coldsweat$getHearthEntry(Identifier id, CallbackInfoReturnable<DebugScreenEntry> cir)
    {
        if (HearthDebugEntry.ID.equals(id))
        {   cir.setReturnValue(HearthDebugEntry.INSTANCE);
        }
    }
}
