package com.rumaruka.gribtweaks.init;

import com.ncpbails.modestmining.item.ModTiers;
import com.ncpbails.modestmining.item.custom.tools.BrushItem;
import com.rumaruka.gribtweaks.common.items.PrimitiveBrushItem;
import com.rumaruka.gribtweaks.common.items.SandBucketItem;
import com.rumaruka.gribtweaks.common.items.SandTroughItem;
import com.rumaruka.gribtweaks.common.items.TestItem;

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
   public static final RegistryObject<TestItem> TEST = ITEMS.register("test",()->new TestItem(new Item.Properties()));
   public static final RegistryObject<Item> TEST_DAMAGE = ITEMS.register("test_damage",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> statue_obj = ITEMS.register("statue_obj", () -> new BlockItem(GTBlocks.statue_obj.get(), new Item.Properties()));
    public static final RegistryObject<Item> sand_layer = ITEMS.register("sand_layer", () -> new BlockItem(GTBlocks.sand_layer.get(), new Item.Properties()));
    public static final RegistryObject<BrushItem> primitive_brush = ITEMS.register("primitive_brush",()-> new BrushItem(0.0F, 0.0F,ModTiers.MOUNTAIN_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(10)));
    public static final RegistryObject<PrimitiveBrushItem> primitive = ITEMS.register("primitive", PrimitiveBrushItem::new);
    public static final RegistryObject<Item> sand_copper = ITEMS.register("sand_copper", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_trough = ITEMS.register("sand_trough", ()->new SandTroughItem(new Item.Properties()));
    public static final RegistryObject<Item> sand_knife = ITEMS.register("sand_knife", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> sand_bucket = ITEMS.register("sand_bucket", ()->new SandBucketItem(Fluids.EMPTY,new Item.Properties()));
    public static final RegistryObject<Item> water_sand_bucket = ITEMS.register("water_sand_bucket", ()->new SandBucketItem(Fluids.WATER,new Item.Properties()));





    public static void setup() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

}
