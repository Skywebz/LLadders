package se.luppii.ladders.lib;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	// General
	public static Property checkForUpdates;
	
	public static void loadConfig(FMLPreInitializationEvent e) {
		
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		try {
			config.load();

			// Update Checker
			checkForUpdates = config.get(Configuration.CATEGORY_GENERAL, "Check for updates", true);
			
			config.save();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}
