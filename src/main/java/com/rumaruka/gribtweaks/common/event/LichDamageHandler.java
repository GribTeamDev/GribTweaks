package com.rumaruka.gribtweaks.common.event;


import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sorcerium.entity.*;
import twilightforest.entity.boss.Lich;

@Mod.EventBusSubscriber
public class LichDamageHandler {

    @SubscribeEvent
    public static void onLichHurt(LivingHurtEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (event.getEntity() instanceof Lich) {
            if (!(directEntity instanceof SageGuardianWandEntity ||
                    directEntity instanceof IronWandEntity ||
                    directEntity instanceof GoldenWandEntity ||
                    directEntity instanceof SlimeStaffEntity ||
                    directEntity instanceof BotanistsStaffEntity ||
                    directEntity instanceof EarthStaffEntity ||
                    directEntity instanceof CopperWandEntity ||
                    directEntity instanceof SparkStaffEntity ||
                    directEntity instanceof WaterStaffEntity ||
                    directEntity instanceof AbyssWandEntity ||
                    directEntity instanceof FireStaffEntity ||
                    directEntity instanceof MoltenTridentEntity ||
                    directEntity instanceof BlazingStaffEntity ||
                    directEntity instanceof VulcanoWandEntity ||
                    directEntity instanceof AscentWandEntity ||
                    directEntity instanceof ChainStaffEntity ||
                    directEntity instanceof IceTridentEntity ||
                    directEntity instanceof FrostWandEntity ||
                    directEntity instanceof IceStaffEntity ||
                    directEntity instanceof StarlightWandEntity ||
                    directEntity instanceof DemonsEyeWandEntity ||
                    directEntity instanceof SoulHunterStaffEntity ||
                    directEntity instanceof HowlingAuroraWandEntity ||
                    directEntity instanceof BoguslavTheSorcererEntity) || !(event.getEntity().isOnFire())) {
                event.setCanceled(true);
            }
        }

    }
}
