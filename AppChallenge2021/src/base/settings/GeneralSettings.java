package base.settings;

import javafx.beans.property.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class GeneralSettings {
	
	private static GeneralSettings INSTANCE;
	
	static void create(GeneralSettingsRecord record) {
		if(INSTANCE != null)
			throw new IllegalStateException("GeneralSettings have already been loaded");
		INSTANCE = new GeneralSettings(record);
	}
	
	public static GeneralSettings get() {
		if(INSTANCE == null)
			throw new IllegalStateException("GeneralSettings have not yet been loaded");
		return INSTANCE;
	}
	
	private final BooleanProperty doNotShowEditWarning;
	
	private GeneralSettings(GeneralSettingsRecord record) {
		doNotShowEditWarning = new SimpleBooleanProperty(record.doNotShowEditWarning());
	}
	
	public BooleanProperty doNotShowEditWarning() {
		return doNotShowEditWarning;
	}
	
	public boolean shouldShowEditWarning() {
		return !doNotShowEditWarning().get();
	}
	
	GeneralSettingsRecord record() {
		return new GeneralSettingsRecord(
				doNotShowEditWarning.get()
		);
	}
	
}
