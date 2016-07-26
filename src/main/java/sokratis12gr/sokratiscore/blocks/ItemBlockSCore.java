package sokratis12gr.sokratiscore.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sokratis12gr.sokratiscore.config.FeatureWrapper;

import java.util.List;

/**
 * sokratis12gr.sokratiscore.blocks
 * SokratisCore created by sokratis12GR on 7/26/2016 2:16 PM.
 * This is the base item block for all custom item blocks.
 */
public class ItemBlockSCore extends ItemBlock {

    public ItemBlockSCore(Block block, FeatureWrapper feature) {
        super(block);
    }

    public ItemBlockSCore(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(BlockSCore.TILE_DATA_TAG)) {
            tooltip.add(I18n.format("info.de.hasSavedData.txt"));
        }
    }
}