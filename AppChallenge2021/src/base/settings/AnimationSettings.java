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
	
	private final BooleanProperty timeInPracticePane;
	
	private AnimationSettings(AnimationSettingsRecord record) {
		timeInPracticePane = new SimpleBooleanProperty(record.timeInPracticePane());
	}
	
	public BooleanProperty timeInPracticePane() {
		return timeInPracticePane;
	}
	
	AnimationSettingsRecord record() {
		return new AnimationSettingsRecord(
				timeInPracticePane.get()
		);
	}
	
}
