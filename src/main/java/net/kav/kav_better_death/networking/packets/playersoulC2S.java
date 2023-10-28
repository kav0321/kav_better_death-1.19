package net.kav.kav_better_death.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kav.kav_better_death.networking.ModMessages;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class playersoulC2S {


    public static void INIT(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {

        PacketByteBuf buffer = PacketByteBufs.create();
        boolean soul= Soul_counterData.getsoul(((IEntityDataSaver) player));
        buffer.writeBoolean(soul);

        Soul_counterData.setsoul(((IEntityDataSaver) player),soul);
        ServerPlayNetworking.send(player, ModMessages.SOUL,buffer);
    }
}
