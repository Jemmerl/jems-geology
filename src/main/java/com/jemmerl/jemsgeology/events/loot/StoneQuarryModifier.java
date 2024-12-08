package com.jemmerl.jemsgeology.events.loot;

import com.google.gson.JsonObject;
import com.jemmerl.jemsgeology.items.QuarryItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class StoneQuarryModifier extends LootModifier {
    protected StoneQuarryModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (!(entity instanceof PlayerEntity)) {
            return generatedLoot;
        }
        PlayerEntity player = (PlayerEntity) entity;

        Vector3d vector3d = context.get(LootParameters.ORIGIN);
        if (vector3d == null) {
            return generatedLoot;
        }
        BlockPos pos = new BlockPos(vector3d);

        BlockState state = context.get(LootParameters.BLOCK_STATE);
        if (state == null) {
            return generatedLoot;
        }

        if (isBlockQuarried(context.getWorld(), pos, state, player)) {
            generatedLoot.clear();
            generatedLoot.add(state.getBlock().asItem().getDefaultInstance());
        }
        return generatedLoot;
    }


    public static class Serializer extends GlobalLootModifierSerializer<StoneQuarryModifier> {
        @Override
        public StoneQuarryModifier read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions) {
            return new StoneQuarryModifier(lootConditions);
        }

        @Override
        public JsonObject write(StoneQuarryModifier instance) {
            return makeConditions(instance.conditions);
        }
    }


    // Check if the block has been successfully quarried by the player
    private boolean isBlockQuarried(World world, BlockPos pos, BlockState state, PlayerEntity playerIn) {
        if (world.isRemote || playerIn.isCreative()) {
            return false;
        }

        if (holdingQuarryTools(state, playerIn) && canQuarryBlock(world, pos)) {
            playerIn.getHeldItemOffhand().damageItem(1, playerIn,
                    (player) -> player.sendBreakAnimation(Hand.OFF_HAND));
            return true;
        }

        return false;
    }

    // Check if the player is holding the right tools to quarry
    private boolean holdingQuarryTools(BlockState state, PlayerEntity player) {
        boolean quarryTool = player.getHeldItemOffhand().getItem() instanceof QuarryItem;
        boolean canBreak = player.getHeldItemMainhand().canHarvestBlock(state);
        return (quarryTool && canBreak);
    }

    // Check if three connected faces that share a vertex are open to air
    private boolean canQuarryBlock(World world, BlockPos pos) {
        boolean up = isFaceOpen(world, pos.offset(Direction.UP));
        boolean down = isFaceOpen(world, pos.offset(Direction.DOWN));
        if (!(up || down)) return false;

        boolean north = isFaceOpen(world, pos.offset(Direction.NORTH));
        boolean south = isFaceOpen(world, pos.offset(Direction.SOUTH));
        if (!(north || south)) return false;

        boolean east = isFaceOpen(world, pos.offset(Direction.EAST));
        boolean west = isFaceOpen(world, pos.offset(Direction.WEST));
        return (east || west);
    }

    // Check if a face is exposed
    private boolean isFaceOpen(World world, BlockPos offsetPos) {
        BlockState state = world.getBlockState(offsetPos);
        Material stateMaterial = state.getMaterial();
        return (state.isIn(BlockTags.FIRE) || stateMaterial.isLiquid() || stateMaterial.isReplaceable());
    }
}