package sokratis12gr.sokratiscore.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * sokratis12gr.sokratiscore.network
 * SokratisCore created by sokratis12GR on 7/26/2016 12:45 PM.
 */
public abstract class MessageHandlerWrapper<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY> {

    @Override
    public REPLY onMessage(REQ message, MessageContext ctx) {

        PacketSyncObject<REQ, REPLY> syncObject = new PacketSyncObject<REQ, REPLY>(message, ctx) {
            @Override
            public void run() {
                reply = handleMessage(message, ctx);
            }
        };

        if (ctx.side == Side.CLIENT) {
            syncObject.addPacketClient();
        } else {
            syncObject.addPacketServer();
        }

        //TODO I dont think this will work because reply will be null until the run method is actually called...
        return syncObject.reply;
    }

    public abstract REPLY handleMessage(REQ message, MessageContext ctx);

}