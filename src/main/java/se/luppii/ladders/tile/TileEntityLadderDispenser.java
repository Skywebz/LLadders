package se.luppii.ladders.tile;

import se.luppii.ladders.LLadders;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityLadderDispenser extends TileEntityMachineBase implements ISidedInventory {

	private ItemStack[] inventory;

	// private ItemStack inputItem; //Not used for now. Maybe later to filter valid itemstacks for the given slot.
	private String name;

	private int mode;

	private int ticks;

	private boolean working;

	public TileEntityLadderDispenser() {

		name = "Ladder Dispenser";
		inventory = new ItemStack[getSizeInventory()];
		mode = 0;
	}

	public int getMode() {

		return mode;
	}

	public boolean isWorking() {

		return working;
	}

	public void setIsWorking(boolean work) {

		working = work;
	}

	public void setMode(int par1) {

		mode = par1;
	}

	@Override
	public int getSizeInventory() {

		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int i) {

		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {

		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				setInventorySlotContents(i, null);
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize <= 0) {
				inventory[i] = null;
			}
			markDirty();
			return itemstack1;
		}
		else {
			markDirty();
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {

		ItemStack slotContent = this.getStackInSlot(i);
		if (slotContent != null) {
			setInventorySlotContents(i, null);
		}
		return slotContent;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
		// If slot changed is 4 we need to mark the block to update as the texture used has most likely changed.
		if (i == 4)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {

		if (working && !worldObj.isRemote) { // Important to not run code on clients, block placement will be bugged for clients otherwise.
			ticks++;
			if (getActiveState()) {
				boolean did_work = false;
				if (mode == 1 || mode == 2) { // Place ladders. Mode 1 is up, mode 2 is down ladder placement.
					if (ticks == 6) {
						ticks = 0;
						for (int i = 0; i < 2; i++) {
							int direction = getForgeDirectionToInt(getFacingDirection());
							for (int slot = 0; slot < getSizeInventory() - 1; slot++) {
								ItemStack stack = getStackInSlot(slot);
								if (stack != null) { // If stack contain items.
									Block ladder = Block.getBlockFromItem(stack.getItem());
									if ((mode == 1 && ladder == LLadders.blockSturdyLadder)
											|| (mode == 2 && (ladder == LLadders.blockRopeLadder || ladder == LLadders.blockVineLadder))) { // If block in slot is the same as the ladder we are trying to place - continue.
										int dir = getLadderDir(ladder); // Direction in Y-axis we want to use. 1 is up, -1 is down. 0 is no movement, which means something is wrong.
										boolean can_place = canSetLadder(ladder, xCoord, yCoord + dir, zCoord, direction); // Flag to see if it is possible to put a ladder at the specific place.
										if (can_place && dir != 0) { // We have a ladder, and can place it down or up.
											ItemStack ladderStack = extractLadderFromDispenser(slot);
											if (ladderStack != null && ladderStack.stackSize > 0) {
												if (setLadder(ladderStack, xCoord, yCoord + dir, zCoord, direction)) {
													did_work = true;
													break;
												}
											}
										}
									}
								}
							}
							setMode(getMode() + 1); // This round of Ladder placement finished, go to next mode.
						}
					}
				}
				if (mode > 2) {
					if (did_work) { // If ladder placement is done but there's more work to do, reset to mode 1.
						ticks = 0;
						mode = 1;
					}
					else if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) { // Else if block is unpowered - turn machine off and start retracting ladders.
						ticks = 0;
						setActiveState(false);
					}
				}
			}
			else { // Retract ladders.
				if (ticks == 10) {
					ticks = 0;
					boolean finished = true;
					if (canRemoveLadder(xCoord, yCoord - 1, zCoord)) {
						this.removeLadder(xCoord, yCoord - 1, zCoord);
						finished = false;
					}
					if (canRemoveLadder(xCoord, yCoord + 1, zCoord)) {
						this.removeLadder(xCoord, yCoord + 1, zCoord);
						finished = false;
					}
					if (finished) {
						mode = 0;
						working = false;
					}
				}
			}
			if (ticks > 10) {
				ticks = 0;
			}
		}
	}

	private int getLadderDir(Block ladder) {

		if (ladder == LLadders.blockRopeLadder || ladder == LLadders.blockVineLadder) {
			return -1;
		}
		else if (ladder == LLadders.blockSturdyLadder) {
			return 1;
		}
		return 0;
	}

	private boolean canRemoveLadder(int x, int y, int z) {

		Block block = worldObj.getBlock(x, y, z);
		return block == LLadders.blockRopeLadder || block == LLadders.blockSturdyLadder || block == LLadders.blockVineLadder;
	}

	private boolean canSetLadder(Block ladder, int x, int y, int z, int direction) {

		if (y >= worldObj.getHeight() - 1 || y < 0)
			return false; // Make sure that we're not trying to place ladders out of the world.
		Block block = worldObj.getBlock(x, y, z);
		if (block == ladder) {
			int dir;
			if (block == LLadders.blockRopeLadder || block == LLadders.blockVineLadder)
				dir = -1;
			else if (block == LLadders.blockSturdyLadder)
				dir = 1;
			else
				return false; // Safety measure, should never happen.
			return canSetLadder(ladder, x, y + dir, z, direction);
		}
		else if (!worldObj.isAirBlock(x, y, z))
			return false;
		return true;
	}

	private void removeLadder(int x, int y, int z) {

		Block block = worldObj.getBlock(x, y, z);
		int metadata = worldObj.getBlockMetadata(x, y, z);
		if (block != LLadders.blockRopeLadder && block != LLadders.blockSturdyLadder && block != LLadders.blockVineLadder) {
			return;
		}
		else if (worldObj.getBlock(x, y - 1, z) == LLadders.blockRopeLadder || worldObj.getBlock(x, y - 1, z) == LLadders.blockVineLadder) { // We want to retract from bottom and up.
			removeLadder(x, y - 1, z);
		}
		else if (worldObj.getBlock(x, y + 1, z) == LLadders.blockSturdyLadder) { // Or from the top down if sturdy ladders.
			removeLadder(x, y + 1, z);
		}
		else {
			worldObj.setBlockToAir(x, y, z);
			worldObj.removeTileEntity(x, y, z);
			ItemStack itemstack = new ItemStack(block, 1, metadata & 12);
			if (!this.insertLadderToDispenser(itemstack)) {
				dropBlockAsItem(x, y, z, itemstack);
			}
		}
	}

	private boolean setLadder(ItemStack stack, int x, int y, int z, int meta) {

		if (stack != null && stack.stackSize > 0 && !worldObj.isRemote) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if (worldObj.isAirBlock(x, y, z) && worldObj.getActualHeight() >= y) {
				worldObj.setBlock(x, y, z, block, meta, 2);
				return true;
			}
			if (block == LLadders.blockRopeLadder || block == LLadders.blockVineLadder) {
				return setLadder(stack, x, y - 1, z, meta);
			}
			else if (block == LLadders.blockSturdyLadder) {
				return setLadder(stack, x, y + 1, z, meta);
			}
		}
		return false;
	}

	private boolean insertLadderToDispenser(ItemStack itemstack) {

		if (this.isItemStackInDispenser(itemstack)) {
			for (int i = 0; i < getSizeInventory() - 1; i++) {
				if (getStackInSlot(i) != null && getStackInSlot(i).isItemEqual(itemstack) && isItemValidForSlot(i, itemstack)
						&& getStackInSlot(i).stackSize < getInventoryStackLimit()) {
					ItemStack stack = new ItemStack(itemstack.getItem(), getStackInSlot(i).stackSize + 1, itemstack.getItemDamage());
					setInventorySlotContents(i, stack);
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < getSizeInventory() - 1; i++) {
				if (getStackInSlot(i) == null) {
					setInventorySlotContents(i, itemstack);
					return true;
				}
			}
		}
		return false;
	}

	private boolean isItemStackInDispenser(ItemStack stack) {

		for (int i = 0; i < getSizeInventory() - 1; i++) {
			ItemStack tempStack = getStackInSlot(i);
			if (tempStack != null && tempStack.isItemEqual(stack) && tempStack.stackSize < getInventoryStackLimit())
				return true;
		}
		return false;
	}

	private ItemStack extractLadderFromDispenser(int slot) {

		ItemStack stack = this.getStackInSlot(slot);
		if ((stack != null)
				&& (stack.isItemEqual(new ItemStack(LLadders.blockRopeLadder)) || stack.isItemEqual(new ItemStack(LLadders.blockSturdyLadder)) || stack
						.isItemEqual(new ItemStack(LLadders.blockVineLadder)))) {
			return this.decrStackSize(slot, 1);
		}
		else {
			return null;
		}
	}

	private void dropBlockAsItem(int x, int y, int z, ItemStack itemstack) {

		if (!worldObj.isRemote && worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = (double) (worldObj.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d1 = (double) (worldObj.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d2 = (double) (worldObj.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			EntityItem ei = new EntityItem(worldObj, (double) x + d0, (double) y + d1, (double) z + d2, itemstack);
			ei.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(ei);
		}
	}

	@Override
	public String getInventoryName() {

		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {

		return false;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		ItemStack slotContent = this.getStackInSlot(i);
		if (slotContent == null)
			return true;
		if (itemstack != null && slotContent.isItemEqual(itemstack)) {
			if (itemstack.getTagCompound() == null && slotContent.getTagCompound() == null)
				return true;
			if (itemstack.getTagCompound() == null || slotContent.getTagCompound() == null)
				return false;
			return itemstack.getTagCompound().equals(slotContent.getTagCompound());
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {

		int[] slots = new int[getSizeInventory() - 1];
		return slots;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {

		if (itemstack != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {

		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {

		super.readFromNBT(par1NBTTagCompound);
		inventory = new ItemStack[getSizeInventory()];
		NBTTagList nbttaglist;
		mode = (int) par1NBTTagCompound.getByte("mode");
		working = par1NBTTagCompound.getBoolean("working");
		if (par1NBTTagCompound.hasKey("Items")) {
			nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound slot = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				int j = slot.getByte("Slot") & 0xff;
				if (j >= 0 && j < inventory.length) {
					inventory[j] = ItemStack.loadItemStackFromNBT(slot);
				}
			}
		}
		markDirty();
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {

		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist;
		if (inventory.length > 0) {
			nbttaglist = new NBTTagList();
			par1NBTTagCompound.setByte("mode", (byte) mode);
			par1NBTTagCompound.setBoolean("working", working);
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null && inventory[i].stackSize > 0) {
					NBTTagCompound slot = new NBTTagCompound();
					slot.setByte("Slot", (byte) i);
					inventory[i].writeToNBT(slot);
					nbttaglist.appendTag(slot);
				}
			}
			par1NBTTagCompound.setTag("Items", nbttaglist);
		}
	}
}
