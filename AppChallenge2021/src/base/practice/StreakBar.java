package base.practice;

import javafx.scene.layout.*;

/**
 * 
 * @author Sam Hooper
 *
 */
final class StreakBar extends HBox {
	
	private static final String STREAK_BAR_CSS = "streak-bar";
	
	private final StreakCount count;
	private final AccuracyBar accuracyBar;
	
	StreakBar() {
		count = new StreakCount();
		accuracyBar = new AccuracyBar();

		setPickOnBounds(false);
		getChildren().addAll(count, accuracyBar);
		getStyleClass().add(STREAK_BAR_CSS);
	}
	
	void notifyCorrect() {
		count.incrementCount();
		accuracyBar.addCheck();
	}
	
	void notifyIncorrect() {
		count.resetCount();
		accuracyBar.addX();
	}
	
	StreakCount count() {
		return count;
	}
	
	void resetAll() {
		count().resetAll();
		accuracyBar.clear();
	}
}
