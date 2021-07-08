package base.practice;

import fxutils.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

/**
 * 
 * @author Sam Hooper
 *
 */
final class StreakCount extends StackPane {

	private static final double CIRCLE_RADIUS = 16;
	
	private static final String
			STREAK_COUNT_CSS = "count",
			CIRCLE_CSS = "circle",
			TEXT_CSS = "text";
	
	private final Label text;
	private final Circle circle;
	
	private int streak, longest;
	
	StreakCount() {
		text = new Label("0");
		initText();
		circle = new Circle();
		initCircle();
		
		getChildren().addAll(circle, text);
		getStyleClass().add(STREAK_COUNT_CSS);
		
		streak = longest = 0;
	}

	private void initText() {
		text.getStyleClass().add(TEXT_CSS);
	}

	private void initCircle() {
		circle.setRadius(CIRCLE_RADIUS);
		Nodes.setTranslate(circle, CIRCLE_RADIUS, CIRCLE_RADIUS);
		circle.getStyleClass().add(CIRCLE_CSS);
	}
	
	void incrementCount() {
		streak++;
		longest = Math.max(longest, streak);
		updateLabel();
	}
	
	void resetCount() {
		streak = 0;
		updateLabel();
	}
	
	/** Invokes {@link #resetCount()} and resets the {@link #longest()} streak count. */
	void resetAll() {
		resetCount();
		longest = 0;
	}
	
	private void updateLabel() {
		text.setText(String.valueOf(streak()));
	}
	
	int streak() {
		return streak;
	}
	
	int longest() {
		return longest;
	}
	
}
