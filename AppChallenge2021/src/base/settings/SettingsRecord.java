package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
record SettingsRecord(
		GeneralSettingsRecord generalSettings,
		AnimationSettingsRecord animationSettings
) implements Serializable {

	static final SettingsRecord DEFAULT = new SettingsRecord(
			GeneralSettingsRecord.DEFAULT,
			AnimationSettingsRecord.DEFAULT
	);
	
}
