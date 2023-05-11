package com.rumaruka.gribtweaks.init;

import com.ncpbails.modestmining.item.ModTiers;
import com.ncpbails.modestmining.item.custom.tools.BrushItem;
import com.rumaruka.gribtweaks.common.items.*;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;


public class GTItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
   public static final RegistryObject<HopeCatalystItem> HOPE_CATALYST = ITEMS.register("hope_catalyst",()->new HopeCatalystItem(new Item.Properties()));
   public static final RegistryObject<Item> TEST_DAMAGE = ITEMS.register("test_damage",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> statue_obj = ITEMS.register("statue_obj", () -> new BlockItem(GTBlocks.statue_obj.get(), new Item.Properties()));
    public static final RegistryObject<Item> sand_layer = ITEMS.register("sand_layer", () -> new BlockItem(GTBlocks.sand_layer.get(), new Item.Properties()));
    public static final RegistryObject<Item> breake_bush = ITEMS.register("breake_bush", () -> new BlockItem(GTBlocks.breake_bush.get(), new Item.Properties()));
    public static final RegistryObject<BrushItem> primitive_brush = ITEMS.register("primitive_brush",()-> new BrushItem(0.0F, 0.0F,ModTiers.MOUNTAIN_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(10)));
    public static final RegistryObject<PrimitiveBrushItem> primitive = ITEMS.register("primitive", PrimitiveBrushItem::new);
    public static final RegistryObject<Item> sand_copper = ITEMS.register("sand_copper", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_gold = ITEMS.register("sand_gold", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_iron = ITEMS.register("sand_iron", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_lapis = ITEMS.register("sand_lapis", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_redstone = ITEMS.register("sand_redstone", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_zinc = ITEMS.register("sand_zinc", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_copper_nugget = ITEMS.register("sand_copper_nugget", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_gold_nugget = ITEMS.register("sand_gold_nugget", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_iron_nugget = ITEMS.register("sand_iron_nugget", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_zinc_nugget = ITEMS.register("sand_zinc_nugget", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_trough = ITEMS.register("sand_trough", ()->new SandTroughItem(new Item.Properties()));
    public static final RegistryObject<Item> sand_knife = ITEMS.register("sand_knife", ()->new Item(new Item.Properties().durability(1)));
    public static final RegistryObject<Item> sand_bucket = ITEMS.register("sand_bucket", ()->new SandBucketItem(Fluids.EMPTY,new Item.Properties()));
    public static final RegistryObject<Item> water_sand_bucket = ITEMS.register("water_sand_bucket", ()->new SandBucketItem(Fluids.WATER,new Item.Properties().stacksTo(1)));


    public static final RegistryObject<Item> wooden_bucket = ITEMS.register("wooden_bucket", ()->new WoodenBucketItem(Fluids.EMPTY,new Item.Properties()));
    public static final RegistryObject<Item> water_wooden_bucket = ITEMS.register("water_wooden_bucket", ()->new WoodenBucketItem(Fluids.WATER,new Item.Properties().stacksTo(1)));



    public static void setup() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

}
