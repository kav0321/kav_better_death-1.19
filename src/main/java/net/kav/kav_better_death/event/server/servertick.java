package net.kav.kav_better_death.event.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kav.kav_better_death.potionlist.potion_death;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class servertick implements ServerTickEvents.EndWorldTick{

    @Override
    public void onEndTick(ServerWorld world) {
       for(ServerPlayerEntity player: world.getServer().getPlayerManager().getPlayerList())
        {
            if(Soul_counterData.getsoul(((IEntityDataSaver) player)))
            {
                for(String potion: potion_death.potion_soul)
                {
                    applyStatusEffect(player,potion,0,0);
                }
            }
        }
    }

    public void applyStatusEffect(PlayerEntity player, String statusEffectId, int duration, int amplifier) {
        String id1= splitStringAtLastColon(statusEffectId)[0];
        String id2= splitStringAtLastColon(statusEffectId)[1];
        Identifier statusEffectIdentifier = new Identifier(id1,id2);
        StatusEffect statusEffect = Registry.STATUS_EFFECT.get(statusEffectIdentifier);

        if (statusEffect != null) {
            StatusEffectInstance effectInstance = new StatusEffectInstance(statusEffect, duration, amplifier);
            player.addStatusEffect(effectInstance);
        } else {
            // Handle the case where the status effect identifier is invalid or not registered.
            // For example, you can print an error message to the console.
            System.out.println("Invalid status effect identifier: " + statusEffectId);
        }
    }

    public static String[] splitStringAtLastColon(String input) {
        String[] result = new String[2];

        int lastColonIndex = input.lastIndexOf(":");
        if (lastColonIndex >= 0) {
            result[0] = input.substring(0, lastColonIndex);
            result[1] = input.substring(lastColonIndex + 1);
        } else {
            // If there's no ":" in the string, set both parts to the input string.
            result[0] = input;
            result[1] = input;
        }

        return result;
    }

}
