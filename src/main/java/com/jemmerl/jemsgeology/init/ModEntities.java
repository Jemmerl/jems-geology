package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.entities.FallingCobbleEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, JemsGeology.MOD_ID);

    public static final RegistryObject<EntityType<FallingCobbleEntity>> FALLING_COBBLE =
            ENTITIES.register("falling_cobble_entity",
                    () -> EntityType.Builder
                            .<FallingCobbleEntity>create(FallingCobbleEntity::new, EntityClassification.MISC)
                            .size(0.98F, 0.98F)
                            .build(new ResourceLocation(JemsGeology.MOD_ID, "falling_cobble_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
