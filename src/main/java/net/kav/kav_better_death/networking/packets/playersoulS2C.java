package net.kav.kav_better_death.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class playersoulS2C {


    public static void soulS2C(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {

        Soul_counterData.setsoul(((IEntityDataSaver) client.player),buf.readBoolean());
        System.out.println(Soul_counterData.getsoul((IEntityDataSaver) client.player));
    }


}
