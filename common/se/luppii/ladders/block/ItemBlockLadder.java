package se.luppii.ladders.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;

public class ItemBlockLadder extends ItemBlock {

	public ItemBlockLadder(int par1) {
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return Block.blocksList[getBlockID()].getIcon(2, par1);
	}
}
