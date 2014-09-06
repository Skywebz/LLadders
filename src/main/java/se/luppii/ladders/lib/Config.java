package se.luppii.ladders.lib;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	// General
	public static Property ropeLadderLeftClick;

	public static Property sturdyLadderLeftClick;

	public static Property vineLadderLeftClick;

	// Updater
	public static Property checkForUpdates;

	// Ropes+ config
	public static Property ropesPlusRecipe;

	public static Property overwriteVanillaRecipe;

	// LadderDispenser config
	public static Property canClimbOnDispenser;

	public static void loadConfig(FMLPreInitializationEvent e) {

		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		try {
			config.load();
			// Block config.
			ropeLadderLeftClick = config.get(Configuration.CATEGORY_GENERAL, "Rope Ladder extend on left click", true);
			sturdyLadderLeftClick = config.get(Configuration.CATEGORY_GENERAL, "Sturdy Ladder extend on left click", true);
			vineLadderLeftClick = config.get(Configuration.CATEGORY_GENERAL, "Vine Ladder extend on left click", true);
			// Update Checker
			checkForUpdates = config.get("updater", "Check for updates", true);
			// Ropes+ config
			ropesPlusRecipe = config.get("modcompat", "Use Ropes+ recipe", true);
			overwriteVanillaRecipe = config.get("modcompat", "Overwrite vanilla recipe for RopeLadders", true);
			// LadderDispenser config
			canClimbOnDispenser = config.get("ladderdispenser", "Can climb on Ladder Dispenser", false);
			config.save();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}
