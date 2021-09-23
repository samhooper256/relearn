package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
final class SettingsRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6559622964888723855L;

	static final SettingsRecord DEFAULT = new SettingsRecord(
			GeneralSettingsRecord.DEFAULT,
			AnimationSettingsRecord.DEFAULT
	);
	
	private final GeneralSettingsRecord generalSettings;
	private final  AnimationSettingsRecord animationSettings;
	
	public SettingsRecord(GeneralSettingsRecord generalSettings, AnimationSettingsRecord animationSettings) {
		this.generalSettings = generalSettings;
		this.animationSettings = animationSettings;
	}
	
	public GeneralSettingsRecord generalSettings() {
		return generalSettings;
	}
	
	public AnimationSettingsRecord animationSettings() {
		return animationSettings;
	}
	
}
