package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.block.*;

import com.rumaruka.gribtweaks.common.block.watercompost.LayeredWaterCompost;
import com.rumaruka.gribtweaks.common.block.watercompost.WaterCompost;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;

public class GTBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Block> BLOCKS2 = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> statue_obj = BLOCKS.register("statue_obj", StatueOBJ::new);
    public static final RegistryObject<Block> sand_layer = BLOCKS.register("sand_layer", SandLayersBlock::new);
    public static final RegistryObject<Block> compost = BLOCKS.register("compost",()-> new WaterCompost(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()));
    public static final RegistryObject<Block> compost_water = BLOCKS.register("compost_water",()-> new LayeredWaterCompost(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(2.0F).noOcclusion(), LayeredCauldronBlock.RAIN));

    public static final RegistryObject<Block> breake_bush = BLOCKS.register("breake_bush",
            ()-> new BreakeDeadBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));

    public static final RegistryObject<Block> arch_block = BLOCKS.register("arch_block", ArchaeologicalGeneratorBlock::new);
    public static final RegistryObject<Block> SUSPICIOUS_SAND = BLOCKS.register("suspicious_sand",
            ()-> new BrushableBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND = BLOCKS.register("sand2",
            ()-> new BrushableBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static void setup() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS2.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
