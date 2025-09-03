package com.jemmerl.jemsgeology.capabilities.world.watertable;

import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.lighting.WorldLightManager;

import javax.annotation.Nullable;

// Class heavily built/cloned from Lost Cities (McJty)
// My gratitude cannot be expressed enough for McJty's prior work in developing this, full credit to them
// https://github.com/McJtyMods/LostCities
public class DummyChunk extends ChunkPrimer {

    private static final BlockState RHYOLITE = ModBlocks.GEO_BLOCKS.get(GeoType.GRAY_RHYOLITE).getBaseState();

//    private final ChunkHeightmap heightmap;

    public DummyChunk(ChunkPos pos/*, ChunkHeightmap heightmap*/) {
        super(pos, UpgradeData.EMPTY);
//        this.heightmap = heightmap;
    }

    // TODO I do not know if this is important or not. I do not think so.
    @Nullable
    @Override
    public BlockState setBlockState(BlockPos pos, BlockState state, boolean isMoving) {
        return Blocks.AIR.getDefaultState();
//        heightmap.update(pos.getX() & 0xf, pos.getY(), pos.getZ() & 0xf, state);
//        return super.setBlockState(pos, state, isMoving);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();

//        if (pos.getY() >= heightmap.getHeight(pos.getX() & 0xf, pos.getZ() & 0xf)) {
//            return Blocks.AIR.getDefaultState();
//        } else {
//            return RHYOLITE;
//        }

//        return super.getBlockState(pos);
    }

    @Override
    public int getTopBlockY(Heightmap.Type type, int x, int z) {
        return 63;
//        return heightmap.getHeight(x & 0xf, z & 0xf);
    }

    // Chunk Primer
    //   public int getTopBlockY(Heightmap.Type heightmapType, int x, int z) {
    //      Heightmap heightmap = this.heightmaps.get(heightmapType);
    //      if (heightmap == null) {
    //         Heightmap.updateChunkHeightmaps(this, EnumSet.of(heightmapType));
    //         heightmap = this.heightmaps.get(heightmapType);
    //      }
    //
    //      return heightmap.getHeight(x & 15, z & 15) - 1;
    //   }

    // Chunk Primer Wrapper
    //   public int getTopBlockY(Heightmap.Type heightmapType, int x, int z) {
    //      return this.chunk.getTopBlockY(this.func_209532_c(heightmapType), x, z);
    //   }

    @Override
    public void addLightValue(short packedPosition, int lightValue) {
        // Do nothing
    }

    @Override
    public void addLightPosition(BlockPos lightPos) {
        // Do nothing
    }

    @Override
    public void setLightManager(WorldLightManager worldLightManager) {
        super.setLightManager(worldLightManager);
    }
}
