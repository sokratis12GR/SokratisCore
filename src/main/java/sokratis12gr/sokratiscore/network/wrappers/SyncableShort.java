package sokratis12gr.sokratiscore.network.wrappers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import sokratis12gr.sokratiscore.SokratisCore;
import sokratis12gr.sokratiscore.blocks.TileSCBase;
import sokratis12gr.sokratiscore.network.PacketSyncableObject;
import sokratis12gr.sokratiscore.util.LogHelper;

/**
 * Created by brandon3055 on 26/3/2016.
 */
public class SyncableShort extends SyncableObject {

    public short value;
    private short lastTickValue;

    public SyncableShort(short value, boolean syncInTile, boolean syncInContainer, boolean updateOnReceived) {
        super(syncInTile, syncInContainer, updateOnReceived);
        this.value = this.lastTickValue = value;
    }

    public SyncableShort(short value, boolean syncInTile, boolean syncInContainer) {
        super(syncInTile, syncInContainer);
        this.value = this.lastTickValue = value;
    }

    @Override
    public void detectAndSendChanges(TileSCBase tile, EntityPlayer player, boolean forceSync) {
        if (lastTickValue != value || forceSync) {
            lastTickValue = value;
            tile.dirtyBlock();
            if (player == null) {
                SokratisCore.network.sendToAllAround(new PacketSyncableObject(tile, index, value, updateOnReceived), tile.syncRange());
            } else if (player instanceof EntityPlayerMP) {
                SokratisCore.network.sendTo(new PacketSyncableObject(tile, index, value, updateOnReceived), (EntityPlayerMP) player);
            } else LogHelper.error("SyncableInt#detectAndSendChanges No valid destination for sync packet!");
        }
    }

    @Override
    public void updateReceived(PacketSyncableObject packet) {
        if (packet.dataType == PacketSyncableObject.SHORT_INDEX) {
            value = packet.shortValue;
        }
    }

    @Override
    public void toNBT(NBTTagCompound compound) {
        compound.setShort("SyncableShort" + index, value);
    }

    @Override
    public void fromNBT(NBTTagCompound compound) {
        if (compound.hasKey("SyncableShort" + index)) {
            value = compound.getShort("SyncableShort" + index);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
