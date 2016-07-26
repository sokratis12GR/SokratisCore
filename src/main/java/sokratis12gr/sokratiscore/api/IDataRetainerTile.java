package sokratis12gr.sokratiscore.api;

import net.minecraft.nbt.NBTTagCompound;

/**
 * sokratis12gr.sokratiscore.api
 * SokratisCore created by sokratis12GR on 7/26/2016 1:02 PM.
 * Implemented by the TileEntity of blocks that need to retain custom data when harvested.
 */
public interface IDataRetainerTile {

    /**
     * Used to write custom tile specific data to NBT.
     * Data saved in this method will be synced with the client via description packets.
     * Data saved in this method will also be saved to the ItemBlock when the tile is harvested so it can be restored
     * when the tile is placed.
     */
    void writeDataToNBT(NBTTagCompound dataCompound);

    /**
     * This is where any data saved in writeDataToNBT should be loaded from NBT.
     */
    void readDataFromNBT(NBTTagCompound dataCompound);
}