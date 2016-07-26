package sokratis12gr.sokratiscore.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import sokratis12gr.sokratiscore.config.FeatureWrapper;

/**
 * sokratis12gr.sokratiscore.blocks
 * SokratisCore created by sokratis12GR on 7/26/2016 2:17 PM.
 * Used for simple blocks that require some minor additional features such as blocks that have subtypes.
 */
public class ItemBlockBasic extends ItemBlockSCore {

    private FeatureWrapper feature;

    public ItemBlockBasic(Block block) {
        super(block);
    }

    public ItemBlockBasic(Block block, FeatureWrapper feature) {
        this(block);
        this.feature = feature;
        this.setHasSubtypes(feature.variantMap().length > 0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getHasSubtypes() && feature.nameMap.containsKey(stack.getItemDamage())) {
            return (super.getUnlocalizedName(stack) + "." + feature.nameMap.get(stack.getItemDamage())).replaceAll("=", ".").replaceAll(",", ".");
        } else return super.getUnlocalizedName(stack);
    }
}