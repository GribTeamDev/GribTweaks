package com.rumaruka.gribtweaks.common.entity;

import com.rumaruka.gribtweaks.common.entity.projectile.SandShardProjectile;
import com.rumaruka.gribtweaks.init.GTEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;

public class SandyBreeze extends Monster implements RangedAttackMob, IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public SandyBreeze(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 0;
        this.setNoAi(false);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new FlyingPathNavigation(this, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new Goal() {
            {
                this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            }

            public boolean canUse() {
                return SandyBreeze.this.getTarget() != null && !SandyBreeze.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return SandyBreeze.this.getMoveControl().hasWanted() && SandyBreeze.this.getTarget() != null && SandyBreeze.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = SandyBreeze.this.getTarget();
                Vec3 vec3d = livingentity.getEyePosition(1.0f);
                SandyBreeze.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0);
            }

            public void tick() {
                LivingEntity livingentity = SandyBreeze.this.getTarget();
                if (SandyBreeze.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    SandyBreeze.this.doHurtTarget(livingentity);
                } else {
                    double d0 = SandyBreeze.this.distanceTo(livingentity);
                    if (d0 < 16.0) {
                        Vec3 vec3d = livingentity.getEyePosition(1.0f);
                        SandyBreeze.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0);
                    }
                }
            }
        });
        this.goalSelector.addGoal(2, (Goal) new RandomStrollGoal(this, 0.8, 20) {

            protected Vec3 getPosition() {
                RandomSource random = SandyBreeze.this.getRandom();
                double dir_x = SandyBreeze.this.getX() + (double) ((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
                double dir_y = SandyBreeze.this.getY() + (double) ((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
                double dir_z = SandyBreeze.this.getZ() + (double) ((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
                return new Vec3(dir_x, dir_y, dir_z);
            }
        });
        this.goalSelector.addGoal(3, (Goal) new MeleeAttackGoal(this, 1.2, false) {

            protected double getAttackReachSqr(LivingEntity entity) {
                return 4.0 + (double) (entity.getBbWidth() * entity.getBbWidth());
            }
        });
        this.goalSelector.addGoal(4, (Goal) new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, (Goal) new NearestAttackableTargetGoal<>(this, Animal.class, false, false));
        this.targetSelector.addGoal(6, (Goal) new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(7, (Goal) new HurtByTargetGoal(this, new Class[0]));
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.composter.fill"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.composter.fill_success"));
    }

    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.FALL) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public void baseTick() {
        super.baseTick();
    }

    protected void checkFallDamage
            (double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    public static void init() {
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    @Override
    public void registerControllers(AnimationData data) {

        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::move));
        data.addAnimationController(new AnimationController<>(this, "controller1", 0, this::attackPredicate));
    }

    private <E extends IAnimatable> PlayState move(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fly", true));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }


    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> state) {
        if (this.swinging) {

            state.getController().setAnimation(new AnimationBuilder().addAnimation("animation.attack", LOOP));
            this.swinging = false;
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        SandShardProjectile sandShardProjectile = new SandShardProjectile(GTEntity.SAND_SHARD.get(), getLevel());
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getBoundingBox().minY + pTarget.getBbHeight() / 3.0F - sandShardProjectile.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        sandShardProjectile.shoot(d0, d1 + d3 * 0.2D, d2, 1.6F, 14 - this.getLevel().getDifficulty().getId() * 4);
        this.gameEvent(GameEvent.PROJECTILE_SHOOT);
        this.getLevel().addFreshEntity(sandShardProjectile);
    }
}
