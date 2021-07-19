package base.settings;

import java.io.Serializable;

record SettingsRecord(
		boolean doNotShowEditWarning
) implements Serializable {

	static final SettingsRecord DEFAULT = new SettingsRecord(false);
	
}
