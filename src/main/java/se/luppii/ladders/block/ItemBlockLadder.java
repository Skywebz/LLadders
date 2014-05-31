package se.luppii.ladders.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockLadder extends ItemBlock {
	
	private Block block;
	
	public ItemBlockLadder(Block par1Block) {
		super(par1Block);
		this.block = par1Block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return this.block.getIcon(2, par1);
	}
}
