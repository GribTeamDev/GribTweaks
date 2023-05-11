package com.rumaruka.gribtweaks.client;

import com.rumaruka.gribtweaks.GribTweaks;
import com.rumaruka.gribtweaks.client.render.StatueTESR;
import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.init.GTTiles;
import com.rumaruka.gribtweaks.common.items.HopeCatalystItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import shadows.gateways.gate.Failure;
import shadows.gateways.gate.Gateway;
import shadows.gateways.gate.Reward;
import shadows.gateways.gate.WaveEntity;
import shadows.placebo.PlaceboClient;
import shadows.placebo.json.RandomAttributeModifier;
import shadows.placebo.util.AttributeHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = MODID)
public class GTClient {
    private static int waveIdx = 0;
    private static ItemStack currentTooltipItem;
    private static long tooltipTick;
    static RandomSource rand = RandomSource.create();
    private static final ResourceLocation MUMMY_BLUR_TEXTURE = new ResourceLocation(GribTweaks.MODID, "textures/hud/mummyblur.png");

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent e) {
        registerBlockEntityRenderers();
        MinecraftForge.EVENT_BUS.addListener(GTClient::tooltip);
        MinecraftForge.EVENT_BUS.addListener(GTClient::scroll);
        MinecraftForge.EVENT_BUS.addListener(GTClient::scroll2);
    }

    public static void tooltip(ItemTooltipEvent e) {
        currentTooltipItem = e.getItemStack();
        tooltipTick = PlaceboClient.ticks;
        if (e.getItemStack().getItem() == GTItems.HOPE_CATALYST.get()) {
            Gateway gate = HopeCatalystItem.getGate(e.getItemStack());
            List<Component> tooltips = e.getToolTip();
            if (gate == null) {
                tooltips.add(Component.literal("Errored Gate Pearl, file a bug report detailing how you obtained this."));
                return;
            }

            Component comp;// = Component.translatable("tooltip.gateways.max_waves", gate.getNumWaves()).withStyle(ChatFormatting.GRAY);
            //tooltips.add(comp);

            if (Screen.hasShiftDown()) {
                waveIdx = Math.floorMod(waveIdx, gate.getNumWaves());

                int wave = waveIdx;
                Component sub = Component.translatable("tooltip.gateways.scroll").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY).withItalic(true).withUnderlined(false));
                comp = Component.translatable("tooltip.gateways.wave", wave + 1, gate.getNumWaves(), sub).withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE);
                tooltips.add(comp);
                tooltips.add(Component.nullToEmpty(null));
                comp = Component.translatable("tooltip.gateways.entities").withStyle(ChatFormatting.BLUE);
                tooltips.add(comp);
                Map<String, Integer> counts = new HashMap<>();
                for (WaveEntity entity : gate.getWave(wave).entities()) {
                    counts.put(entity.getDescription().getString(), counts.getOrDefault(entity.getDescription().getString(), 0) + 1);
                }
                for (Map.Entry<String, Integer> counted : counts.entrySet()) {
                    comp = Component.translatable("tooltip.gateways.list1", counted.getValue(), Component.translatable(counted.getKey())).withStyle(ChatFormatting.BLUE);
                    tooltips.add(comp);
                }
                if (!gate.getWave(wave).modifiers().isEmpty()) {
                    comp = Component.translatable("tooltip.gateways.modifiers").withStyle(ChatFormatting.RED);
                    tooltips.add(comp);
                    for (RandomAttributeModifier inst : gate.getWave(wave).modifiers()) {
                        comp = AttributeHelper.toComponent(inst.getAttribute(), inst.genModifier(rand));
                        comp = Component.translatable("tooltip.gateways.list2", comp.getString()).withStyle(ChatFormatting.RED);
                        tooltips.add(comp);
                    }
                }
                comp = Component.translatable("tooltip.gateways.rewards").withStyle(ChatFormatting.GOLD);
                tooltips.add(comp);
                for (Reward r : gate.getWave(wave).rewards()) {
                    r.appendHoverText(c -> {
                        tooltips.add(Component.translatable("tooltip.gateways.list2", c).withStyle(ChatFormatting.GOLD));
                    });
                }
            } else {
                comp = Component.translatable("tooltip.gateways.shift").withStyle(ChatFormatting.GRAY);
                tooltips.add(comp);
            }
            if (Screen.hasControlDown()) {
                comp = Component.translatable("tooltip.gateways.completion").withStyle(Style.EMPTY.withColor(0xFCFF00).withUnderlined(true));
                tooltips.add(comp);
                tooltips.add(Component.nullToEmpty(null));
                comp = Component.translatable("tooltip.gateways.experience", gate.getCompletionXp()).withStyle(Style.EMPTY.withColor(0xFCFF00));
                tooltips.add(comp);
                for (Reward r : gate.getRewards()) {
                    r.appendHoverText(c -> {
                        tooltips.add(Component.translatable("tooltip.gateways.list2", c).withStyle(Style.EMPTY.withColor(0xFCFF00)));
                    });
                }
            } else {
                comp = Component.translatable("tooltip.gateways.ctrl").withStyle(ChatFormatting.GRAY);
                tooltips.add(comp);
            }
            if (Screen.hasAltDown()) {
                comp = Component.translatable("tooltip.gateways.failure").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED).withUnderlined(true));
                tooltips.add(comp);
                tooltips.add(Component.nullToEmpty(null));
                for (Failure f : gate.getFailures()) {
                    f.appendHoverText(c -> {
                        tooltips.add(Component.translatable("tooltip.gateways.list2", c).withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED)));
                    });
                }
            } else {
                comp = Component.translatable("tooltip.gateways.alt").withStyle(ChatFormatting.GRAY);
                tooltips.add(comp);
            }
        }
    }

    public static void scroll(ScreenEvent.MouseScrolled.Pre e) {
        if (currentTooltipItem!=null&&(currentTooltipItem.getItem() == GTItems.HOPE_CATALYST.get()) && tooltipTick == PlaceboClient.ticks && Screen.hasShiftDown()) {
            waveIdx += e.getScrollDelta() < 0 ? 1 : -1;
            e.setCanceled(true);
        } else {
            waveIdx = 0;
        }
    }

    public static void scroll2(InputEvent.MouseScrollingEvent e) {
         if (currentTooltipItem!=null&&(currentTooltipItem.getItem() == GTItems.HOPE_CATALYST.get()) && tooltipTick == PlaceboClient.ticks && Screen.hasShiftDown()) {
            waveIdx += e.getScrollDelta() < 0 ? 1 : -1;
            e.setCanceled(true);
        } else {
            waveIdx = 0;
        }
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(GTTiles.statue_obj.get(), StatueTESR::new);

    }

}
