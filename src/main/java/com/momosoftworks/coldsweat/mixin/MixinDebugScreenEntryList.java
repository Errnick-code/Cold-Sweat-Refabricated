package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.client.debug.HearthDebugEntry;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DebugScreenEntryList.class)
public class MixinDebugScreenEntryList
{
    @Shadow @Final private Map<Identifier, DebugScreenEntryStatus> allStatuses;

    @Inject(method = "load", at = @At("RETURN"))
    private void coldsweat$defaultHearthStatus(CallbackInfo ci)
    {
        if (!this.allStatuses.containsKey(HearthDebugEntry.ID))
        {
            this.allStatuses.put(HearthDebugEntry.ID, DebugScreenEntryStatus.IN_OVERLAY);
            ((DebugScreenEntryList) (Object) this).rebuildCurrentList();
        }
    }
}
