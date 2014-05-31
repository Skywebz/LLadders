package se.luppii.ladders.tile;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMachineBase extends TileEntity {
	
	private ForgeDirection facingDirection;
	private static final int[][] texturePattern = new int[][] {
		
		{ 0, 1, 2, 3, 4, 5 }, // D
		{ 0, 1, 2, 3, 4, 5 }, // U
		{ 0, 1, 2, 3, 4, 5 }, // N
		{ 0, 1, 3, 2, 5, 4 }, // S
		{ 0, 1, 5, 4, 2, 3 }, // W
		{ 0, 1, 4, 5, 3, 2 }, // E
		
	};
	
	public ForgeDirection getFacingDirection() {
		return facingDirection != null ? facingDirection : ForgeDirection.NORTH;
	}
	
	public int getRotatedSide(int side) {
		if (facingDirection == null) facingDirection = ForgeDirection.NORTH;
		return texturePattern[facingDirection.ordinal()][side];
	}
	
	public void setFacingDirection(int side) {
		facingDirection = ForgeDirection.getOrientation(side);
		if (worldObj != null) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("facing", this.getFacingDirection().ordinal());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		int facing = par1NBTTagCompound.getInteger("facing");
		setFacingDirection(facing);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("facing", getFacingDirection().ordinal());
	}
	
	@SideOnly(Side.CLIENT)
	public Object getGui(InventoryPlayer inventoryPlayer) {
		return null;
	}
}
