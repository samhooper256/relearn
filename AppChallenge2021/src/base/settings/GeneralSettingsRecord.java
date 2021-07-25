package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
record GeneralSettingsRecord(
		boolean doNotShowEditWarning
) implements Serializable {
	
	static final GeneralSettingsRecord DEFAULT = new GeneralSettingsRecord(false);
	
}
