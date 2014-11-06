package se.luppii.ladders.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBridgeBuilder extends TileEntityMachineBase implements ISidedInventory {

	private ItemStack[] inventory;

	private ItemStack bridgeStack;

	private String name;

	private int mode;

	private int ticks;

	private int blocksPlaced;

	private int maxLength;

	private boolean working;

	public TileEntityBridgeBuilder() {

		name = "Bridge Builder";
		inventory = new ItemStack[getSizeInventory()];
		mode = 0;
		maxLength = 16;
		bridgeStack = null;
	}

	public int getMode() {

		return mode;
	}

	public boolean isWorking() {

		return working;
	}

	public boolean isExtended() {

		return blocksPlaced > 0;
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
			if (getActiveState()) { // Block placement mode
				boolean did_work = false;
				if (mode == 1) {
					if (ticks >= 6) {
						ticks = 0;
						int slot = 0;
						ItemStack stack = getStackInSlot(slot);
						if (stack != null && stack.stackSize > 0 && blocksPlaced < maxLength) { // If stack contain items and extension has not yet reached max length.
							Block block = Block.getBlockFromItem(stack.getItem());
							if (block.renderAsNormalBlock()) {
								ForgeDirection dir = getFacingDirection();
								if (canPlaceBlock(block, stack.getItemDamage(), xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir)) {
									if (placeBlock(block, stack.getItemDamage(), xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir)) {
										blocksPlaced++;
										bridgeStack = stack.copy();
										bridgeStack.stackSize = 1;
										decrStackSize(slot, 1);
										did_work = true;
									}
								}
							}
						}
						mode = 0;
					}
				}
				if (mode == 0) {
					if (did_work) {
						ticks = 0;
						mode = 1;
					}
					else if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) { // Else if block is unpowered - turn machine off and start retracting.
						ticks = 0;
						setActiveState(false);
					}
				}
			}
			else { // Retractment mode
				if (ticks == 10) {
					ticks = 0;
					if (blocksPlaced > 0) {
						ForgeDirection dir = getFacingDirection();
						removeBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
						blocksPlaced--;
					}
					else {
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

	private boolean canPlaceBlock(Block block, int meta, int x, int y, int z, ForgeDirection dir) {

		if (y >= worldObj.getHeight() - 1 || y < 0)
			return false; // Make sure that we're not trying to place ladders out of the world.
		Block targetBlock = worldObj.getBlock(x, y, z);
		int targetBlockMeta = worldObj.getBlockMetadata(x, y, z);
		if (block == targetBlock && meta == targetBlockMeta)
			return canPlaceBlock(block, meta, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
		else if (block.canPlaceBlockAt(worldObj, x, y, z))
			return true;
		else if (!worldObj.isAirBlock(x, y, z))
			return false;
		return true;
	}

	private void removeBlock(int x, int y, int z, ForgeDirection dir) {

		Block block = worldObj.getBlock(x, y, z);
		int metadata = worldObj.getBlockMetadata(x, y, z);
		if (Block.getBlockFromItem(bridgeStack.getItem()) != block || bridgeStack.getItemDamage() != metadata)
			return;
		if (bridgeStack != null && bridgeStack.stackSize > 0) {
			if (Block.getBlockFromItem(bridgeStack.getItem()) == worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
					&& bridgeStack.getItemDamage() == worldObj.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
				removeBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
			else {
				worldObj.setBlockToAir(x, y, z);
				worldObj.removeTileEntity(x, y, z);
				ItemStack stack = new ItemStack(block, 1, metadata);
				if (!insertItemStack(stack))
					dropBlockAsItem(x, y, z, stack);
			}
		}
	}

	private boolean placeBlock(Block block, int meta, int x, int y, int z, ForgeDirection dir) {

		if (y >= worldObj.getHeight() - 1 || y < 0)
			return false; // Make sure that we're not trying to place ladders out of the world.
		Block targetBlock = worldObj.getBlock(x, y, z);
		int targetBlockMeta = worldObj.getBlockMetadata(x, y, z);
		if (block == targetBlock && meta == targetBlockMeta)
			return placeBlock(block, meta, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
		else if (block.canPlaceBlockAt(worldObj, x, y, z)) {
			worldObj.setBlock(x, y, z, block);
			worldObj.setBlockMetadataWithNotify(x, y, z, meta, 2);
			return true;
		}
		else if (!worldObj.isAirBlock(x, y, z))
			return false;
		worldObj.setBlock(x, y, z, block);
		worldObj.setBlockMetadataWithNotify(x, y, z, meta, 2);
		return true;
	}

	private boolean insertItemStack(ItemStack stack) {

		if (inventory[0] == null) {
			setInventorySlotContents(0, stack);
			return true;
		}
		else if (inventory[0] != null && inventory[0].isItemEqual(stack) && inventory[0].stackSize < getInventoryStackLimit()) {
			ItemStack itemstack = new ItemStack(stack.getItem(), inventory[0].stackSize + 1, stack.getItemDamage());
			setInventorySlotContents(0, itemstack);
			return true;
		}
		return false;
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
		blocksPlaced = (int) par1NBTTagCompound.getByte("blocksPlaced");
		NBTTagCompound bridgeInv = (NBTTagCompound) par1NBTTagCompound.getTag("bridgeBlock");
		if (bridgeInv != null) {
			bridgeStack = ItemStack.loadItemStackFromNBT(bridgeInv);
		}
		if (bridgeStack == null && getStackInSlot(0) != null) {
			bridgeStack = getStackInSlot(0).copy();
			bridgeStack.stackSize = 1;
		}
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
			par1NBTTagCompound.setByte("blocksPlaced", (byte) blocksPlaced);
			if (bridgeStack != null) {
				NBTTagCompound bridgeInv = new NBTTagCompound();
				bridgeStack.writeToNBT(bridgeInv);
				par1NBTTagCompound.setTag("bridgeBlock", bridgeInv);
			}
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
