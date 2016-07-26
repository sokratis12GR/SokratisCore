package sokratis12gr.sokratiscore.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * sokratis12gr.sokratiscore.items
 * SokratisCore created by sokratis12GR on 7/26/2016 2:36 PM.
 */
public class ItemSCore extends Item {

    public Map<Integer, String> nameMap = new HashMap<Integer, String>();

    public ItemSCore addName(int damage, String name) {
        nameMap.put(damage, name);
        return this;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getHasSubtypes() && nameMap.containsKey(stack.getItemDamage())) {
            return super.getUnlocalizedName(stack) + "." + nameMap.get(stack.getItemDamage());
        } else return super.getUnlocalizedName(stack);
    }
}