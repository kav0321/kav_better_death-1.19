package net.kav.kav_better_death.event.server;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.networking.ModMessages;
import net.kav.kav_better_death.utils.HealthData;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class playerdeathafter implements ServerPlayerEvents.AfterRespawn{

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        PacketByteBuf buf = PacketByteBufs.create();
        boolean soul= true;
        buf.writeBoolean(soul);

        Soul_counterData.setsoul(((IEntityDataSaver) newPlayer),soul);
        ServerPlayNetworking.send(newPlayer, ModMessages.SOUL,buf);



        Identifier customMaxHealthId = new Identifier(Kav_better_death.MODID, "custom_max_health");
        EntityAttributeInstance customMaxHealthAttribute = newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (customMaxHealthAttribute != null) {
            // Set the new max health value here, for example, half of the current max health
            double maxHealth = oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
            double targetMaxHealth = maxHealth / 2.0;
            HealthData.sethealth((IEntityDataSaver) newPlayer,targetMaxHealth+HealthData.gethealth((IEntityDataSaver) oldPlayer));
            newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(targetMaxHealth);
        }



    }
}
