package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapability;
import com.jemmerl.jemsgeology.capabilities.world.watertable.IWaterTable;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability;
import com.jemmerl.jemsgeology.events.loot.StoneQuarryModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

//bus = Mod.EventBusSubscriber.Bus.MOD
@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new StoneQuarryModifier.Serializer().setRegistryName(
                new ResourceLocation(JemsGeology.MOD_ID, "quarry_stone_block")
        ));
    }

    // TODO this is the wrong event subscription. cant be mod event bus.
//    @SubscribeEvent
//    public static void updateWTDelta(@Nonnull final TickEvent.WorldTickEvent event) {
//        if (event.side.isClient()) return;
//
//        if ((event.world.getGameTime() % 1000) == 0) {
//            IWaterTable cap = event.world.getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY)
//                    .orElseThrow(() -> new RuntimeException("JemsGeo Water Table capability is null in \"updateWTDelta\" tick event..."));
//        }
//    }

}
