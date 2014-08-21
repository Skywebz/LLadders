package se.luppii.ladders.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityLadderDispenser extends TileEntityMachineBase implements ISidedInventory {

	private ItemStack[] inventory;
	
	//private ItemStack inputItem;	// Not used for now. Maybe later to filter valid itemstacks for the given slot.
	private String name;
	private boolean active;
	private int mode;
	
	public TileEntityLadderDispenser() {
		name = "Ladder Dispenser";
		inventory = new ItemStack[getSizeInventory()];
		active = false;
		mode = 0;
	}
	
	public boolean getActiveState() {
		return active;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setActiveState(boolean par1) {
		active = par1;
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
		if (slotContent == null) return true;
		if (itemstack != null && slotContent.isItemEqual(itemstack)) {
			
			if (itemstack.getTagCompound() == null && slotContent.getTagCompound() == null) return true;
			if (itemstack.getTagCompound() == null || slotContent.getTagCompound() == null) return false;
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
		
		active = par1NBTTagCompound.getBoolean("active");
		mode = (int)par1NBTTagCompound.getByte("mode");
		
		if (par1NBTTagCompound.hasKey("Items")) {
			
			nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				
				NBTTagCompound slot = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
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
			
			par1NBTTagCompound.setBoolean("active", active);
			par1NBTTagCompound.setByte("mode", (byte)mode);
			
			for (int i = 0; i < inventory.length; i++) {
				
				if (inventory[i] != null && inventory[i].stackSize > 0) {
					
					NBTTagCompound slot = new NBTTagCompound();
					slot.setByte("Slot", (byte)i);
					inventory[i].writeToNBT(slot);
					nbttaglist.appendTag(slot);
				}
			}
			par1NBTTagCompound.setTag("Items", nbttaglist);
		}
	}
}
