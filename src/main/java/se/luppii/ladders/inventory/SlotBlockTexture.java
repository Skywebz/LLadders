package se.luppii.ladders.inventory;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotBlockTexture extends Slot {

	public SlotBlockTexture(IInventory par1iInventory, int par2, int par3, int par4) {

		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public int getSlotStackLimit() {

		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {

		if (par1ItemStack.getItem() instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(par1ItemStack.getItem());
			return block.isOpaqueCube() && block.renderAsNormalBlock();
		}
		return false;
	}
}
