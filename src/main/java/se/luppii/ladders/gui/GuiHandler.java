package se.luppii.ladders.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.luppii.ladders.gui.client.GuiBridgeBuilder;
import se.luppii.ladders.gui.client.GuiLadderDispenser;
import se.luppii.ladders.inventory.ContainerBridgeBuilder;
import se.luppii.ladders.inventory.ContainerLadderDispenser;
import se.luppii.ladders.tile.TileEntityBridgeBuilder;
import se.luppii.ladders.tile.TileEntityLadderDispenser;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == 0) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityLadderDispenser) {
				return new ContainerLadderDispenser(player.inventory, (TileEntityLadderDispenser) te);
			}
			else if (te instanceof TileEntityBridgeBuilder) {
				return new ContainerBridgeBuilder(player.inventory, (TileEntityBridgeBuilder) te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == 0) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityLadderDispenser) {
				return new GuiLadderDispenser(player.inventory, (TileEntityLadderDispenser) te);
			}
			else if (te instanceof TileEntityBridgeBuilder) {
				return new GuiBridgeBuilder(player.inventory, (TileEntityBridgeBuilder) te);
			}
		}
		return null;
	}
}
