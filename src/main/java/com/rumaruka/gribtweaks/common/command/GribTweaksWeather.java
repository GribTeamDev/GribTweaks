package com.rumaruka.gribtweaks.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.rumaruka.gribtweaks.common.event.SandstormHandler;
import com.rumaruka.gribtweaks.util.DimensionHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Random;

public class GribTweaksWeather {

    private static boolean isSandstorm;
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gribweather").requires((p) -> p.hasPermission(1)).executes(c -> sendUsage(c.getSource()))
                .then(Commands.literal("clear").executes(c -> execute(c.getSource(), false, 0)).then(Commands.argument("time", IntegerArgumentType.integer(-1)).executes(c -> execute(c.getSource(), false, IntegerArgumentType.getInteger(c, "time")))))
                .then(Commands.literal("sandstorm").executes(c -> execute(c.getSource(), true, 0)).then(Commands.argument("time", IntegerArgumentType.integer(-1)).executes(c -> execute(c.getSource(), true, IntegerArgumentType.getInteger(c, "time"))))));
    }

    private static int sendUsage(CommandSourceStack source) {
        source.sendSuccess(Component.translatable("atum.commands.weather.usage"), true);
        return 0;
    }

    private static int execute(CommandSourceStack source, boolean isSand, int time) {
        ServerLevel serverLevel = source.getLevel();
        if (serverLevel.dimension()== Level.OVERWORLD) {
           // isSand = isSandstorm = serverLevel.isRaining() || serverLevel.isThundering();
            DimensionHelper.getData(serverLevel).setStorming(isSand);
            SandstormHandler.INSTANCE.stormTime = time == -1 ? 1500 : time != 0 ? Math.min(time, 1000000) * 20 : (300 + (new Random()).nextInt(600)) * 20;
            if (isSand) {
                source.sendSuccess(Component.translatable("atum.commands.weather.sandstorm"), true);
            } else {
                source.sendSuccess(Component.translatable("commands.weather.set.clear"), true);
            }
            return 0;
        }
        return 0;
    }

}
