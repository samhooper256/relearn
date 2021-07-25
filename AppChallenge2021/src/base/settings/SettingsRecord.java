package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
record SettingsRecord(
		boolean doNotShowEditWarning,
		AnimationSettingsRecord animationSettings
) implements Serializable {

	static final SettingsRecord DEFAULT = new SettingsRecord(false, AnimationSettingsRecord.DEFAULT);
	
}
