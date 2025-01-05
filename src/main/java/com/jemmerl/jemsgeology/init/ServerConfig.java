package com.jemmerl.jemsgeology.init;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_SPEC;

    // Stone Property Options
    private static final int stoneHardnessMultiplier = 1;
    private static final int stoneDepthMultiplier = 1;
    private static final int stoneDepthMultiplierStart = 50;

    // Quarrying and Mining Options
    private static final boolean disableSilktouch = true;
    private static final boolean disableFortune = false;

    // Vanilla World-gen Options
    private static final boolean disableWaterLakes = true;
    private static final boolean disableLavaLakes = true;
    private static final boolean disableWaterSprings = true;
    private static final boolean disableLavaSprings = true;
    private static final boolean disableMineshafts = true;
    private static final boolean disableFossils = true;

    // Debug Options
    private static final boolean debugOreRegistry = true; // Debug ore-type registration methods/API - Default false
    private static final boolean debugFeatureRemover = false; // Debug the biome vanilla feature remover - Default false


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Stone Property Options
    public static ForgeConfigSpec.IntValue STONE_HARD_MULT;
    public static ForgeConfigSpec.IntValue STONE_DEPTH_MULT;
    public static ForgeConfigSpec.IntValue STONE_DEPTH_MULT_START;

    // Quarrying and Mining Options
    public static ForgeConfigSpec.BooleanValue DISABLE_SILK;
    public static ForgeConfigSpec.BooleanValue DISABLE_FORTUNE;

    // Vanilla World-gen Options
    public static ForgeConfigSpec.BooleanValue DISABLE_WATER_LAKES;
    public static ForgeConfigSpec.BooleanValue DISABLE_LAVA_LAKES;
    public static ForgeConfigSpec.BooleanValue DISABLE_WATER_SPRINGS;
    public static ForgeConfigSpec.BooleanValue DISABLE_LAVA_SPRINGS;
    public static ForgeConfigSpec.BooleanValue DISABLE_MINESHAFTS;
    public static ForgeConfigSpec.BooleanValue DISABLE_FOSSILS;

    // Debug Options
    public static ForgeConfigSpec.BooleanValue DEBUG_ORE_REGISTRY;
    public static ForgeConfigSpec.BooleanValue DEBUG_FEATURE_REMOVER;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        /////////////////////////////////////
        //      STONE PROPERTY OPTIONS     //
        /////////////////////////////////////

        builder.push("Stone Property Options");
        STONE_HARD_MULT = builder.comment("hardness multiplier, set to 1 to disable")
                .defineInRange("stoneHardnessMultiplier", stoneHardnessMultiplier, 1, 2);
        STONE_DEPTH_MULT = builder.comment("depth multiplier, recommended 20, 1 to disable")
                .defineInRange("stoneDepthMultiplier", stoneDepthMultiplier, 1, 2);
        STONE_DEPTH_MULT_START = builder.comment("depth multiplier start, recommended y=50")
                .defineInRange("stoneDepthMultiplierStart", stoneDepthMultiplierStart, 0, 65535);
        builder.pop();


        ///////////////////////////////////////
        //      QUARRYING/MINING OPTIONS     //
        ///////////////////////////////////////

        builder.push("Quarrying and Mining Options");
        DISABLE_SILK = builder.comment("Disable silktouch enchantment on geo-stone blocks? Forces needing a quarrying tool - Default true")
                .define("disableSilktouch", disableSilktouch);
        DISABLE_FORTUNE = builder.comment("Disable fortune enchantment on geo-stone blocks? Default - false")
                .define("disableFortune", disableFortune);
        builder.pop();


        ////////////////////////////////////////
        //      VANILLA WORLD-GEN OPTIONS     //
        ////////////////////////////////////////

        builder.push("Vanilla World-Gen Options");
        DISABLE_WATER_LAKES = builder.comment().define("disableWaterLakes", disableWaterLakes);
        DISABLE_LAVA_LAKES = builder.comment().define("disableLavaLakes", disableLavaLakes);
        DISABLE_WATER_SPRINGS = builder.comment().define("disableWaterSprings", disableWaterSprings);
        DISABLE_LAVA_SPRINGS = builder.comment().define("disableLavaSprings", disableLavaSprings);
        DISABLE_MINESHAFTS = builder.comment().define("disableMineshafts", disableMineshafts);
        DISABLE_FOSSILS = builder.comment().define("disableFossils", disableFossils);
        builder.pop();


        ////////////////////////////////
        //      DEBUGGING OPTIONS     //
        ////////////////////////////////

        builder.push("Debug Options");
        DEBUG_ORE_REGISTRY = builder.comment("Debug ore-type registration methods/API - Default false")
                        .define("debugOreRegistry", debugOreRegistry);
        DEBUG_FEATURE_REMOVER = builder.comment("Debug the biome vanilla feature remover - Default false")
                .define("debugFeatureRemover", debugFeatureRemover);
        builder.pop();


        SERVER_SPEC = builder.build();
    }


    public static void loadConfig(ForgeConfigSpec serverSpec, Path configPath) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(configPath).sync()
                .autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        serverSpec.setConfig(configData);
    }

}
