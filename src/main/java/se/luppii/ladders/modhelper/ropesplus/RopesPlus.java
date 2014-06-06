package se.luppii.ladders.modhelper.ropesplus;

import java.util.logging.Level;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import se.luppii.ladders.LLadders;
import se.luppii.ladders.lib.References;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.FMLLog;
import se.luppii.ladders.block.BlockRopeLadder;
import atomicstryker.ropesplus.common.BlockRope;
import atomicstryker.ropesplus.common.RopesPlusCore;

@Mod(modid = References.MOD_HELP_ROPES_ID, name = References.MOD_HELP_ROPES_NAME, version = References.MOD_HELP_ROPES_VERSION, dependencies = "required-after:" + References.MOD_ID + ";after:RopesPlus")
public class RopesPlus {
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		if (!Loader.isModLoaded("RopesPlus")){
			FMLLog.info("Ropes+ missing, " + References.MOD_HELP_ROPES_NAME + " is not loading");
			return;
		}
		
		//Try to use ropes as recipes for ladders
		try {
			// Rope Ladder
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LLadders.blockRopeLadder, 6, 0), true, new Object[] {
				"R R", "PPP", "R R", 'P', "plankWood", 'R', new ItemStack(BlockRope.getBlockFromName("BlockRope"), 1, 0)}));
		} catch (Exception err) {
			err.printStackTrace();
		}
		
	}
}
