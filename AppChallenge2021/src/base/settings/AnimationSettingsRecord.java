package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
record AnimationSettingsRecord(
		boolean timeInPracticePane,
		boolean accuracyBar,
		boolean progressBar,
		boolean popups
) implements Serializable {

	static final AnimationSettingsRecord DEFAULT = new AnimationSettingsRecord(true, true, true, true);
	
}
