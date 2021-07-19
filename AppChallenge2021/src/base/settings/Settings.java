package base.settings;

import java.io.IOException;

import base.Main;
import javafx.beans.property.*;
import utils.IO;

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
		INSTANCE = new Settings(record);
	}
	
	public static synchronized void save() {
		try {
			IO.writeObject(Main.SETTINGS_FILE, Settings.get().record());
		} catch(IOException e) {
			e.printStackTrace(); //TODO better error handling?
		}
	}
	
	public static Settings get() {
		if(INSTANCE == null)
			throw new IllegalStateException("Settings have not yet been loaded");
		return INSTANCE;
	}
	
	private final BooleanProperty doNotShowEditWarning;
	
	private Settings(SettingsRecord record) {
		doNotShowEditWarning = new SimpleBooleanProperty(record.doNotShowEditWarning());
	}
	
	public BooleanProperty doNotShowEditWarning() {
		return doNotShowEditWarning;
	}
	
	public boolean shouldShowEditWarning() {
		return !doNotShowEditWarning().get();
	}
	
	private SettingsRecord record() {
		return new SettingsRecord(
				doNotShowEditWarning.get()
		);
	}
	
}
