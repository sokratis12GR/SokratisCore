package sokratis12gr.sokratiscore.config;

import net.minecraft.item.ItemBlock;

import java.util.HashMap;
import java.util.Map;

/**
 * sokratis12gr.sokratiscore.config
 * SokratisCore created by sokratis12GR on 7/26/2016 2:10 PM.
 */
public class FeatureWrapper {
    private final String name;
    private final boolean isActive;
    private final boolean isConfigurable;
    private final String[] variantMap;
    private final Class<? extends ItemBlock> itemBlockClass;

    public final Map<Integer, String> nameMap = new HashMap<Integer, String>();

    public FeatureWrapper(Feature feature) {
        this.name = feature.name();
        this.isActive = feature.isActive();
        this.isConfigurable = feature.isConfigurable();
        this.variantMap = feature.variantMap();
        this.itemBlockClass = feature.itemBlock();

        if (feature.variantMap().length > 0) {
            for (String s : feature.variantMap()) {
                int meta = Integer.parseInt(s.substring(0, s.indexOf(":")));
                nameMap.put(meta, s.substring(s.indexOf(":") + 1));
            }
        }
    }

    public String name() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isConfigurable() {
        return isConfigurable;
    }

    public String[] variantMap() {
        return variantMap;
    }

    public Class<? extends ItemBlock> getItemBlock() {
        return itemBlockClass;
    }

}