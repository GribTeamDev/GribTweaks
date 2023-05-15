package com.rumaruka.gribtweaks.common.entity;

import com.rumaruka.gribtweaks.common.event.BadGrassNaturalEntitySpawningConditionProcedure;
import com.rumaruka.gribtweaks.common.event.BadGrassOnEntityTickUpdateProcedure;
import com.rumaruka.gribtweaks.init.GTEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class BadSand extends Monster {
    public BadSand(EntityType<? extends BadSand> entityType,Level pLevel) {
        super(entityType, pLevel);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false){

            @Override
            protected double getAttackReachSqr(LivingEntity pAttackTarget) {
                return 4.0 + (double)(pAttackTarget.getBbWidth() * pAttackTarget.getBbWidth());
            }
        });
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(7, new RemoveBlockGoal(Blocks.POTATOES, this, 1.0, 5));
        this.goalSelector.addGoal(8, new RemoveBlockGoal(Blocks.WHEAT, this, 1.0, 5));
        this.goalSelector.addGoal(9, new RemoveBlockGoal(Blocks.CARROTS, this, 1.0, 5));
        this.goalSelector.addGoal(10, new RemoveBlockGoal(Blocks.BEETROOTS, this, 1.0, 5));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void playStepSound
            (BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.step")), 0.15f, 1.0f);
    }

    public SoundEvent getHurtSound
            (DamageSource ds) {
        return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.hit"));
    }

    public SoundEvent getDeathSound
            () {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break"));
    }

    public boolean hurt
            (DamageSource source, float amount) {
        if (source == DamageSource.CACTUS
        ) {
            return false;
        }
        if (source == DamageSource.DROWN) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public void baseTick
            () {
        super.baseTick
                ();
        BadGrassOnEntityTickUpdateProcedure.execute((LevelAccessor)this.level, (Entity)this);
    }

    public static void init() {
   
        DungeonHooks.addDungeonMob((EntityType)((EntityType) GTEntity.SAND_BAD.get()), (int)180);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 3.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder;
    }
}



