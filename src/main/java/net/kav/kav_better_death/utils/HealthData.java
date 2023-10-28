package net.kav.kav_better_death.utils;

import net.minecraft.nbt.NbtCompound;

public class HealthData {

    private static double Health;
    public static void addhealth(IEntityDataSaver player, double health)
    {
        NbtCompound nbt = player.getPersistentData();
        Health =nbt.getDouble("health_kav");

        Health=Health+health;
        nbt.putDouble("health_kav",health);
    }
    public static void sethealth(IEntityDataSaver player, double health)
    {
        NbtCompound nbt = player.getPersistentData();
        System.out.println(health+ " here");
        nbt.putDouble("health_kav",health);
    }
    public static double gethealth(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getDouble("health_kav");
    }
}
