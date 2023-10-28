package net.kav.kav_better_death;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kav.kav_better_death.entity.ModEntities;
import net.kav.kav_better_death.event.server.playerdeath;
import net.kav.kav_better_death.event.server.playerdeathafter;
import net.kav.kav_better_death.event.server.servertick;
import net.kav.kav_better_death.items.ModItems;
import net.kav.kav_better_death.jsonreader.JsonReader;
import net.kav.kav_better_death.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kav_better_death implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID= "kav_better_death";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		JsonReader.init();

		ModEntities.registerEntityserver();
		ModMessages.registerC2SPackets();
		//server player stuff

		ModItems.registerModItems();
		ServerPlayerEvents.AFTER_RESPAWN.register(new playerdeathafter());
		ServerPlayerEvents.COPY_FROM.register(new playerdeath());
		ServerTickEvents.END_WORLD_TICK.register(new servertick());

		LOGGER.info("Hello Fabric world!");
	}

}
