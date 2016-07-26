package sokratis12gr.sokratiscore.config;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sokratis12gr.sokratiscore.config
 * SokratisCore created by sokratis12GR on 7/26/2016 2:09 PM.
 * Used to identify a feature (item or block) that can be disabled via the config
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Feature {
    /**
     * used For All
     */
    public String name();

    /**
     * used For All
     */
    public boolean isActive() default true;

    /**
     * used For All
     */
    public boolean isConfigurable() default true;

    /**
     * used For All
     */
    public String[] variantMap() default {};

    /**
     * used For Items with a single variant
     */
    public String stateOverride() default "";

    /**
     * used For Blocks
     */
    public Class<? extends ItemBlock> itemBlock() default ItemBlock.class;

    /**
     * used For Blocks
     */
    public Class<? extends TileEntity> tileEntity() default TileEntity.class;

    /**
     * used For All
     * set to -1 to exclude from creative tab.
     */
    public int cTab() default 0;
}