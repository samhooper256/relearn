package base.practice;

import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * 
 * @author Sam Hooper
 *
 */
final class StreakBar extends HBox {
	
	private static final String STREAK_BAR_CSS = "streak-bar";
	
	private final Label count;
	private final AccuracyBar accuracyBar;
	
	private int streak;
	
	StreakBar() {
		count = new Label("0");
		accuracyBar = new AccuracyBar();

		setPickOnBounds(false);
		getChildren().addAll(count, accuracyBar);
		getStyleClass().add(STREAK_BAR_CSS);
		
		streak = 0;
	}
	
	void notifyCorrect() {
		streak++;
		accuracyBar.addCheck();
	}
	
	void notifyIncorrect() {
		streak = 0;
		accuracyBar.addX();
	}
	
	int streak() {
		return streak;
	}
	
}
