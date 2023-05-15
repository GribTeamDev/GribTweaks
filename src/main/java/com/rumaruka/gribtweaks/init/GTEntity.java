package com.rumaruka.gribtweaks.init;

import com.rumaruka.gribtweaks.common.entity.BadSand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.gribtweaks.GribTweaks.MODID;
@Mod.EventBusSubscriber
public class GTEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<BadSand>> SAND_BAD = ENTITY_TYPES.register("sand_bad",
            () -> EntityType.Builder.of(BadSand::new, MobCategory.MONSTER).fireImmune().sized(3.0F, 3.0F).clientTrackingRange(10).build("sand_bad"));
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SAND_BAD.get(),BadSand.createAttributes().build());
    }
}
