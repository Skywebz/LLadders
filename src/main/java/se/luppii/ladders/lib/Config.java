package se.luppii.ladders.lib;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	// General
	public static Property ropeLadderLeftClick;
	public static Property sturdyLadderLeftClick;
	
	// Updater
	public static Property checkForUpdates;
	
	//Ropes+ config
	public static Property ropesPlusRecipe;
	public static Property overwriteVanillaRecipe;
	
	public static void loadConfig(FMLPreInitializationEvent e) {
		
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		try {
			config.load();
			
			// Block config.
			sturdyLadderLeftClick = config.get(Configuration.CATEGORY_GENERAL, "Sturdy Ladder extend on left click", true);
			ropeLadderLeftClick = config.get(Configuration.CATEGORY_GENERAL, "Rope Ladder extend on left click", true);
			
			// Update Checker
			checkForUpdates = config.get("updater", "Check for updates", true);
			
			//Ropes+ config
			ropesPlusRecipe = config.get("modCompat", "Use Ropes+ recipe", true);
			overwriteVanillaRecipe = config.get("modCompat", "Overwrite vanilla recipe for RopeLadders", true);
			
			config.save();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}
