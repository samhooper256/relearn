package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
record AnimationSettingsRecord(
		boolean timeInPracticePane
) implements Serializable {

	static final AnimationSettingsRecord DEFAULT = new AnimationSettingsRecord(true);
	
}
