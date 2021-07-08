package base.practice;

import base.Main;
import fxutils.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class InfoBar extends HalvedPane {

	public static final double ICON_SIZE = 32;
	
	private static final String
			PERCENTAGE_CSS = "percentage",
			LONGEST_STREAK_CSS = "longest-streak";
	
	private final Label percentage, longestStreak;
	
	InfoBar() {
		percentage = new Label();
		initPercentage();
		longestStreak = new Label("streek");
		initLongestStreak();
		
		setHalves(percentage, longestStreak);
	}
	
	private void initPercentage() {
		percentage.setGraphic(new ImageView(Main.TARGET));
		percentage.getStyleClass().add(PERCENTAGE_CSS);
	}
	
	private void initLongestStreak() {
		longestStreak.setGraphic(new ImageView(Main.FIRE));
		longestStreak.getStyleClass().add(LONGEST_STREAK_CSS);
	}
	
	void updateAccuracy(int correct, int incorrect) {
		percentage.setText(String.format("%.0f%%", 100d * correct / (correct + incorrect)));
	}
	
	void updateLongestStreak(int streak) {
		this.longestStreak.setText(String.valueOf(streak));
	}
	
}
