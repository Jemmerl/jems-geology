package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

// TODO can implement pumped brine extraction mining the same way I could do oil shales and related fluids/dissolvables
//  Have a check around the whole chunk, grabbing and de-oreing a certain number of valid blocks per extraction.
//  I.e., for brine: Consume X mb of water, check for Y number of ore blocks in radius, convert to non-ore variants
//  (how to handle solid evaporite blocks?? maybe let it be air... worry about collapse!!), and return Z mb of brine.

public class ThickSaltCrustBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

    public ThickSaltCrustBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    //todo make it so it only can break when moving or falling?
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote) {
            this.tryTrample(worldIn, pos, entityIn, 10);
            System.out.println("collide");

        }
    }


//    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
//        this.tryTrample(worldIn, pos, entityIn, 1);
//        super.onEntityWalk(worldIn, pos, entityIn);
//    }
//
//    public void onLanded(IBlockReader worldIn, Entity entityIn) {
//        System.out.println(worldIn.getBlockState(entityIn.getPosition()));
//        super.onLanded(worldIn, entityIn);
//    }

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

    private void tryTrample(World worldIn, BlockPos pos, Entity trampler, int chanceOutOf) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isRemote && worldIn.rand.nextInt(chanceOutOf) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                if (blockstate.matchesBlock(ModBlocks.THICK_SALT_CRUST_BLOCK.get())) {
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
                    worldIn.destroyBlock(pos, false);
                }
            }

        }
    }

    //TODO obvjously <- current Jem here, tf did I mean "obviously". Like, what is wrong with it? Why did I do this.
    private boolean canTrample(World worldIn, Entity trampler) {
        if (!(trampler instanceof LivingEntity)) {
            return false;
        }
        return trampler instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
    }



}
