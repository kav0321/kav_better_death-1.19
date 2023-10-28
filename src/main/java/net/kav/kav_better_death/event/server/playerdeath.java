package net.kav.kav_better_death.event.server;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kav.kav_better_death.entity.ModEntities;
import net.kav.kav_better_death.entity.custom.Soul_Entity;
import net.kav.kav_better_death.networking.ModMessages;
import net.kav.kav_better_death.utils.HealthData;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

public class playerdeath implements ServerPlayerEvents.CopyFrom {


    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
      /*  PacketByteBuf buf = PacketByteBufs.create();
        boolean soul= true;
        buf.writeBoolean(soul);

        Soul_counterData.setsoul(((IEntityDataSaver) newPlayer),soul);
        ServerPlayNetworking.send(newPlayer, ModMessages.SOUL,buf);*/

        UUID playerUUID = newPlayer.getUuid();
        MinecraftServer server = newPlayer.getServer();

        if (server != null) {
            for (ServerWorld world : server.getWorlds()) {
                world.iterateEntities().forEach(entity -> {
                    if (entity instanceof Soul_Entity) {
                        Soul_Entity soulEntity = (Soul_Entity) entity;
                        UUID entityPlayerUUID = soulEntity.getPlayerUUID();

                        if (entityPlayerUUID != null && entityPlayerUUID.equals(playerUUID)) {
                            entity.kill(); // Kill the existing Soul_Entity with the specific UUID
                        }
                    }
                });
            }
        }

        if(!Soul_counterData.getsoul((IEntityDataSaver) oldPlayer))
        {
            Soul_Entity SOUL_APPEARING = ModEntities.SOUL_ENTITY.create(newPlayer.getWorld());
            SOUL_APPEARING.setPos(oldPlayer.getPos().getX(),oldPlayer.getPos().getY(),oldPlayer.getPos().getZ());
            SOUL_APPEARING.setPlayerUUID(newPlayer.getUuid());
            System.out.println(oldPlayer.experienceLevel);
            SOUL_APPEARING.setPlayerEXP(oldPlayer.experienceLevel);
            newPlayer.world.spawnEntity(SOUL_APPEARING);
        }

    }


}
