package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.block.CompostWaterBlock;
import com.rumaruka.gribtweaks.common.block.SandLayersBlock;
import com.rumaruka.gribtweaks.common.block.StatueOBJ;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
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

    public static final RegistryObject<Block> statue_obj = BLOCKS.register("statue_obj", StatueOBJ::new);
    public static final RegistryObject<Block> sand_layer = BLOCKS.register("sand_layer", SandLayersBlock::new);
    public static final RegistryObject<Block> compost = BLOCKS.register("compost",()-> new CompostWaterBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()));
    public static final RegistryObject<Block> compost_water = BLOCKS.register("compost_water",()-> new LayeredCauldronBlock(BlockBehaviour.Properties.copy(GTBlocks.compost.get()), LayeredCauldronBlock.RAIN, CauldronInteraction.WATER));

    public static void setup() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
