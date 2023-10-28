package net.kav.kav_better_death.entity.client;

import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.entity.custom.Soul_Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;
import net.minecraft.util.Identifier;
import java.util.function.Function;
import static net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature;

public class Soul_Entity_Renderer extends GeoEntityRenderer<Soul_Entity> {
    public Soul_Entity_Renderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new Soul_Entity_Model());
        Function<Identifier, RenderLayer> emissiveRenderTypeFunction = (identifier) -> {
            // You can return the appropriate RenderLayer for emissive rendering here
            // For example, if you want glowing eyes, you can use:
            // return RenderLayer.getEyes();
            // If you want glowing eye glints, you can use:
            // return RenderLayer.getEyesGlint();

            // Replace the following line with the desired RenderLayer based on your requirements
            return RenderLayer.getEyes(new Identifier(Kav_better_death.MODID, "textures/entity/soul.png"));
        };
      addLayer(new LayerGlowingAreasGeo(this,
                entity -> this.modelProvider.getTextureResource((Soul_Entity) entity),
                entity -> this.modelProvider.getModelResource((Soul_Entity) entity),
              emissiveRenderTypeFunction));

        this.shadowRadius = 0.4f;
    }
    public Identifier getTextureResource(Soul_Entity instance) {
        return new Identifier(Kav_better_death.MODID, "textures/entity/soul.png");
    }



    @Override
    public RenderLayer getRenderType(Soul_Entity entity, float partialticks, MatrixStack stack, VertexConsumerProvider rendertypebuffer, VertexConsumer vertexConsumer, int packedlightIn, Identifier texturelocate)
    {
        stack.scale(1.5f,1.5f,1.5f);

        return super.getRenderType(entity,partialticks,stack,rendertypebuffer,vertexConsumer,packedlightIn,texturelocate);
    }
}
