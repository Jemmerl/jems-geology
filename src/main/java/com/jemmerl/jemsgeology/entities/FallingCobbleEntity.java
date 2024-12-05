package com.jemmerl.jemsgeology.entities;

import com.jemmerl.jemsgeology.blocks.FallingCobbleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

// Code adapted from TerraFirmaCraft (love this mod so much oml; what are you doing reading this, go play TFC)
// https://github.com/TerraFirmaCraft/TerraFirmaCraft/blob/1.16.x/src/main/java/net/dries007/tfc/common/entities/TFCFallingBlockEntity.java
public class FallingCobbleEntity extends FallingBlockEntity {

    private final boolean dontSetBlock;
    private boolean brokeBlock;

    public FallingCobbleEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);

        dontSetBlock = false;
        brokeBlock = false;
    }

    public FallingCobbleEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
        super(worldIn, x, y, z, fallingBlockState);

        dontSetBlock = false;
        brokeBlock = false;
    }

    //TODO CHECK IF TAGS NEED UPDATING

    //todo the above todo is one from the old mod version and i have no idea what it means
    //  also imma just assume this code is perfect cause its been months since i messed with it ;-;

    @Override
    public void tick() {
        final BlockState fallTile = getBlockState();
        
        if (fallTile.isAir()) {
            remove();
        } else {
            Block block = fallTile.getBlock();
            if (fallTime++ == 0) {
                BlockPos blockpos = getPosition();
                if (world.getBlockState(blockpos).matchesBlock(block)) {
                    world.removeBlock(blockpos, false);
                } else if (!world.isRemote) {
                    remove();
                    return;
                }
            }

            if (!hasNoGravity()) {
                setMotion(getMotion().add(0.0D, -0.04D, 0.0D));
            }

            move(MoverType.SELF, this.getMotion());
            if (!world.isRemote) {
                BlockPos blockPos = getPosition();
                BlockState blockstate = world.getBlockState(blockPos);
                BlockState downState = world.getBlockState(blockPos.down());

                if (!onGround) {
                    brokeBlock = false;
                    if (world.isBlockPresent(blockPos) && !(blockstate.isAir()) && FallingCobbleBlock.canSmashThrough(blockstate)) {
                        // Break the current block if smashable
                        world.destroyBlock(blockPos, true);
                        //brokeBlock = true;
                        return;
                    }
                    if (!world.isRemote && ((fallTime > 100) && ((blockPos.getY() < 1) || (blockPos.getY() > 256)) || (fallTime > 600))) {
                        if (shouldDropItem && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            entityDropItem(block);
                        }

                        remove();
                    }
                } else {
                    // On ground
                    boolean isReplaceable = blockstate.isReplaceable(new DirectionalPlaceContext(world, blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                    boolean isValidPosition = fallTile.isValidPosition(world, blockPos);

                    if (!brokeBlock) {
                        if (world.isBlockPresent(blockPos.down()) && FallingCobbleBlock.canSmashThrough(downState)) {
                            // Break the below block if smashable
                            world.destroyBlock(blockPos.down(), true);
                            brokeBlock = true;
                            return;
                        }
                    }

                    setMotion(getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (!blockstate.matchesBlock(Blocks.MOVING_PISTON)) {
                        remove();
                        if (!dontSetBlock) {

                            if (isReplaceable && isValidPosition) {
                                if (world.setBlockState(blockPos, fallTile, 3)) {
                                    if (block instanceof FallingBlock) {
                                        ((FallingBlock)block).onEndFalling(this.world, blockPos, fallTile, blockstate, this);
                                    }

                                    if (tileEntityData != null && fallTile.hasTileEntity()) {
                                        TileEntity tileentity = world.getTileEntity(blockPos);
                                        if (tileentity != null) {
                                            CompoundNBT compoundnbt = tileentity.write(new CompoundNBT());

                                            for(String s : tileEntityData.keySet()) {
                                                INBT inbt = tileEntityData.get(s);
                                                if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                                    compoundnbt.put(s, inbt.copy());
                                                }
                                            }

                                            tileentity.read(fallTile, compoundnbt);
                                            tileentity.markDirty();
                                        }
                                    }
                                } else if (shouldDropItem && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    entityDropItem(block);
                                }
                            } else if (shouldDropItem && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                entityDropItem(block);
                            }
                        } else if (block instanceof FallingBlock) {
                            ((FallingBlock)block).onBroken(world, blockPos, this);
                        }
                    }
                }
            }

            setMotion(getMotion().scale(0.98D));
        }
    }
}
