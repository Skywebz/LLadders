package se.luppii.ladders.gui;

import se.luppii.ladders.gui.client.GuiLadderDispenser;
import se.luppii.ladders.gui.container.ContainerLadderDispenser;
import se.luppii.ladders.tile.TileEntityLadderDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (ID == 0) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityLadderDispenser) {
				return new ContainerLadderDispenser(player.inventory, (TileEntityLadderDispenser) te);
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
		}
		return null;
	}

}
