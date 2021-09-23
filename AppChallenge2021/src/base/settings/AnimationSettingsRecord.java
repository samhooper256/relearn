package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
final class AnimationSettingsRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7261720080285289422L;

	static final AnimationSettingsRecord DEFAULT = new AnimationSettingsRecord(true, true, true, true);
	
	private final boolean timeInPracticePane;
	private final boolean accuracyBar;
	private final boolean progressBar;
	private final boolean popups;
	
	public AnimationSettingsRecord(boolean timeInPracticePane, boolean accuracyBar, boolean progressBar,
			boolean popups) {
		this.timeInPracticePane = timeInPracticePane;
		this.accuracyBar = accuracyBar;
		this.progressBar = progressBar;
		this.popups = popups;
	}
	
	boolean timeInPracticePane() {
		return timeInPracticePane;
	}

	boolean accuracyBar() {
		return accuracyBar;
	}
	
	boolean progressBar() {
		return progressBar;
	}
	
	boolean popups() {
		return popups;
	}
	
}
