package com.jemmerl.jemsgeology.init;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_SPEC;





    public static void loadConfig(ForgeConfigSpec serverSpec, Path configPath) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(configPath).sync()
                .autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        serverSpec.setConfig(configData);
    }

}
