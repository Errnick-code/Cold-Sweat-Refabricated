package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.command.BaseCommand;
import com.momosoftworks.coldsweat.common.command.argument.NicerEnumArgument;
import com.momosoftworks.coldsweat.common.command.argument.TempAttributeTraitArgument;
import com.momosoftworks.coldsweat.common.command.argument.TempModifierArgument;
import com.momosoftworks.coldsweat.common.command.argument.TempModifierTraitArgument;
import com.momosoftworks.coldsweat.common.command.argument.TemperatureTraitArgument;
import com.momosoftworks.coldsweat.common.command.impl.ColdSweatCommand;
import com.momosoftworks.coldsweat.common.command.impl.TempCommand;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import java.util.ArrayList;

public class ModCommands
{
    private static final ArrayList<BaseCommand> COMMANDS = new ArrayList<>();

    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            COMMANDS.add(new TempCommand("temperature", 2, true));
            COMMANDS.add(new TempCommand("temp", 2, true));
            COMMANDS.add(new ColdSweatCommand("coldsweat", 2, true));

            COMMANDS.forEach(command ->
            {
                if (command.isEnabled() && command.setExecution() != null)
                {   dispatcher.register(command.getBuilder());
                }
            });
        });
    }

    public static void registerArgumentTypes()
    {
        ArgumentTypeRegistry.registerArgumentType(ColdSweat.createKey("temp_modifier_trait"), TempModifierTraitArgument.class, new TempModifierTraitArgument.Info());
        ArgumentTypeRegistry.registerArgumentType(ColdSweat.createKey("temperature_trait"), TemperatureTraitArgument.class, new TemperatureTraitArgument.Info());
        ArgumentTypeRegistry.registerArgumentType(ColdSweat.createKey("temp_attribute_trait"), TempAttributeTraitArgument.class, new TempAttributeTraitArgument.Info());
        ArgumentTypeRegistry.registerArgumentType(ColdSweat.createKey("nicer_enum"), NicerEnumArgument.class, new NicerEnumArgument.Info());
        ArgumentTypeRegistry.registerArgumentType(ColdSweat.createKey("temp_modifier"), TempModifierArgument.class, new TempModifierArgument.Info());
    }
}