package sokratis12gr.sokratiscore.config;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sokratis12gr.sokratiscore.blocks.ItemBlockSCore;
import sokratis12gr.sokratiscore.util.LogHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sokratis12gr.sokratiscore.config
 * SokratisCore created by sokratis12GR on 7/26/2016 2:13 PM.
 * Features Refer to Blocks and Items.
 * This class is responsible for the registration of all features in a mod including registering rendering.
 * It also generates and reads configs for all features as it registers (Or dose not register) them.
 * And it stores a list of all features registered by every mod using this class for feature registration.
 */
public class ModFeatureParser {

    private String modid;
    private CreativeTabs[] modTabs;
    private static final String CATEGORY_BLOCKS = "Blocks";
    private static final String CATEGORY_ITEMS = "Items";

    private static final Map<Object, Boolean> featureStates = new HashMap<Object, Boolean>();
    private static final List<FeatureEntry> featureEntries = new ArrayList<FeatureEntry>();

    /**
     * @param modid modid of the mod implementing this instance of ModFeatureParser
     * @param modTabs list of creative tabs that belong to the mod
     */
    public ModFeatureParser(String modid, CreativeTabs[] modTabs) {
        this.modid = modid;
        this.modTabs = modTabs;
    }

    public void loadFeatures(Class collection) {
        for (Field field : collection.getFields()) {
            if (field.isAnnotationPresent(Feature.class)) {
                try {
                    featureEntries.add(new FeatureEntry(field.get(null), field.getAnnotation(Feature.class)));
                }
                catch (IllegalAccessException e) {
                    LogHelper.error("Error Loading Feature!!! [" + field.getAnnotation(Feature.class).name() + "]");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Generates or reads the config setting for every loaded feature.
     * Must be called AFTER loadFeatures
     * @param configuration the mods configuration
     */
    public void loadFeatureConfig(Configuration configuration) {
        try {
            for (FeatureEntry entry : featureEntries) {
                if (!entry.feature.isConfigurable()) {
                    featureStates.put(entry.featureObj, true);
                    continue;
                }

                String category = entry.featureObj instanceof Block ? CATEGORY_BLOCKS : CATEGORY_ITEMS;

                entry.enabled = configuration.get(category, entry.feature.name(), entry.feature.isActive()).getBoolean(entry.feature.isActive());
                featureStates.put(entry.featureObj, entry.enabled);
            }

        }
        catch (Exception var4) {
            LogHelper.error("Error Loading Block/Item Config");
            var4.printStackTrace();
        }
        finally {
            if (configuration.hasChanged()) configuration.save();
        }
    }

    /**
     * Registers all features that are not disabled via the config.
     * Must be called AFTER loadFeatureConfig
     */
    public void registerFeatures() {
        for (FeatureEntry entry : featureEntries) {
            if (!entry.enabled) continue;

            if (entry.featureObj instanceof ICustomRegistry) {
                ((ICustomRegistry) entry.featureObj).registerFeature(entry.feature);
                continue;
            }

            if (entry.featureObj instanceof Block) {
                Block block = (Block) entry.featureObj;
                block.setRegistryName(entry.feature.name());
                block.setUnlocalizedName(modid.toLowerCase() + ":" + entry.feature.name());

                if (entry.feature.cTab() >= 0 && entry.feature.cTab() < modTabs.length) {
                    block.setCreativeTab(modTabs[entry.feature.cTab()]);
                }

                if (ItemBlockSCore.class.isAssignableFrom(entry.feature.itemBlock())) {
                    GameRegistry.register(block);

                    try {
                        Constructor<? extends ItemBlock> constructor = entry.feature.itemBlock().getConstructor(Block.class, FeatureWrapper.class);
                        ItemBlock itemBlock = constructor.newInstance(block, new FeatureWrapper(entry.feature));
                        itemBlock.setRegistryName(block.getRegistryName());
                        GameRegistry.register(itemBlock);
                    }
                    catch (Exception e) {
                        LogHelper.error("Well... It broke... [%s]", entry.feature.name());
                        e.printStackTrace();
                    }

                } else {
                    GameRegistry.register(block);
                    GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
                }

                if (block.hasTileEntity(block.getDefaultState())) {
                    if (block instanceof IRegisterMyOwnTiles) {
                        ((IRegisterMyOwnTiles) block).registerTiles(modid.toLowerCase() + ":", entry.feature.name());
                    } else {
                        GameRegistry.registerTileEntity(entry.feature.tileEntity(), modid.toLowerCase() + ":" + entry.feature.name());
                    }
                }
            } else if (entry.featureObj instanceof Item) {
                Item item = (Item) entry.featureObj;
                item.setRegistryName(entry.feature.name());
                item.setUnlocalizedName(modid.toLowerCase() + ":" + entry.feature.name());

                if (entry.feature.cTab() >= 0 && entry.feature.cTab() < modTabs.length) {
                    item.setCreativeTab(modTabs[entry.feature.cTab()]);
                }

                GameRegistry.register(item);
            }
        }
    }

    /**
     * Registers the rendering for all loaded and enabled features.
     * Must be called AFTER registerFeatures, during Pre Initialization and from your Client Proxy
     */
    @SideOnly(Side.CLIENT)
    public void registerRendering() {
        for (FeatureEntry entry : featureEntries) {
            if (!entry.enabled) continue;

            if (entry.featureObj instanceof ICustomRender) {
                ICustomRender customRender = (ICustomRender) entry.featureObj;
                customRender.registerRenderer(entry.feature);

                if (!customRender.registerNormal(entry.feature)) {
                    continue;
                }
            }

            if (entry.featureObj instanceof Block) {
                Block block = (Block) entry.featureObj;

                if (entry.feature.variantMap().length > 0) {
                    registerVariants(Item.getItemFromBlock(block), entry.feature);
                } else {
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid.toLowerCase() + ":" + entry.feature.name()));
                }
            } else if (entry.featureObj instanceof Item) {
                Item item = (Item) entry.featureObj;

                if (!entry.feature.stateOverride().isEmpty()) {
                    String s = entry.feature.stateOverride().substring(0, entry.feature.stateOverride().indexOf("#"));
                    s += entry.feature.stateOverride().substring(entry.feature.stateOverride().indexOf("#")).toLowerCase();
                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modid.toLowerCase() + ":" + s));
                } else if (entry.feature.variantMap().length > 0) {
                    registerVariants(item, entry.feature);
                } else {
                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modid.toLowerCase() + ":" + entry.feature.name()));
                }
            }
        }
    }

    private void registerVariants(Item item, Feature feature) {
        for (String s : feature.variantMap()) {
            int meta = Integer.parseInt(s.substring(0, s.indexOf(":")));
            String fullName = modid.toLowerCase() + ":" + feature.name();
            String variant = s.substring(s.indexOf(":") + 1);
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(fullName, variant));
        }
    }

    /**
     * Returns true if this object has been registered with a ModFeatureParser
     * */
    public static boolean isFeature(Object object) {
        return featureStates.containsKey(object);
    }

    /**
     * Returns true if feature is enabled. Applies to all mods using a ModFeatureParser instance
     * */
    public static boolean isEnabled(Object feature) {
        if (!featureStates.containsKey(feature)) {
            return false;
        }
        else {
            return featureStates.get(feature);
        }
    }

    private static class FeatureEntry {

        private final Object featureObj;
        private final Feature feature;
        public boolean enabled;

        private FeatureEntry(Object featureObj, Feature feature) {

            this.featureObj = featureObj;
            this.feature = feature;
            this.enabled = feature.isActive();
        }
    }
}