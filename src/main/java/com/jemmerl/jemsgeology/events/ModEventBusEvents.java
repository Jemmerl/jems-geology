package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.events.loot.StoneQuarryModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new StoneQuarryModifier.Serializer().setRegistryName(
                new ResourceLocation(JemsGeology.MOD_ID, "quarry_stone_block")
        ));
    }
}
