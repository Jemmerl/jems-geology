package com.jemmerl.jemsgeology.world.features.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ServerConfig;
import com.jemmerl.jemsgeology.util.ReplaceStatus;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.world.features.config.BoulderFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.Random;

public class BoulderFeature extends Feature<BoulderFeatureConfig> {
    public BoulderFeature(Codec<BoulderFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BoulderFeatureConfig config) {
//        if (rand.nextInt(entry.getChance()) != 0) {
//            return false;
//        }

        //LootPredicateManager

        // Place boulder in center of chunk +/-4 blocks in any direction (12x12 valid area vs 16x16 for buffer)
        BlockPos placePos = pos.add((8 + rand.nextInt(5)), 0, (8 + rand.nextInt(5)));
//        Biome biome = reader.getBiome(placePos);
//        if (!entry.getBiomes().contains(biome.getCategory())) {
//            return false;
//        }

        GeoType geoType = config.getStoneState(rand);
        if (buildBoulder(config, reader, rand, placePos, geoType, false)) {
            // Attempt to place smaller side boulders up to the max allowed, 50% chance for each
            for (int i = 0; i < config.max_side_boulders; i++) {
                if (rand.nextBoolean()) continue;
                buildBoulder(config, reader, rand, placePos, geoType, true);
            }
            return true;
        }
        return false;
    }


    // todo vertical rotation? :3
    private boolean buildBoulder(BoulderFeatureConfig config, ISeedReader reader, Random rand, BlockPos pos, GeoType geoType, boolean adjacent) {
        final BlockState boulderState = ModBlocks.GEO_BLOCKS.get(geoType).getBaseState();
        int radA = rand.nextInt(config.max_radius_a - config.min_radius_a + 1) + config.min_radius_a;
        int radB = rand.nextInt(config.max_radius_b - config.min_radius_b + 1) + config.min_radius_b;
        int embedDepth = rand.nextInt(Math.min(radA,radB)-1) + 1; // Depth the center is into the ground (1 to (shortest radius-1))
        float rotAngle = (float) Math.toRadians(rand.nextInt(181) - 90); // Horizontal rotation angle (-90 to 90)

        // Radius A is assumed to be larger or equal to radius B
        // Fast swap values if radius B is larger than radius A
        if (radB > radA) {
            radA = radA ^ radB;
            radB = radA ^ radB;
            radA = radA ^ radB;
        }

        // Shift placement location and shrink size if adjacent boulder
        if (adjacent) {
            pos = pos.add((rand.nextInt((radA * 2) + 1) - radA), 0, (rand.nextInt((radA * 2) + 1) - radA));
            if (radA > 3) radA /= 2;
            if (radB > 3) radB /= 2;
        }

        int centerX = pos.getX();
        int centerZ = pos.getZ();
        int centerY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ) - embedDepth;

        // Check if the boulder can spawn on hill slopes. If not, check if it is on a hill and cancel if so
        if (!config.place_on_hills && isOnHill(reader, centerX, centerZ)) {
            // Debug
            if (ServerConfig.DEBUG_BOULDER_FEATURE.get()) {
                JemsGeology.LOGGER.debug("Boulder failed to place on hill at ({} ~ {})", centerX, centerZ);
            }
            return false;
        }

        float alpha = (float) Math.tan(rotAngle/2f); // flipped signs to change rotation direction
        float beta = (float) -Math.sin(rotAngle);

        // Generate planar rotation matrix
        int[][] xShears = new int[(radA * 2) + 1][(radA * 2) + 1];
        int[][] zShears = new int[(radA * 2) + 1][(radA * 2) + 1];
        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-radA, 0, -radA), pos.add(radA, 0, radA))) {
            int xRot = currPos.getX() - centerX;
            int zRot = currPos.getZ() - centerZ;

            int iX = xRot + radA;
            int iZ = zRot + radA;

            // First shear - horizontal
            int xShear = Math.round(Math.abs(zRot) * alpha);
            if (zRot < 0) xShear *= -1;
            xRot += xShear;

            // Second shear - vertical
            int zShear = Math.round(Math.abs(xRot) * beta);
            if (xRot < 0) zShear *= -1;
            zRot += zShear;

            // Third shear - horizontal
            xShear = Math.round(Math.abs(zRot) * alpha);
            if (zRot < 0) xShear *= -1;
            xRot += xShear;

            xShears[iX][iZ] = xRot;
            zShears[iX][iZ] = zRot;
        }

        // Rotate and place boulder
        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-radA, (centerY - radA), -radA), pos.add(radA, (centerY + radA), radA))) {
            if (isInvalidPlacement(reader.getBlockState(currPos).getBlock())) {
                continue;
            }

            int xPosAdj = currPos.getX() - centerX;
            int zPosAdj = currPos.getZ() - centerZ;

            int iX = xPosAdj + radA;
            int iZ = zPosAdj + radA;

            int xRot = xPosAdj + xShears[iX][iZ];
            int zRot = zPosAdj + zShears[iX][iZ];

            // Calculate vertical decrement
            int yAdjAbs = Math.abs(currPos.getY() - centerY);
            if (yAdjAbs > radB) continue;
            float yPercent = 1 - (float) Math.pow((yAdjAbs / (float) radB), 2);

//            float noiseVal = BlobNoise.getBoulderNoise((xRot * 7f / (radB * 4f)), (zRot * 13f / (radA * 4f)));
//            if (noiseVal < -(0.43f - (0.13f * yPercent))) {
//                reader.setBlockState(currPos, boulderState, 2);
//            }
        }

        // Debug
        if (ServerConfig.DEBUG_BOULDER_FEATURE.get()) {
            if (adjacent) {
                JemsGeology.LOGGER.debug(
                        "-> Placed Extra Boulder with stone: {} at ({} ~ {}) with long radius: {}, short radius {}, and embed depth {}.",
                        geoType.getName(), centerX, centerZ, radA, radB, embedDepth);
            } else {
                JemsGeology.LOGGER.debug(
                        "Placed Boulder with stone: {} at ({} ~ {}) with long radius: {}, short radius {}, and embed depth {}.",
                        geoType.getName(), centerX, centerZ, radA, radB, embedDepth);
            }
        }

        return true;
    }

    private boolean isOnHill(ISeedReader reader, int centerX, int centerZ) {
        int yX1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX + 1, centerZ);
        int yX2 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX - 1, centerZ);
        float mX = (yX1 - yX2) / 2f;

        if (Math.abs(mX) > 0.75f) {
            return true;
        }

        int yZ1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ + 1);
        int yZ2 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ - 1);
        float mZ = (yZ1 - yZ2) / 2f;

        if (Math.abs(mZ) > 0.75f) {
            return true;
        }

        return false;
    }

    //todo refine?
    private boolean isInvalidPlacement(Block block) {
        return ReplaceStatus.isStoneBlock(block) || (block.isIn(BlockTags.LOGS));
    }
}
