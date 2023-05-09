package com.rumaruka.gribtweaks;

import com.mojang.brigadier.CommandDispatcher;
import com.rumaruka.gribtweaks.common.command.GribTweaksWeather;
import com.rumaruka.gribtweaks.common.event.GTEvents;
import com.rumaruka.gribtweaks.config.GTConfig;

import com.rumaruka.gribtweaks.data.PrimiriveBrushRecipeGen;
import com.rumaruka.gribtweaks.common.event.SandstormHandler;
import com.rumaruka.gribtweaks.init.*;
import com.rumaruka.gribtweaks.net.NetworkHandler;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;


@Mod(MODID)
public class GribTweaks {
    public static final String MODID = "gribtweaks";
    public static boolean isModInstalled = false;
    public static boolean flag = false;
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public static Screen WarningScreen = new ConfirmScreen(b -> {
        if (b) {
            Util.getPlatform().openUri("https://drive.google.com/drive/folders/1BFNaJFeCNsbS814NRKGbZEHkYK93kko5?usp=share_link");
        } else {
            Minecraft.getInstance().stop();
        }
    }, Component.translatable("gribtweaks.rumaruka.failure.title"), Component.translatable("gribtweaks.rumaruka.failure.notfound"), Component.translatable("gribtweaks.rumaruka.openlink"), Component.translatable("menu.quit"));


    public GribTweaks() {
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        GTBlocks.setup();
        GTItems.setup();
        GTTiles.setup();
        REGISTRATE.registerEventListeners(modBus);
        modBus.addListener(this::setupCommon);
        GTRecipeType.register(modBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GTConfig.spec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        NetworkHandler.initialize();
        modBus.addListener(EventPriority.LOWEST, GribTweaks::gatherData);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public  void setupCommon(FMLCommonSetupEvent event) {
        GTEvents.registerCauldronInteractions();
        if (GTConfig.GENERAL.sandstormEnabled.get()) {
            MinecraftForge.EVENT_BUS.register(new SandstormHandler());
        }
    }



    private void doClientStuff(FMLClientSetupEvent event) {
        isModInstalled =/* ModList.get().isLoaded("avaritia") &&*/ ModList.get().isLoaded("gateways") && ModList.get().isLoaded("modestmining");

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegisterWarningEvent {
        @SubscribeEvent
        public static void checkMods(TickEvent.RenderTickEvent event) {
            if (!isModInstalled && !flag) {
                Minecraft.getInstance().setScreen(WarningScreen);
            }
        }

    }

    @Mod.EventBusSubscriber
    public static class ROMCommand {
        @SubscribeEvent
        public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

            GribTweaksWeather.register(dispatcher);

        }
    }
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(true,new PrimiriveBrushRecipeGen(gen));
    }
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }


}
