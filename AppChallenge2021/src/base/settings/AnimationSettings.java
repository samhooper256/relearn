package base.settings;

import javafx.beans.property.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class AnimationSettings {
	
	private static AnimationSettings INSTANCE;
	
	static void create(AnimationSettingsRecord record) {
		if(INSTANCE != null)
			throw new IllegalStateException("AnimationSettings have already been loaded");
		INSTANCE = new AnimationSettings(record);
	}
	
	static AnimationSettings get() {
		if(INSTANCE == null)
			throw new IllegalStateException("AnimationSettings have not yet been loaded");
		return INSTANCE;
	}
	
	private final BooleanProperty timeInPracticePane, accuracyBar, progressBar, popups;
	
	private AnimationSettings(AnimationSettingsRecord record) {
		timeInPracticePane = new SimpleBooleanProperty(record.timeInPracticePane());
		accuracyBar = new SimpleBooleanProperty(record.accuracyBar());
		progressBar = new SimpleBooleanProperty(record.progressBar());
		popups = new SimpleBooleanProperty(record.popups());
	}
	
	public BooleanProperty timeInPracticePane() {
		return timeInPracticePane;
	}
	
	public BooleanProperty accuracyBar() {
		return accuracyBar;
	}
	
	public BooleanProperty progressBar() {
		return progressBar;
	}
	
	public BooleanProperty popups() {
		return popups;
	}
	
	AnimationSettingsRecord record() {
		return new AnimationSettingsRecord(
				timeInPracticePane.get(),
				accuracyBar.get(),
				progressBar.get(),
				popups.get()
		);
	}
	
}
