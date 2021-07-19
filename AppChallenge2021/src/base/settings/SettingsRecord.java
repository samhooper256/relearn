package base.settings;

import java.io.Serializable;

record SettingsRecord(
		boolean showEditEraseWarning
) implements Serializable {

	static final SettingsRecord DEFAULT = new SettingsRecord(true);
	
}
