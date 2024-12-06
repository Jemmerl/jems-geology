package com.jemmerl.jemsgeology.init;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_SPEC;

    private static final int stoneHardnessMultiplier = 1;
    private static final int stoneDepthMultiplier = 1;
    private static final int stoneDepthMultiplierStart = 50;

    public static ForgeConfigSpec.IntValue STONE_HARD_MULT;
    public static ForgeConfigSpec.IntValue STONE_DEPTH_MULT;
    public static ForgeConfigSpec.IntValue STONE_DEPTH_MULT_START;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Stone Property Options");
        STONE_HARD_MULT = builder.comment("hardness multiplier, set to 1 to disable")
                .defineInRange("stoneHardnessMultiplier", stoneHardnessMultiplier, 1, 2);
        STONE_DEPTH_MULT = builder.comment("depth multiplier, recommended 20, 1 to disable")
                .defineInRange("stoneDepthMultiplier", stoneDepthMultiplier, 1, 2);
        STONE_DEPTH_MULT_START = builder.comment("depth multiplier start, recommended y=50")
                .defineInRange("stoneDepthMultiplierStart", stoneDepthMultiplierStart, 0, 65535);
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
