package com.jemmerl.jemsgeology.init;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, JemsGeology.MOD_ID);

    // TODO need to test this happens after other mods put ores in.
    public static final ImmutableMap<String, OreType> registeredOres = GeoOreRegistryAPI.getRegisteredOres();

    public static final LinkedHashMap<GeoType, GeoRegistry> GEOBLOCKS = new LinkedHashMap<>();
    static {
        for (GeoType geoType : GeoType.values()) {
            GEOBLOCKS.put(geoType, new GeoRegistry(geoType));
        }
    }

//    public static final HashMap<GeologyType, GeoRegistry> GEOBLOCKS = new HashMap<>();
//    static {
//        for (GeologyType geologyType: GeologyType.values()) {
//            GEOBLOCKS.put(geologyType, new GeoRegistry(geologyType));
//        }
//    }


    public static void register(IEventBus eventBus) {
        System.out.println(registeredOres.size());
        BLOCKS.register(eventBus);
    }

}
