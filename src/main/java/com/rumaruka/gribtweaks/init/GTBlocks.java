package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.block.SandLayersBlock;
import com.rumaruka.gribtweaks.common.block.StatueOBJ;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;

public class GTBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> statue_obj = BLOCKS.register("statue_obj", StatueOBJ::new);
    public static final RegistryObject<Block> sand_layer = BLOCKS.register("sand_layer", SandLayersBlock::new);

    public static void setup() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
