package base.settings;

import java.io.IOException;

import base.Main;
import utils.IO;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class Settings {

	private static Settings INSTANCE;
	
	public static synchronized void load() {
		if(INSTANCE != null)
			throw new IllegalStateException("Settings have already been loaded");
		SettingsRecord record = SettingsRecord.DEFAULT;
		try {
			record = IO.readObject(Main.SETTINGS_FILE);
		} catch(Exception e) {
			//TODO is there any error handling to be done here?
		}
		create(record);
	}
	
	public static synchronized void save() {
		try {
			IO.writeObject(Main.SETTINGS_FILE, Settings.get().record());
		} catch(IOException e) {
			e.printStackTrace(); //TODO better error handling?
		}
	}
	
	private static void create(SettingsRecord record) {
		AnimationSettings.create(record.animationSettings());
		GeneralSettings.create(record.generalSettings());
		INSTANCE = new Settings();
	}
	
	public static Settings get() {
		if(INSTANCE == null)
			throw new IllegalStateException("Settings have not yet been loaded");
		return INSTANCE;
	}
	
	public static AnimationSettings animation() {
		return AnimationSettings.get();
	}
	
	public static GeneralSettings general() {
		return GeneralSettings.get();
	}
	
	private Settings() {
		
	}
	
	private SettingsRecord record() {
		return new SettingsRecord(
				GeneralSettings.get().record(),
				AnimationSettings.get().record()
		);
	}
	
}
