package com.jemmerl.jemsgeology.init.worldgen;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.world.placements.BottomCornerPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeaturePlacements {
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, JemsGeology.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> BOTTOM_CORNER_PLACEMENT
            = PLACEMENTS.register("bottom_corner_placement", () -> new BottomCornerPlacement(NoPlacementConfig.CODEC));

    public static void register(IEventBus eventBus) {
        PLACEMENTS.register(eventBus);
    }
}