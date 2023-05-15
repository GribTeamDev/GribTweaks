package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.common.block.StatueOBJ;
import com.rumaruka.gribtweaks.init.GTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import shadows.gateways.entity.GatewayEntity;
import shadows.gateways.gate.Gateway;
import shadows.gateways.gate.GatewayManager;
import shadows.gateways.item.GatePearlItem;

public class ForestCatalystItem extends GatePearlItem {

    public static final float ARENA_RANGE = 12F;
    public static final int ARENA_HEIGHT = 5;

    public ForestCatalystItem(Properties props) {
        super(props);
    }

    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        ItemStack stack = ctx.getItemInHand();
        BlockPos pos = ctx.getClickedPos();

        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (!world.getEntitiesOfClass(GatewayEntity.class, (new AABB(pos)).inflate(25.0, 25.0, 25.0)).isEmpty()) {
            return InteractionResult.FAIL;
        } else {
            GatewayEntity entity = new GatewayEntity(world, ctx.getPlayer(), getGate(stack));
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == GTBlocks.statue_obj.get() && state.getValue(StatueOBJ.ACTIVE) == Boolean.FALSE) {
                world.setBlockAndUpdate(pos, state.setValue(StatueOBJ.ACTIVE, true));
                entity.setPos((double) pos.getX() + 0.5, (double) pos.getY() + state.getShape(world, pos).min(Direction.Axis.Y), (double) pos.getZ() + 0.5);
                int y = 0;

                while (y++ < 2 && !world.noCollision(entity)) {
                    entity.setPos(entity.getX(), entity.getY() - 2.0, entity.getZ());
                }

//                if (!world.noCollision(entity)) {
//                    ctx.getPlayer().sendSystemMessage(Component.translatable("error.gateways.no_space").withStyle(ChatFormatting.RED));
//                    return InteractionResult.FAIL;
//                } else {

                world.addFreshEntity(entity);

                entity.onGateCreated();


                if (!ctx.getPlayer().isCreative()) {
                    stack.shrink(1);
                }


                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.FAIL;
            }
        }

    }

    public static void setGate(ItemStack opener, Gateway gate) {
        opener.getOrCreateTag().putString("gateway", gate.getId().toString());
    }

    public static Gateway getGate(ItemStack opener) {
        return (Gateway) GatewayManager.INSTANCE.getValue(new ResourceLocation(opener.getOrCreateTag().getString("gateway")));
    }

    public Component getName(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            return super.getName(stack);
        } else {
            Gateway gate = getGate(stack);
            return (Component) (gate != null ? Component.translatable("gateways.forestcatalyst").withStyle(Style.EMPTY.withColor(gate.getColor())) : super.getName(stack));
        }
    }
    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {

    }



    public interface IGateSupplier {
        GatewayEntity createGate(Level var1, Player var2, Gateway var3);
    }
}