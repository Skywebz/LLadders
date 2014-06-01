package se.luppii.ladders.lib;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	// Block
	public static Property blockLadderDispenser;
	public static Property blockRopeLadder;
	public static Property blockSturdyLadder;
	
	// Settings
	public static Property sturdyLadderLeftClick;
	public static Property ropeLadderLeftClick;
	
	// Updater
	public static Property checkForUpdates;
	
	public static void loadConfig(FMLPreInitializationEvent e) {
		
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		try {
			config.load();
			
			// Blocks
			blockLadderDispenser = config.get(config.CATEGORY_BLOCK, "ID.Block.LadderDispenser", 682);
			blockRopeLadder = config.get(config.CATEGORY_BLOCK, "ID.Block.RopeLadder", 680);
			blockSturdyLadder = config.get(config.CATEGORY_BLOCK, "ID.Block.SturdyLadder", 681);
			
			// Settings
			sturdyLadderLeftClick = config.get(config.CATEGORY_GENERAL, "Sturdy Ladder extend on left click", true);
			ropeLadderLeftClick = config.get(config.CATEGORY_GENERAL, "Rope Ladder extend on left click", true);
			
			// Updater
			checkForUpdates = config.get(config.CATEGORY_GENERAL, "Check for updates", true);
			
			config.save();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}
