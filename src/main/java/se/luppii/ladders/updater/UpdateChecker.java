package se.luppii.ladders.updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import se.luppii.ladders.lib.References;
import cpw.mods.fml.common.FMLLog;

public class UpdateChecker extends Thread {

	private String _versionUrl;

	private ModVersion _newVersion;

	private boolean _checkComplete;

	private boolean _newVersionAvailable;

	public UpdateChecker() {

		_versionUrl = "https://raw.githubusercontent.com/CodeAG/" + References.MOD_ID + "/master/VERSION";
	}

	@Override
	public void run() {

		try {
			URL versionFile = new URL(_versionUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionFile.openStream()));
			ModVersion newVersion = ModVersion.parse(reader.readLine());
			ModVersion thisVersion = ModVersion.parse(References.MOD_VERSION);
			reader.close();
			_newVersion = newVersion;
			_newVersionAvailable = thisVersion.compareTo(newVersion) < 0;
			if (_newVersionAvailable) {
				FMLLog.info("[" + References.MOD_NAME + "] A new version of " + References.MOD_NAME + " is available: " + newVersion.toString() + ".",
						new Object[0]);
			}
			_checkComplete = true;
		}
		catch (Exception err) {
			FMLLog.warning("[" + References.MOD_NAME + "] Update check failed: " + err.getMessage(), new Object[0]);
			err.printStackTrace();
		}
	}

	public boolean checkComplete() {

		return _checkComplete;
	}

	public boolean isNewVersionAvailable() {

		return _newVersionAvailable;
	}

	public ModVersion newVersion() {

		return _newVersion;
	}
}
