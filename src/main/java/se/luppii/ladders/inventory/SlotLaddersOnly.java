package se.luppii.ladders.inventory;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import se.luppii.ladders.block.BlockGenericLadder;

public class SlotLaddersOnly extends Slot {

	public SlotLaddersOnly(IInventory par1IInventory, int par2, int par3, int par4) {

		super(par1IInventory, par2, par3, par4);
	}

	@Override
	public int getSlotStackLimit() {

		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {

		return Block.getBlockFromItem(par1ItemStack.getItem()) instanceof BlockGenericLadder;
	}
}
