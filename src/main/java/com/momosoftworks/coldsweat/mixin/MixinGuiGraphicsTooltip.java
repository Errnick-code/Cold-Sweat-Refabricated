package com.momosoftworks.coldsweat.mixin;

import com.mojang.datafixers.util.Either;
import com.momosoftworks.coldsweat.core.event.EventBus;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterClientTooltipComponentFactoriesEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RenderTooltipEvent;
import com.momosoftworks.coldsweat.mixin_api.AbstractContainerScreenAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(GuiGraphicsExtractor.class)
public class MixinGuiGraphicsTooltip
{
    @Unique
    private static final ThreadLocal<ItemStack> coldsweat$tooltipStack = new ThreadLocal<>();

    @Unique
    private static final ThreadLocal<List<ClientTooltipComponent>> coldsweat$pendingComponents = new ThreadLocal<>();

    @Unique
    private static final ThreadLocal<List<Either<FormattedText, TooltipComponent>>> coldsweat$pendingElements = new ThreadLocal<>();

    @Inject(method = "setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V",
            at = @At("HEAD"))
    private void coldsweat$captureStack(Font font, ItemStack stack, int x, int y, CallbackInfo ci)
    {   coldsweat$pendingComponents.remove();
        coldsweat$pendingElements.remove();
        coldsweat$tooltipStack.set(stack);
    }

    @Inject(method = "setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;II)V",
            at = @At("HEAD"))
    private void coldsweat$clearPendingForTextTooltip(Font font, Component message, int x, int y, CallbackInfo ci)
    {   coldsweat$pendingComponents.remove();
        coldsweat$pendingElements.remove();
    }

    @Inject(method = "setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/resources/Identifier;)V",
            at = @At("HEAD"), cancellable = true)
    private void coldsweat$gatherTooltip(Font font, List<Component> lines, Optional<TooltipComponent> image, int x, int y,
                                         Identifier style, CallbackInfo ci)
    {
        coldsweat$pendingComponents.remove();
        coldsweat$pendingElements.remove();

        ItemStack stack = coldsweat$tooltipStack.get();
        coldsweat$tooltipStack.remove();
        if (stack == null || stack.isEmpty())
        {
            if (!(Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> screen)) return;
            Slot hoveredSlot = ((AbstractContainerScreenAccess) screen).coldsweat$getHoveredSlot();
            if (hoveredSlot == null) return;
            stack = hoveredSlot.getItem();
            if (stack.isEmpty()) return;
        }

        GuiGraphicsExtractor graphics = (GuiGraphicsExtractor) (Object) this;
        RenderTooltipEvent.Pre pre = new RenderTooltipEvent.Pre(stack, graphics, x, y);
        EventBus.post(pre);
        if (pre.isCanceled())
        {   ci.cancel();
            return;
        }

        List<Either<FormattedText, TooltipComponent>> elements = new ArrayList<>();
        for (Component line : lines)
        {   elements.add(Either.left(line));
        }
        image.ifPresent(tooltip -> elements.add(Either.right(tooltip)));

        EventBus.post(new RenderTooltipEvent.GatherComponents(stack, graphics, elements));

        coldsweat$pendingElements.set(elements);
        coldsweat$pendingComponents.set(coldsweat$buildComponents(elements));
    }

    @Unique
    private static List<ClientTooltipComponent> coldsweat$buildComponents(List<Either<FormattedText, TooltipComponent>> elements)
    {
        List<ClientTooltipComponent> components = new ArrayList<>();
        for (Either<FormattedText, TooltipComponent> element : elements)
        {
            ClientTooltipComponent component = element.map(
                    text -> new ClientTextTooltip(Language.getInstance().getVisualOrder(text)),
                    tooltip ->
                    {
                        ClientTooltipComponent custom = RegisterClientTooltipComponentFactoriesEvent.create(tooltip);
                        return custom != null ? custom : ClientTooltipComponent.create(tooltip);
                    });
            if (component != null)
            {   components.add(component);
            }
        }
        return components;
    }

    /**
     * Iceberg (and possibly other tooltip-reworking mods) rebuilds the component list from
     * {@code ItemStack.getTooltipLines(...)} at the HEAD of {@code tooltip}, which wipes every custom
     * component added by this mod. Since that injection always lands before the first vanilla instruction,
     * injecting at the first vanilla {@code List.size()} call of {@code tooltip} guarantees we run after it,
     * so the wiped components can be restored from the element list we gathered earlier.
     */
    @Inject(method = "tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0), require = 0)
    private void coldsweat$restoreWipedComponents(Font font, List<ClientTooltipComponent> components, int x, int y,
                                                   ClientTooltipPositioner positioner, Identifier style, CallbackInfo ci)
    {
        List<ClientTooltipComponent> produced = coldsweat$pendingComponents.get();
        List<Either<FormattedText, TooltipComponent>> elements = coldsweat$pendingElements.get();
        coldsweat$pendingComponents.remove();
        coldsweat$pendingElements.remove();

        if (produced == null || elements == null || produced.isEmpty())
        {   return;
        }
        // Untouched tooltip (identity check works because every component we produced is still the same instance)
        if (components.containsAll(produced))
        {   return;
        }
        List<ClientTooltipComponent> rebuilt = coldsweat$buildComponents(elements);
        if (rebuilt.isEmpty())
        {   return;
        }
        components.clear();
        components.addAll(rebuilt);
    }

    @ModifyVariable(method = "setTooltipForNextFrameInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Z)V",
                    at = @At("HEAD"), argsOnly = true)
    private List<ClientTooltipComponent> coldsweat$replaceComponents(List<ClientTooltipComponent> components)
    {
        List<ClientTooltipComponent> pending = coldsweat$pendingComponents.get();
        return pending == null ? components : pending;
    }
}
