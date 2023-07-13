package com.rumaruka.gribtweaks.init;


import com.rumaruka.gribtweaks.GribTweaks;
import com.rumaruka.gribtweaks.common.items.*;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;


public class GTItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<Item> ITEMS2 = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<HopeCatalystItem> HOPE_CATALYST = ITEMS.register("hope_catalyst", () -> new HopeCatalystItem(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<ForestCatalystItem> FOREST_CATALYST = ITEMS.register("forest_catalyst", () -> new ForestCatalystItem(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> DAMAGE_HOPE_CATALYST = ITEMS.register("damage_hope_catalyst", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> statue_obj = ITEMS.register("statue_obj", () -> new BlockItem(GTBlocks.statue_obj.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> compost = ITEMS.register("compost", () -> new BlockItem(GTBlocks.compost.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_layer = ITEMS.register("sand_layer", () -> new BlockItem(GTBlocks.sand_layer.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> breake_bush = ITEMS.register("breake_bush", () -> new BlockItem(GTBlocks.breake_bush.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> arch_block = ITEMS.register("arch_block", () -> new BlockItem(GTBlocks.arch_block.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<PrimitiveBrushItem> primitive = ITEMS.register("primitive", PrimitiveBrushItem::new);

    public static final RegistryObject<Item> sand_copper = ITEMS.register("sand_copper", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_gold = ITEMS.register("sand_gold", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_iron = ITEMS.register("sand_iron", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_lapis = ITEMS.register("sand_lapis", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_redstone = ITEMS.register("sand_redstone", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_zinc = ITEMS.register("sand_zinc", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_copper_nugget = ITEMS.register("sand_copper_nugget", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_gold_nugget = ITEMS.register("sand_gold_nugget", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_iron_nugget = ITEMS.register("sand_iron_nugget", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_zinc_nugget = ITEMS.register("sand_zinc_nugget", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_trough = ITEMS.register("sand_trough", () -> new SandTroughItem(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> sand_knife = ITEMS.register("sand_knife", () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS).durability(1)));
    public static final RegistryObject<Item> sand_bucket = ITEMS.register("sand_bucket",
            () -> new SandBucket(() -> Fluids.EMPTY, new Item.Properties().stacksTo(16).tab(GribTweaks.GRIBTWEAKS_TABS)));

    public static final RegistryObject<Item> water_sand_bucket = ITEMS.register("water_sand_bucket",
            () -> new SandBucket(() -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> wooden_bucket = ITEMS.register("wooden_bucket",
            () -> new WoodenBucket(() -> Fluids.EMPTY, new Item.Properties().stacksTo(16).tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> water_wooden_bucket = ITEMS.register("water_wooden_bucket",
            () -> new WoodenBucket(() -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> SANDY_BREEZE_EGGS = ITEMS.register("sandy_breeze_egg_spawn",()->new ForgeSpawnEggItem(GTEntity.SANDY_BREEZE ,0x22b341, 0x19732e,
            new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> SAND_BAD_EGGS = ITEMS.register("sand_bad_egg_spawn",()->new ForgeSpawnEggItem(GTEntity.SAND_BAD ,0x22b341, 0x19732e,
            new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));

    public static final RegistryObject<Item> PRIMITIVE_BRUSH = ITEMS.register("primitive_brush",
            () -> new BrushItem(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS).durability(8), true));
    public static final RegistryObject<Item> COPPER_BRUSH = ITEMS.register("copper_brush",
            () -> new BrushItem(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS).durability(32), false));

    public static final RegistryObject<Item> SAND_STONE_PIECES = ITEMS.register("sand_stone_pieces",
            () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> ANDESITE_PIECE = ITEMS.register("andesite_piece",
            () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> STONE_NUGGET = ITEMS.register("stone_nugget",
            () -> new Item(new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));

    public static final RegistryObject<Item> SUSPICIOUS_SAND = ITEMS.register("suspicious_sand",
            () -> new BlockItem(GTBlocks.SUSPICIOUS_SAND.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));
    public static final RegistryObject<Item> SAND = ITEMS2.register("sand2",
            () -> new BlockItem(GTBlocks.SAND.get(), new Item.Properties().tab(GribTweaks.GRIBTWEAKS_TABS)));


    public static void setup() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS2.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
