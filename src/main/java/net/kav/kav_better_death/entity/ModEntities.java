package net.kav.kav_better_death.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.entity.client.Soul_Entity_Renderer;
import net.kav.kav_better_death.entity.custom.Soul_Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<Soul_Entity> SOUL_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Kav_better_death.MODID, "soul_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Soul_Entity::new).fireImmune()
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());


    public static void registerEntityserver()
    {
        FabricDefaultAttributeRegistry.register(ModEntities.SOUL_ENTITY, Soul_Entity.setAttributes());
    }
    public static void registerEntityclient()
    {
        EntityRendererRegistry.register(ModEntities.SOUL_ENTITY, Soul_Entity_Renderer::new);
    }
}
