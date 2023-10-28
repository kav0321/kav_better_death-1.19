package net.kav.kav_better_death.entity.client;

import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.entity.custom.Soul_Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Soul_Entity_Model extends AnimatedGeoModel<Soul_Entity> {

    @Override
    public Identifier getModelResource(Soul_Entity object) {
        //  System.out.println(object.getsequence()+ " c");

        return new Identifier(Kav_better_death.MODID,"geo/soul.geo.json");
    }

    @Override
    public Identifier getTextureResource(Soul_Entity object) {

        return new Identifier(Kav_better_death.MODID, "textures/entity/soul.png");
    }

    @Override
    public Identifier getAnimationResource(Soul_Entity animatable) {
        return new Identifier(Kav_better_death.MODID, "animations/soul.animation.json");
    }
}
