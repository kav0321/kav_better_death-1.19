package net.kav.kav_better_death.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.items.custom.Effigy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {


    public static final Item EFFIGY_SOUL= registerItem("effigy_soul",new Effigy(new FabricItemSettings().group(ItemGroup.MISC).maxCount(3)));
    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registry.ITEM, new Identifier(Kav_better_death.MODID, name),item);
    }

    public static void registerModItems()
    {
        Kav_better_death.LOGGER.debug("Rendering Mod Item for "+Kav_better_death.MODID);
    }
}
