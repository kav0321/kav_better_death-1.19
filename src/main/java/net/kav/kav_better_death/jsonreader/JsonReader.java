package net.kav.kav_better_death.jsonreader;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class JsonReader {

    public static void init()
    {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new deathloader());

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {

        });
    }
}
