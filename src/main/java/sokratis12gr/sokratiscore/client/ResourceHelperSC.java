package sokratis12gr.sokratiscore.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import sokratis12gr.sokratiscore.SokratisCore;

import java.util.HashMap;
import java.util.Map;

/**
 * sokratis12gr.sokratiscore.client
 * SokratisCore created by sokratis12GR on 7/26/2016 1:32 PM.
 */
public class ResourceHelperSC {

    public static final String RESOURCE_PREFIX = SokratisCore.MODID + ":";
    private static ResourceLocation vanillaParticles;
    private static Map<String, ResourceLocation> cachedResources = new HashMap<String, ResourceLocation>();

    public static void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }

    public static ResourceLocation getResource(String rs) {
        if (!cachedResources.containsKey(rs)) cachedResources.put(rs, new ResourceLocation(RESOURCE_PREFIX + rs));
        return cachedResources.get(rs);
    }

    public static ResourceLocation getResourceRAW(String rs) {
        if (!cachedResources.containsKey(rs)) cachedResources.put(rs, new ResourceLocation(rs));
        return cachedResources.get(rs);
    }

    public static void bindTexture(String rs) {
        bindTexture(getResource(rs));
    }
}