package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.common.block.BrushableBlock;
import com.rumaruka.gribtweaks.common.tiles.BrushableBlockEntity;
import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class BrushItem extends Item {
    private static final double MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0D;
    private final boolean primitive;

    public BrushItem(Properties pProperties, boolean primitive) {
        super(pProperties);
        this.primitive = primitive;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        BlockPos pos = useOnContext.getClickedPos();
        BlockState state = useOnContext.getLevel().getBlockState(pos);
        BlockEntity entity = useOnContext.getLevel().getBlockEntity(pos);
        if (player != null && entity instanceof BrushableBlockEntity brushableBlockEntity) {
            if (brushableBlockEntity.getItem().isEmpty()) {
                if (state.getBlock().equals(GTBlocks.SUSPICIOUS_SAND.get())) {
                    if (this.primitive) {
                        brushableBlockEntity.setItem(this.getBrushingItem(true, true));
                    } else {
                        brushableBlockEntity.setItem(this.getBrushingItem(false, true));
                    }
                } else if (state.getBlock().equals(GTBlocks.SAND.get())) {
                    brushableBlockEntity.setItem(this.getBrushingItem(true, false));
                }
            }
            player.startUsingItem(useOnContext.getHand());
        }
        return InteractionResult.CONSUME;
    }

    public ItemStack getBrushingItem(boolean primitive, boolean suspicious) {
        if (primitive && suspicious) {
            if (RandomUtil.percentChance(0.2)) return new ItemStack(GTItems.sand_copper_nugget.get());
            if (RandomUtil.percentChance(0.4)) return new ItemStack(GTItems.SAND_STONE_PIECES.get());
            return new ItemStack(GTItems.STONE_NUGGET.get());
        } else if (!primitive && suspicious) {
            if (RandomUtil.percentChance(0.1)) return new ItemStack(GTItems.STONE_NUGGET.get());
            if (RandomUtil.percentChance(0.1)) return new ItemStack(GTItems.SAND_STONE_PIECES.get());
            if (RandomUtil.percentChance(0.15)) return new ItemStack(GTItems.sand_zinc_nugget.get());
            if (RandomUtil.percentChance(0.15)) return new ItemStack(GTItems.sand_iron_nugget.get());
            if (RandomUtil.percentChance(0.25)) return new ItemStack(GTItems.sand_copper_nugget.get());
            return new ItemStack(GTItems.ANDESITE_PIECE.get());
        } else {
            if (RandomUtil.percentChance(0.25)) return new ItemStack(Items.BONE);
            if (RandomUtil.percentChance(0.30)) return new ItemStack(GTItems.SAND_STONE_PIECES.get());
            return new ItemStack(GTItems.STONE_NUGGET.get());
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 6000;
    }

    private HitResult calculateHitResult(LivingEntity livingEntity) {
        return getHitResultOnViewVector(livingEntity, (predicate) -> {
            return !predicate.isSpectator() && predicate.isPickable();
        }, MAX_BRUSH_DISTANCE);
    }

    private HitResult getHitResultOnViewVector(Entity entity, Predicate<Entity> predicate, double d1) {
        Vec3 vec3 = entity.getViewVector(0.0F).scale(d1);
        Level level = entity.getLevel();
        Vec3 vec31 = entity.getEyePosition();
        return getHitResult(vec31, entity, predicate, vec3, level);
    }

    private HitResult getHitResult(Vec3 p_278237_, Entity p_278320_, Predicate<Entity> p_278257_, Vec3 p_278342_, Level p_278321_) {
        Vec3 vec3 = p_278237_.add(p_278342_);
        HitResult hitresult = p_278321_.clip(new ClipContext(p_278237_, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_278320_));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(p_278321_, p_278320_, p_278237_, vec3, p_278320_.getBoundingBox().expandTowards(p_278342_).inflate(1.0D), p_278257_);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity livingEntity, int count) {
        if (livingEntity instanceof Player player) {
            HitResult hitResult = this.calculateHitResult(livingEntity);
            if (hitResult instanceof BlockHitResult blockhitresult) {
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    int c = count;
                    int i = this.getUseDuration(stack) - c;
                    boolean flag = i % 10 == 5;
                    if (flag) {
                        BlockPos blockpos = blockhitresult.getBlockPos();
                        if (!player.level.isClientSide()) {
                            BlockEntity blockentity = player.level.getBlockEntity(blockpos);
                            BlockState blockState = player.level.getBlockState(blockpos);
                            if (blockentity instanceof BrushableBlockEntity && blockState.getValue(BrushableBlock.DUSTED) == 3) {
                                BrushableBlockEntity brushableblockentity = (BrushableBlockEntity) blockentity;
                                brushableblockentity.dropContent(player);
                                player.level.removeBlock(blockpos, true);
                                EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                stack.hurtAndBreak(1, livingEntity, (entity) -> {
                                    entity.broadcastBreakEvent(equipmentslot);
                                });
                                return;
                            }
                            if (blockState.getBlock() instanceof BrushableBlock && blockState.getValue(BrushableBlock.DUSTED) < 3) {
                                BlockState blockstate1 = blockState.setValue(BrushableBlock.DUSTED, blockState.getValue(BrushableBlock.DUSTED) + 1);
                                player.level.setBlock(blockpos, blockstate1, 3);
                            }
                        }
                    }
                    return;
                }
            }
            livingEntity.releaseUsingItem();
        } else {
            livingEntity.releaseUsingItem();
        }
    }

//    @Override
//    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int pCount) {
//        if (pCount >= 0 && livingEntity instanceof Player player) {
//            HitResult hitresult = this.calculateHitResult(livingEntity);
//            if (hitresult instanceof BlockHitResult blockhitresult) {
//                if (hitresult.getType() == HitResult.Type.BLOCK) {
//                    int i = this.getUseDuration(itemStack) - pCount + 1;
//                    boolean flag = i % 10 == 5;
//                    if (flag) {
//                        BlockPos blockpos = blockhitresult.getBlockPos();
//                        if (!level.isClientSide()) {
//                            BlockEntity blockentity = level.getBlockEntity(blockpos);
//                            if (blockentity instanceof BrushableBlockEntity) {
//                                BrushableBlockEntity brushableblockentity = (BrushableBlockEntity)blockentity;
//                                boolean flag1 = brushableblockentity.brush(level.getGameTime(), player, blockhitresult.getDirection());
//                                if (flag1) {
//                                    EquipmentSlot equipmentslot = itemStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
//                                    itemStack.hurtAndBreak(1, livingEntity, (p_279044_) -> {
//                                        p_279044_.broadcastBreakEvent(equipmentslot);
//                                    });
//                                }
//                            }
//                        }
//                    }
//                    return;
//                }
//            }
//            livingEntity.releaseUsingItem();
//        } else {
//            livingEntity.releaseUsingItem();
//        }
//    }
}
