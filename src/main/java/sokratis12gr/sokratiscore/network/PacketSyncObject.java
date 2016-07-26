package sokratis12gr.sokratiscore.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import sokratis12gr.sokratiscore.util.LogHelper;

/**
 * sokratis12gr.sokratiscore.network
 * SokratisCore created by sokratis12GR on 7/26/2016 12:46 PM.
 */
public abstract class PacketSyncObject<REQ extends IMessage, REPLY extends IMessage> implements Runnable {

    public final REQ message;
    public final MessageContext ctx;
    public REPLY reply;

    public PacketSyncObject(REQ message, MessageContext ctx) {
        this.message = message;
        this.ctx = ctx;
    }

    @Override
    public abstract void run();

    public void addPacketServer() {
        if (ctx.side == Side.CLIENT) {
            LogHelper.error("[SyncPacket#addPacketServer] HEY!!! I caught you this time! WRONG SIDE!!!! - " + message.getClass());
            return;
        }
        ctx.getServerHandler().playerEntity.getServer().addScheduledTask(this);
    }

    public void addPacketClient() {
        if (ctx.side == Side.SERVER) {
            LogHelper.error("[SyncPacket#addPacketClient] HEY!!! I caught you this time! WRONG SIDE!!!! - " + message.getClass());
            return;
        }
        Minecraft.getMinecraft().addScheduledTask(this);
    }
}