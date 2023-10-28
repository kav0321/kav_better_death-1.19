package net.kav.kav_better_death.utils;

import net.minecraft.nbt.NbtCompound;

public class Soul_counterData {


    public static void setsoul(IEntityDataSaver player, boolean soul)
    {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("soul",soul);
    }
    public static boolean getsoul(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getBoolean("soul");
    }
}
