package net.kav.kav_better_death;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.kav.kav_better_death.entity.ModEntities;
import net.kav.kav_better_death.event.client.clientjoin;
import net.kav.kav_better_death.networking.ModMessages;

public class Kav_better_death_Client implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(new clientjoin());
        ModEntities.registerEntityclient();
        ModMessages.registerS2CPackets();
    }
}
