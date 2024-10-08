package com.jemmerl.jemsgeology.geology.deposits.instances;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.ore.DepositType;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.IEnqueuedDeposit;
import com.jemmerl.jemsgeology.geology.deposits.templates.LayerTemplate;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.jemmerl.jemsgeology.geology.ChunkReader;
import com.jemmerl.jemsgeology.geology.GeoMapBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Random;

public class LayerEnqueuedDeposit implements IEnqueuedDeposit {

    private final LayerTemplate layerTemplate;

    private String name;
    private WeightedProbMap<OreType> ores;
    private WeightedProbMap<GradeType> gradesMap;
    private ArrayList<GeologyType> validList;
    private ArrayList<Biome.Category> validBiomes;

    public LayerEnqueuedDeposit(LayerTemplate template) {
        this.layerTemplate = template;
    }

    /////////////
    // SETTERS //
    /////////////

    @Override
    public LayerEnqueuedDeposit setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public LayerEnqueuedDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    @Override
    public LayerEnqueuedDeposit setGrades(WeightedProbMap<GradeType> gradesMap) {
        this.gradesMap = gradesMap;
        return this;
    }

    @Override
    public LayerEnqueuedDeposit setValid(ArrayList<GeologyType> validList) {
        this.validList = validList;
        return this;
    }

    @Override
    public IEnqueuedDeposit setBiomes(ArrayList<Biome.Category> validBiomes) {
        this.validBiomes = validBiomes;
        return this;
    }

    /////////////
    // GETTERS //
    /////////////

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DepositType getType() {
        return DepositType.LAYER;
    }

    @Override
    public WeightedProbMap<OreType> getOres() {
        return this.ores;
    }

    @Override
    public WeightedProbMap<GradeType> getGrades() {
        return this.gradesMap;
    }

    @Override
    public ArrayList<GeologyType> getValid() {
        return this.validList;
    }

    @Override
    public ArrayList<Biome.Category> getBiomes() {
        return this.validBiomes;
    }

    @Override
    public int getWeight() {
        return this.layerTemplate.getWeight();
    }


    //////////////////////////
    //  DEPOSIT GENERATION  //
    //////////////////////////

    @Override
    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, GeoMapBuilder geoMapBuilder) {

        // Constants
        final int VARIANCE = 9; // The max +/- blocks radius variation

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeedReader().getSeed());
        }

        ////////////////////////
        // DEPOSIT PROPERTIES //
        ////////////////////////

        // Get a uniformly distributed density value for the deposit within the min and max density range
        float densityPercent = ((rand.nextInt(this.layerTemplate.getMaxDensity() -
                this.layerTemplate.getMinDensity()) + this.layerTemplate.getMinDensity()) / 100f);

        // Get the weighted random grade of the ore deposit
        GradeType grade = this.gradesMap.nextElt();

        // Get a normally distributed average radius (for the individual deposit) around the average configured radius
        int avgDepositRadius = getAvgRadius(rand);

        // If the max layers is 1, return 1. Else, random spread. (Having 1 max layer inputs 0 into rand.nextInt, which is illegal)
        int avgLayers = (this.layerTemplate.getMaxLayers() == 1) ? 1 : (rand.nextInt(this.layerTemplate.getMaxLayers() - 1) + 1);

        // Calculate the predicted total height given the total layers to be generated and the average thickness of
        // those layers. One height is added to the avg layer thickness to compensate for spacing levels.
        int totalHeight = avgLayers * (this.layerTemplate.getAvgLayerThick() + 1);

        // Get a uniformly distributed start height for the deposit within the min and max height range
        // The first part of this formula picks the height of the deposit center. The downward shift
        // then defines the true start height of the deposit. Without this shift, the deposit could generate
        // completely outside the user-defined height range. Now, only half the deposit can at maximum.
        int heightStart = (rand.nextInt(this.layerTemplate.getMaxYHeight() - this.layerTemplate.getMinYHeight())
                + this.layerTemplate.getMinYHeight()) - (totalHeight / 2);
        int heightEnd = heightStart + totalHeight;

        // Check if the deposit is generating higher than possible (prevent StateMap ArrayOutOfBounds in the Y direction)
        // If so, try to truncate to that value. If that removed the whole deposit, prevent it from generating.
        if (heightEnd > reader.getMaxHeight()) {
            heightEnd = reader.getMaxHeight();
            if (heightEnd < heightStart) {
                return false;
            }
            totalHeight = heightEnd - heightStart; // Update the new total height
        }

        // Set approximate center of the deposit
        BlockPos centerPos = new BlockPos(
                (pos.getX() + rand.nextInt(16)),
                (int)((heightStart + heightEnd) / 2f),
                (pos.getZ() + rand.nextInt(16))
        );


        ////////////////////////
        // DEPOSIT GENERATION //
        ////////////////////////

        // Debug
        if (JemsGeoConfig.SERVER.debug_layer_deposits.get()) {
            JemsGeology.getInstance().LOGGER.info("Generating layer deposit at {}, with {} avg layers and {} total height.", centerPos, avgLayers, totalHeight);
        }

        // Check for valid biome placement. If not in a valid biome, cancel generation
        if (!this.validBiomes.contains(reader.getSeedReader().getBiome(centerPos).getCategory())) {
            // Debug
            if (JemsGeoConfig.SERVER.debug_layer_deposits.get()) {
                JemsGeology.getInstance().LOGGER.info("Invalid biome for layer deposit at {}, failed to generate.", centerPos);
            }
            return false;
        }

        // Layer heights setup
        int layerHeightTotal = getLayerHeight(rand); // Set the first layer's total height
        int layerHeightCount = -1; // Used to count and put spacing layers between deposit layers

        for (int y = heightStart; y < heightEnd; y++) {

            // Variable density for spacing level generation
            // Set a level as a spacer (has a very low ore density compared to a normal layer)
            // Negative values generate a spacer, >= 0 generate normally
            // The last level of the last layer is also a spacer
            float adjDensityPercent = ((layerHeightCount < 0) || (y == (heightEnd - 1))) ? (densityPercent * 0.2f) : densityPercent;

            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(
                    new BlockPos((centerPos.getX() - avgDepositRadius - (VARIANCE+1)), y,
                            (centerPos.getZ() - avgDepositRadius - (VARIANCE+1))),
                    new BlockPos((centerPos.getX() + avgDepositRadius + (VARIANCE+1)), y,
                            (centerPos.getZ() + avgDepositRadius + (VARIANCE+1)))))
            {

                // TODO BELOW THIS SUCKS
//                // Uses a logarithmic equation to taper the ends of a layer deposit.
//                // Applies mainly to larger deposits, and deposits smaller than a height of 6 are not affected
//                double taperPercent = (totalHeight < 6) ? 100 :
//                        Math.min(100, ((31 / (1 + Math.exp(-3 * (-Math.abs(originPos.getY() - y) + (totalHeight / 2f) - 2)))) + 70));
//
//                // Gets the radius and then multiplies that radius by a logistic regression from 100% to 55%
//                // based on the current and total vertical distance from the center. Tapers off the tops and bottoms!
//                radius = (float) ((avgDepositRadius +
//                        ConfiguredBlobNoise.blobRadiusNoise(areaPos.getX(), y, areaPos.getZ()) * VARIANCE)
//                        * (taperPercent / 100f));
                // TODO ABOVE THIS SUCKS

                float radius = (BlobNoise.blobWarpRadiusNoise((areaPos.getX() * 5), (y * 6), (areaPos.getZ() * 5)) * VARIANCE)
                        + avgDepositRadius;

                // Generate the ore block if within the radius and rolls a success against the density percent
                if ((rand.nextFloat() < adjDensityPercent) &&
                        (UtilMethods.getDistance2D(areaPos.getX(), areaPos.getZ(), centerPos.getX(), centerPos.getZ()) <= radius)) {
                    DepositUtil.processGeoMapEnqueue(reader.getSeedReader(), areaPos, this.ores.nextElt(), grade,
                            this.name, pos, geoMapBuilder);
                }
            }
            layerHeightCount++;

            // Check current layer height
            if (layerHeightCount >= layerHeightTotal) {
                layerHeightTotal = getLayerHeight(rand); // Set next layer height
                layerHeightCount = rand.nextInt(2) - 2; // Tells the loop to generate one or two spacers
            }
        }

        // Debug tool
        if (JemsGeoConfig.SERVER.debug_layer_deposits.get()) {
            for (int yPole = heightEnd; yPole < 120; yPole++) {
                reader.getSeedReader().setBlockState(new BlockPos(centerPos.getX(), yPole, centerPos.getZ()),
                        Blocks.RED_WOOL.getDefaultState(), 2);
            }
        }

        return true;
    }


    ///////////////////////
    // DEPOSIT UTILITIES //
    ///////////////////////

    // Setup: Gets a normally distributed radius around the given average radius
    private int getAvgRadius(Random rand) {
        int radius = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgRadius() / 3f)) + this.layerTemplate.getAvgRadius());
        return (radius <= 0) ? 1 : radius;

    }

    // Setup: Gets a normally distributed layer height around the given average layer height
    private int getLayerHeight(Random rand) {
        int height = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgLayerThick() / 2f)) + this.layerTemplate.getAvgLayerThick());
        return (height <= 0) ? 1 : height;
    }

}