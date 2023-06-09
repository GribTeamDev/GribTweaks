package com.rumaruka.gribtweaks.common.entity;

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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class BadSand extends Monster implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BadSand(EntityType<? extends BadSand> entityType, Level pLevel) {
        super(entityType, pLevel);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {

            @Override
            protected double getAttackReachSqr(LivingEntity pAttackTarget) {
                return 4.0 + (double) (pAttackTarget.getBbWidth() * pAttackTarget.getBbWidth());
            }
        });
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        //  this.goalSelector.addGoal(7, new RemoveBlockGoal(Blocks.POTATOES, this, 1.0, 5));
        // this.goalSelector.addGoal(8, new RemoveBlockGoal(Blocks.WHEAT, this, 1.0, 5));
        //  this.goalSelector.addGoal(9, new RemoveBlockGoal(Blocks.CARROTS, this, 1.0, 5));
        //  this.goalSelector.addGoal(10, new RemoveBlockGoal(Blocks.BEETROOTS, this, 1.0, 5));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.step")), 0.15f, 1.0f);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.hit"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break"));
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.CACTUS) {
            return false;
        }
        if (source == DamageSource.DROWN) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public void baseTick() {
        super.baseTick();

    }

    public static void init() {

        DungeonHooks.addDungeonMob(GTEntity.SAND_BAD.get(), 180);
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.run", true));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }



    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> state) {
        if(this.swinging ) {

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
}



