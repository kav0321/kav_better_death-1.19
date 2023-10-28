package net.kav.kav_better_death.potionlist;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class potion_death {
    public static final ArrayList<String> potion_soul = new ArrayList<String>();
    public static final ArrayList<String> potion_short = new ArrayList<String>();

    public static final ArrayList<Integer> duration = new ArrayList<Integer>();
    public static final ArrayList<Integer> amplification = new ArrayList<Integer>();
    @Nullable
    public static ArrayList<String> getList(String string) {
        switch (string) {
            case "death_penalty":
                return potion_soul;
            case "short_death_penalty":
                return potion_short;
            default:
                return null;
        }
    }

    public static void clear()
    {
        potion_soul.clear();
        potion_short.clear();
    }

    public static void add(String category,String name_id, int durations, int amplifications)
    {
        switch (category)
        {
            case "death_penalty":
                potion_soul.add(name_id);
                break;
            case "armor":
                potion_short.add(name_id);
                duration.add(durations);
                amplification.add(amplifications);
                break;
            default:
                System.out.println("error_loading_list");
        }
    }

}
