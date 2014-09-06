package se.luppii.ladders.updater;

import net.minecraft.util.ChatComponentText;
import se.luppii.ladders.LLadders;
import se.luppii.ladders.lib.References;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class UpdateManager {

	private boolean _notificationDisplayed;

	private UpdateChecker _updateChecker;

	public UpdateManager() {

		_updateChecker = new UpdateChecker();
		if (LLadders.checkForUpdates) {
			_updateChecker.start();
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

		if (!_notificationDisplayed && _updateChecker.checkComplete()) {
			_notificationDisplayed = true;
			if (_updateChecker.isNewVersionAvailable()) {
				ChatComponentText msg = new ChatComponentText("[" + References.MOD_NAME + "] New version is available: "
						+ _updateChecker.newVersion().toString());
				event.player.addChatMessage(msg);
				msg = new ChatComponentText("[" + References.MOD_NAME + "] " + _updateChecker.newVersion().toString() + ": "
						+ _updateChecker.newVersion().description());
				event.player.addChatMessage(msg);
			}
		}
	}
}
