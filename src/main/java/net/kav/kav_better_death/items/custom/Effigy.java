package net.kav.kav_better_death.items.custom;

import net.kav.kav_better_death.utils.HealthData;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Effigy extends Item {
    public Effigy(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        // Perform the function when right-clicking with the item in hand.
        // This method is triggered when the player right-clicks while holding the item.
        // For example, you can play a sound or activate a special effect.
        // Return TypedActionResult.success(itemStack) if the action was successful and you want to keep the item,
        // or TypedActionResult.consume(itemStack) if you want to consume (remove) the item from the player's hand.
        // Example:
        // player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
        // return TypedActionResult.success(player.getStackInHand(hand));

        // Replace the example code above with your desired functionality.
        // For demonstration purposes, we'll just play a sound and keep the item.
        if(!player.world.isClient())
        {
            double maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
            double targetMaxHealth = HealthData.gethealth((IEntityDataSaver) player);
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(maxHealth+targetMaxHealth);
            HealthData.sethealth((IEntityDataSaver) player,0);
        }
        Soul_counterData.setsoul(((IEntityDataSaver) player),false);
        player.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1.0f);
       player.getMainHandStack().setCount(player.getMainHandStack().getCount()-1);

        return TypedActionResult.consume(player.getStackInHand(hand));
    }
}
