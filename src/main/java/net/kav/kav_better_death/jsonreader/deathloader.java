package net.kav.kav_better_death.jsonreader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.potionlist.potion_death;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;

public class deathloader implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return new Identifier(Kav_better_death.MODID,"death_loader");
    }


    @Override
    public void reload(ResourceManager manager) {
        potion_death.clear();

        manager.findResources("death_penalty", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                if(data.get("category").getAsString().equals("death_penalty"))
                {
                    potion_death.add("death_penalty",data.get("name_id").getAsString(),0,0);



                } else if (data.get("category").getAsString().equals("short_death_penalty")) {
                    potion_death.add("short_death_penalty",data.get("name_id").getAsString(),data.get("duration").getAsInt(),data.get("amplification").getAsInt());
                }
                else
                {
                    Kav_better_death.LOGGER.error("Category named wrong");
                }


            }
            catch (Exception e)
            {
                Kav_better_death.LOGGER.error("ERROR LOADING THE STATS");
            }


        });
    }
}
