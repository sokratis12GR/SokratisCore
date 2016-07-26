package sokratis12gr.sokratiscore.config;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * sokratis12gr.sokratiscore.config
 * SokratisCore created by sokratis12GR on 7/26/2016 2:11 PM.
 */
public interface ICustomRender {
    /**
     * Use this to register a custom renderer for the block.
     */
    @SideOnly(Side.CLIENT)
    public void registerRenderer(Feature feature);

    /**
     * Return true if the normal json model should still be registered for the item
     */
    public boolean registerNormal(Feature feature);
}