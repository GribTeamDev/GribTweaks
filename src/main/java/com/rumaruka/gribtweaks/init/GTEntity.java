package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.entity.BadSand;
import com.rumaruka.gribtweaks.common.entity.SandyBreeze;
import com.rumaruka.gribtweaks.common.entity.projectile.SandShardProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;
import static com.rumaruka.gribtweaks.GribTweaks.rl;

@Mod.EventBusSubscriber
public class GTEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<BadSand>> SAND_BAD = ENTITY_TYPES.register("bad_sand",
            () -> EntityType.Builder.of(BadSand::new, MobCategory.MONSTER).sized(3.0F, 0.5F).clientTrackingRange(10).build(rl("bad_sand").toString()));
    public static final RegistryObject<EntityType<SandyBreeze>> SANDY_BREEZE = ENTITY_TYPES.register("sandy_breeze",
            () -> EntityType.Builder.of(SandyBreeze::new, MobCategory.MONSTER).sized(3.0F, 0.5F).clientTrackingRange(10).build(rl("sandy_breeze").toString()));
    public static final RegistryObject<EntityType<SandShardProjectile>> SAND_SHARD = ENTITY_TYPES.register("sand_shard",
            () -> EntityType.Builder.<SandShardProjectile>of(SandShardProjectile::new, MobCategory.MISC).sized(3.0F, 3.0F).clientTrackingRange(10).build(rl("sand_shard").toString()));



    public static void setup(){
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
