package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

// So apparently, salt crusts can conceal mires and marshes underneath them, so you have to be careful driving or
// walking on them. Implementable >:)

public class SaltCrustBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape RAY_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public SaltCrustBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return RAY_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.canCollide ? SHAPE : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return RAY_SHAPE;
    }

    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        this.tryTrample(worldIn, pos, entityIn, 1);
        System.out.println("on");
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        System.out.println(worldIn.getBlockState(entityIn.getPosition()));
        super.onLanded(worldIn, entityIn);
    }

//    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
//        // Could be useful if any mobs are too light, like bunnies or bugs or something?
////        if (!(entityIn instanceof ZombieEntity)) {
//        if (worldIn.rand.nextFloat() > 0.9f) {
//
//        }
//        System.out.println("pos: " + worldIn.getBlockState(pos));
//        System.out.println("pos up: " + worldIn.getBlockState(pos.up()));
//        System.out.println("----");
//
//        this.tryTrample(worldIn, pos.up(), entityIn, 1);
////        }
//
//        // TODO Prevent fall damage? Only if falling through. How?
//        super.onFallenUpon(worldIn, pos, entityIn, 0);
//    }

    private void tryTrample(World worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isRemote && worldIn.rand.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                System.out.println(blockstate);
                if (blockstate.matchesBlock(ModBlocks.SALT_CRUST_BLOCK.get())) {
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
                    worldIn.destroyBlock(pos, false);
                }
            }

        }
    }

    //TODO obvjously
    private boolean canTrample(World worldIn, Entity trampler) {
        if (!(trampler instanceof LivingEntity)) {
            return false;
        }
        return trampler instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
    }


//    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
//        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
//    }
//
//    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
//        return !worldIn.isAirBlock(pos.down());
//    }

}
