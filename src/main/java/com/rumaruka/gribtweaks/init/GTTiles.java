package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.tiles.StatueOBJBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;

public class GTTiles {
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final RegistryObject<BlockEntityType<StatueOBJBlockEntity>> statue_obj = TILES.register("statue_obj", () -> BlockEntityType.Builder.of(StatueOBJBlockEntity::new, GTBlocks.statue_obj.get()).build(null));
    public static void setup() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }




}
