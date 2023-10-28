package net.kav.kav_better_death.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.networking.packets.playersoulC2S;
import net.kav.kav_better_death.networking.packets.playersoulS2C;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier SOUL = new Identifier(Kav_better_death.MODID, "souling");
    public static final Identifier JOIN = new Identifier(Kav_better_death.MODID, "join_death");
    public static void registerC2SPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(ModMessages.JOIN, playersoulC2S::INIT);
    }


    public static void registerS2CPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.SOUL, playersoulS2C::soulS2C);
    }
}
