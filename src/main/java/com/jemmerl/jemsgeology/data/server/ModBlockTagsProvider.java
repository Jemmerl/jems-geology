package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import com.jemmerl.jemsgeology.init.geology.OreBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Jem's Geo Block Tags";
    }

    @Override
    protected void registerTags() {

        Builder<Block> tagBuilderGeoStone = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_STONE);
        Builder<Block> tagBuilderGeoRegolith = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_REGOLITH);
        Builder<Block> tagBuilderGeoCobbles = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_COBBLES);
        Builder<Block> tagBuilderGeoCobblestone = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_COBBLESTONE);

//        Builder<Block> tagBuilderOreHigh = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_HIGH);
//        Builder<Block> tagBuilderOreMid = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_MID);
//        Builder<Block> tagBuilderOreLow = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_LOW);
//        Builder<Block> tagBuilderOreAll = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE);
        Builder<Block> tagBuilderNoOre = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_NO_ORE);

        Builder<Block> tagVanillaWalls = this.getOrCreateBuilder(BlockTags.WALLS);

        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            boolean isRegolith = geoRegistry.getGeoType().getGeoGroup().isRegolith();
            boolean hasCobble = geoRegistry.hasCobble();

            tagBuilderNoOre.add(geoRegistry.getBaseGeoBlock());
            if (isRegolith) {
                tagBuilderGeoRegolith.add(geoRegistry.getBaseGeoBlock());
            } else {
                tagBuilderGeoStone.add(geoRegistry.getBaseGeoBlock());
            }

            for (OreBlockRegistry oreBlockRegistry : geoRegistry.getOreRegistry().values()) {
//                Block highGrade = oreBlockRegistry.getGradeOre(GradeType.HIGHGRADE).get();
                Block midGrade = oreBlockRegistry.getNormalOreBlock().get();
                Block lowGrade = oreBlockRegistry.getPoorOreBlock().get();

//                tagBuilderOreHigh.add(highGrade);
//                tagBuilderOreMid.add(midGrade);
//                tagBuilderOreLow.add(lowGrade);

                if (isRegolith) {
//                    tagBuilderGeoRegolith.add(highGrade);
                    tagBuilderGeoRegolith.add(midGrade);
                    tagBuilderGeoRegolith.add(lowGrade);
                } else {
//                    tagBuilderGeoStone.add(highGrade);
                    tagBuilderGeoStone.add(midGrade);
                    tagBuilderGeoStone.add(lowGrade);
                }
            }

            if (hasCobble) {
                tagBuilderGeoCobbles.add(geoRegistry.getCobbles());
                tagBuilderGeoCobblestone.add(geoRegistry.getCobblestone());

                tagVanillaWalls.add(geoRegistry.getCobbleWall());
                tagVanillaWalls.add(geoRegistry.getMossyCobbleWall());
                tagVanillaWalls.add(geoRegistry.getRawWall());
                tagVanillaWalls.add(geoRegistry.getPolishedWall());
                tagVanillaWalls.add(geoRegistry.getBrickWall());
                tagVanillaWalls.add(geoRegistry.getMossyBrickWall());
            }
        }

////        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_HIGH);
//        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_MID);
//        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_LOW);
    }
}
