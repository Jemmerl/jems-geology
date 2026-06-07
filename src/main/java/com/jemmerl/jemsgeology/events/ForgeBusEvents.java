package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.capabilities.world.watertable.IWaterTableCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability;
import com.jemmerl.jemsgeology.init.NoiseInit;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {

    @SubscribeEvent
    public static void updateWTDelta(@Nonnull final TickEvent.WorldTickEvent event) {
        //TODO TEMP to disable
        if (true) return;

        if (event.side.isClient()) return;

        if (event.world.getDimensionKey().getLocation().toString().equals("minecraft:overworld") && (event.phase == TickEvent.Phase.END)) {
            // Every 2.5 minutes
            if ((event.world.getGameTime() % 600) == 0) {
                long TS = System.currentTimeMillis();
                IWaterTableCap cap = event.world.getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY)
                        .orElseThrow(() -> new RuntimeException("JemsGeo Water Table capability is null in \"updateWTDelta\" tick event..."));

                cap.updateWTMap(event.world);
                long TE = System.currentTimeMillis();
                System.out.println("Ran for: " + (TE-TS) + " ms");

                if (event.world.getGameTime() > 0) {
                    cap.reqPumpWater(new ChunkPos(0, 0), event.world, 40, true);
//                    cap.reqPumpWater(new ChunkPos(2, 0), event.world, 10, false);
//                    cap.reqPumpWater(new ChunkPos(0, 0), event.world, 13, false);
//                    cap.reqPumpWater(new ChunkPos(-2, -1), event.world, 10, false);
                }
            }
        }
    }
}
