package base.practice;

import base.settings.AnimationSettings;
import base.stats.Data;
import fxutils.AnimatedLabel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Sam Hooper
 *
 */
abstract class StatsTimeBox extends HBox {
	
	private static final String EMPTY_TIME_TEXT = "---";
	private static final String
			STATS_TIME_BOX_CSS = "stats-time-box",
			LABEL_CSS = "label";
	
	private final Label description;
	private final AnimatedLabel time;
	
	StatsTimeBox(String description) {
		this.description = new Label(String.format("%s:", description, ":"));
		this.description.getStyleClass().add(LABEL_CSS);
		time = new AnimatedLabel(EMPTY_TIME_TEXT);
		time.getStyleClass().add(LABEL_CSS);
		getChildren().addAll(this.description, time);
		getStyleClass().add(STATS_TIME_BOX_CSS);
	}

	void setTime(double timeInMillis) {
		String timeString = Data.formatTime(timeInMillis);
		if(AnimationSettings.get().timeInPracticePane().get())
			time.animateLRto(timeString);
		else
			time.setText(timeString);
	}
	
	/** Does not play any animations. */
	void reset() {
		time.setText(EMPTY_TIME_TEXT);
	}
	
}
