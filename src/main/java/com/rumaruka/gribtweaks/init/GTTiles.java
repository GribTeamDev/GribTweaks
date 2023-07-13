package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.tiles.ArchaeologicalGeneratorBlockEntity;
import com.rumaruka.gribtweaks.common.tiles.BrushableBlockEntity;
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
    public static final RegistryObject<BlockEntityType<ArchaeologicalGeneratorBlockEntity>> arch_block = TILES.register("arch_block", () -> BlockEntityType.Builder.of(ArchaeologicalGeneratorBlockEntity::new, GTBlocks.arch_block.get()).build(null));
    public static final RegistryObject<BlockEntityType<BrushableBlockEntity>> BRUSHABLE_BLOCK_ENTITY = TILES.register("brushable_block_entity",
            () -> BlockEntityType.Builder.of(BrushableBlockEntity::new, GTBlocks.SUSPICIOUS_SAND.get(), GTBlocks.SAND.get()).build(null));
    public static void setup() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }




}
