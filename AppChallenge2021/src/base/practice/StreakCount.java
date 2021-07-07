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
			CIRCLE_CSS = "circle";
	
	private final Label label;
	private final Circle circle;
	
	private int count;
	
	StreakCount() {
		label = new Label("0");
		circle = new Circle();
		initCircle();
		
		getChildren().addAll(circle, label);
		getStyleClass().add(STREAK_COUNT_CSS);
	}

	private void initCircle() {
		circle.setRadius(CIRCLE_RADIUS);
		Nodes.setTranslate(circle, CIRCLE_RADIUS, CIRCLE_RADIUS);
		circle.getStyleClass().add(CIRCLE_CSS);
	}
	
	void incrementCount() {
		count++;
		updateLabel();
	}
	
	void resetCount() {
		count = 0;
		updateLabel();
	}

	private void updateLabel() {
		label.setText(String.valueOf(count()));
	}
	
	int count() {
		return count;
	}
	
}
