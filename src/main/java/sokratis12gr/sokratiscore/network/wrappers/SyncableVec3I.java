package sokratis12gr.sokratiscore.network.wrappers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import sokratis12gr.sokratiscore.SokratisCore;
import sokratis12gr.sokratiscore.blocks.TileSCBase;
import sokratis12gr.sokratiscore.lib.Vec3I;
import sokratis12gr.sokratiscore.network.PacketSyncableObject;
import sokratis12gr.sokratiscore.util.LogHelper;

/**
 * Created by brandon3055 on 26/3/2016.
 */
public class SyncableVec3I extends SyncableObject {

    public Vec3I vec;
    private Vec3I lastTickVec;


    public SyncableVec3I(Vec3I vec, boolean syncInTile, boolean syncInContainer, boolean updateOnReceived) {
        super(syncInTile, syncInContainer, updateOnReceived);
        this.vec = vec;
        this.lastTickVec = vec;
    }

    public SyncableVec3I(Vec3I vec, boolean syncInTile, boolean syncInContainer) {
        super(syncInTile, syncInContainer);
        this.vec = vec;
        this.lastTickVec = vec;
    }

    @Override
    public void detectAndSendChanges(TileSCBase tile, EntityPlayer player, boolean forceSync) {
        if (!vec.equals(lastTickVec) || forceSync) {
            lastTickVec = vec.copy();
            tile.dirtyBlock();
            if (player == null) {
                SokratisCore.network.sendToAllAround(new PacketSyncableObject(tile, index, vec, updateOnReceived), tile.syncRange());
            } else if (player instanceof EntityPlayerMP) {
                SokratisCore.network.sendTo(new PacketSyncableObject(tile, index, vec, updateOnReceived), (EntityPlayerMP) player);
            } else LogHelper.error("SyncableInt#detectAndSendChanges No valid destination for sync packet!");
        }
    }

    @Override
    public void updateReceived(PacketSyncableObject packet) {
        if (packet.dataType == PacketSyncableObject.VEC3I_INDEX) {
            vec = packet.vec3I.copy();
        }
    }

    @Override
    public void toNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagInt(vec.x));
        list.appendTag(new NBTTagInt(vec.y));
        list.appendTag(new NBTTagInt(vec.z));

        compound.setTag("SyncableVec3I" + index, list);
    }

    @Override
    public void fromNBT(NBTTagCompound compound) {
        if (compound.hasKey("SyncableVec3I" + index)) {
            NBTTagList list = compound.getTagList("SyncableVec3I" + index, 3);
            if (list.tagCount() == 3) {
                vec.set(list.getIntAt(0), list.getIntAt(1), list.getIntAt(2));
            }
        }
    }

    @Override
    public String toString() {
        return vec.toString();
    }
}
