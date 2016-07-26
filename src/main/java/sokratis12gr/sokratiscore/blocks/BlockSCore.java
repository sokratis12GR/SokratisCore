package sokratis12gr.sokratiscore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import sokratis12gr.sokratiscore.api.IDataRetainerTile;
import sokratis12gr.sokratiscore.util.ItemNBTHelper;

import java.util.List;

/**
 * sokratis12gr.sokratiscore.blocks
 * SokratisCore created by sokratis12GR on 7/26/2016 12:51 PM.
 */
public class BlockSCore extends Block {
    public static final String TILE_DATA_TAG = "SMTileData";
    protected boolean isFullCube = true;

    public BlockSCore() {
        this(Material.ROCK);
    }

    public BlockSCore(Material material) {
        super(material);
        this.setHardness(5F);
        this.setResistance(10F);
    }

    //region Rename field names
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        super.getSubBlocks(item, tab, list);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof IDataRetainerTile && ItemNBTHelper.getCompound(stack).hasKey(BlockSCore.TILE_DATA_TAG)) {
            ((IDataRetainerTile) tile).readDataFromNBT(ItemNBTHelper.getCompound(stack).getCompoundTag(BlockSCore.TILE_DATA_TAG));
        }
    }
    //endregion

    //region Setters & Getters
    public BlockSCore setHarvestTool(String toolClass, int level) {
        this.setHarvestLevel(toolClass, level);
        return this;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        ItemStack stack = super.getPickBlock(state, target, world, pos, player);

        if (stack.getItem() == Item.getItemFromBlock(this) && stack.getItem().getHasSubtypes()) {
            stack.setItemDamage(getMetaFromState(state));
        }

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof IDataRetainerTile) {
            NBTTagCompound customData = new NBTTagCompound();
            ((IDataRetainerTile) tileEntity).writeDataToNBT(customData);
            ItemNBTHelper.getCompound(stack).setTag(TILE_DATA_TAG, customData);
        }

        return stack;
    }

    public BlockSCore setIsFullCube(boolean value) {
        isFullCube = value;
        return this;
    }

    //endregion

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack heldStack) {
        if (te instanceof IDataRetainerTile) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(state.getBlock()));
            NBTTagCompound customData = new NBTTagCompound();
            ((IDataRetainerTile) te).writeDataToNBT(customData);
            ItemNBTHelper.getCompound(stack).setTag(TILE_DATA_TAG, customData);
            spawnAsEntity(world, pos, stack);
            world.removeTileEntity(pos);
        } else {
            super.harvestBlock(world, player, pos, state, te, heldStack);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return isFullCube;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return isFullCube;
    }
}
