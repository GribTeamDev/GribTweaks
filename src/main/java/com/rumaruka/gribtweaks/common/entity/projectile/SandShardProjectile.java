package com.rumaruka.gribtweaks.common.entity.projectile;

import com.rumaruka.gribtweaks.init.GTEntity;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;


public class SandShardProjectile extends ThrowableProjectile {


    public SandShardProjectile(EntityType<? extends SandShardProjectile> type, Level world) {
        super(type, world);
    }

    public SandShardProjectile(double x, double y, double z, Level world) {
        super(GTEntity.SAND_SHARD.get(), x, y, z, world);
    }

    public SandShardProjectile(LivingEntity entity, Level world) {
        super(GTEntity.SAND_SHARD.get(), entity, world);
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity owner = this.getOwner();
        Entity entityHit = result.getEntity();
        if (entityHit instanceof LivingEntity living && (owner == null || (entityHit != owner && entityHit != owner.getVehicle()))) {
            if (entityHit.hurt(DamageSource.GENERIC, 2)
                    && this.getLevel().getDifficulty() != Difficulty.PEACEFUL) {
                int poisonTime = this.getLevel().getDifficulty() == Difficulty.HARD ? 7 : 3;
                living.addEffect(new MobEffectInstance(MobEffects.POISON, poisonTime * 20, 0));
            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    protected float getGravity() {
        return 0.003F;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.getLevel().isClientSide()) {
            this.getLevel().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {

        super.handleEntityEvent(id);

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}
