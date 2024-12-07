package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import com.jemmerl.jemsgeology.init.geologyinit.OreBlockRegistry;
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

//        Builder<Block> tagBuilderGeoStone = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_STONE);
//        Builder<Block> tagBuilderGeoRegolith = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_REGOLITH);
//        Builder<Block> tagBuilderGeoDetritus = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_DETRITUS);
        Builder<Block> tagBuilderGeoCobbles = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_COBBLES);
        Builder<Block> tagBuilderGeoCobblestone = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_COBBLESTONE);

//        Builder<Block> tagBuilderOreHigh = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_HIGH);
//        Builder<Block> tagBuilderOreMid = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_MID);
//        Builder<Block> tagBuilderOreLow = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE_LOW);
//        Builder<Block> tagBuilderOreAll = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_ORE);
//        Builder<Block> tagBuilderNoOre = this.getOrCreateBuilder(ModTags.Blocks.JEMSGEO_NO_ORE);

        Builder<Block> tagVanillaWalls = this.getOrCreateBuilder(BlockTags.WALLS);

        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            boolean isDetritus = geoRegistry.getGeoType().getGeoGroup().isDetritus();
            boolean hasCobble = geoRegistry.hasCobble();

//            tagBuilderNoOre.add(geoRegistry.getBaseStone());
//            if (isDetritus) {
//                tagBuilderGeoDetritus.add(geoRegistry.getBaseStone());
//            } else {
//                tagBuilderGeoStone.add(geoRegistry.getBaseStone());
//            }
//
//            for (OreBlockRegistry oreBlockRegistry : geoRegistry.getStoneOreRegistry().values()) {
////                Block highGrade = oreBlockRegistry.getGradeOre(GradeType.HIGHGRADE).get();
//                Block midGrade = oreBlockRegistry.getGradeOre(GradeType.MID).get();
//                Block lowGrade = oreBlockRegistry.getGradeOre(GradeType.LOW).get();
//
////                tagBuilderOreHigh.add(highGrade);
//                tagBuilderOreMid.add(midGrade);
//                tagBuilderOreLow.add(lowGrade);
//
//                if (isDetritus) {
////                    tagBuilderGeoDetritus.add(highGrade);
//                    tagBuilderGeoDetritus.add(midGrade);
//                    tagBuilderGeoDetritus.add(lowGrade);
//                } else {
////                    tagBuilderGeoStone.add(highGrade);
//                    tagBuilderGeoStone.add(midGrade);
//                    tagBuilderGeoStone.add(lowGrade);
//                }
//            }

            if (hasCobble) {
                tagBuilderGeoCobbles.add(geoRegistry.getCobbles());
                tagBuilderGeoCobblestone.add(geoRegistry.getCobblestone());

//                tagBuilderGeoRegolith.add(geoRegistry.getRegolith());
//                tagBuilderNoOre.add(geoRegistry.getRegolith());
//                for (OreBlockRegistry oreBlockRegistry : geoRegistry.getRegolithOreRegistry().values()) {
////                    Block highGrade = oreBlockRegistry.getGradeOre(GradeType.HIGHGRADE).get();
//                    Block midGrade = oreBlockRegistry.getGradeOre(GradeType.MID).get();
//                    Block lowGrade = oreBlockRegistry.getGradeOre(GradeType.LOW).get();
//
////                    tagBuilderOreHigh.add(highGrade);
//                    tagBuilderOreMid.add(midGrade);
//                    tagBuilderOreLow.add(lowGrade);
//
////                    tagBuilderGeoRegolith.add(highGrade);
//                    tagBuilderGeoRegolith.add(midGrade);
//                    tagBuilderGeoRegolith.add(lowGrade);
//                }

                tagVanillaWalls.add(geoRegistry.getCobbleWall());
                tagVanillaWalls.add(geoRegistry.getRawWall());
                tagVanillaWalls.add(geoRegistry.getPolishedWall());
            }
        }

////        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_HIGH);
//        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_MID);
//        tagBuilderOreAll.addTag(ModTags.Blocks.JEMSGEO_ORE_LOW);
    }
}
