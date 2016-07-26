package sokratis12gr.sokratiscore.blocks.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyHelper;
import sokratis12gr.sokratiscore.util.ArrayUtils;
import sokratis12gr.sokratiscore.util.LogHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * sokratis12gr.sokratiscore.blocks.properties
 * SokratisCore created by sokratis12GR on 7/26/2016 2:33 PM.
 */
public class PropertyString extends PropertyHelper<String> {

    private final List<String> valuesSet;

    public PropertyString(String name, String... values) {
        super(name, String.class);
        valuesSet = new ArrayList<String>();
        Collections.addAll(valuesSet, ArrayUtils.arrayToLowercase(values));
    }

    @Override
    public Collection<String> getAllowedValues() {
        return ImmutableSet.copyOf(valuesSet);
    }

    @Override
    public Optional<String> parseValue(String value) {
        if (valuesSet.contains(value)) {
            return Optional.of(value);
        }
        return Optional.absent();
    }

    @Override
    public String getName(String value) {
        return value;
    }

    public int toMeta(String value) {
        return valuesSet.contains(value) ? valuesSet.indexOf(value) : 0;
    }

    public String fromMeta(int meta) {
        if (meta >= 0 && meta < valuesSet.size()) {
            return valuesSet.get(meta);
        }
        LogHelper.error("[PropertyString] Attempted to load property for invalid meta value");
        return valuesSet.get(0);
    }
}