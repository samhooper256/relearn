package base.practice;

import static base.practice.PracticePane.formatTime;

import base.stats.ReadOnlyTimedAccuracyStats;
import fxutils.HalvedPane;
import javafx.scene.control.Label;

/**
 * @author Sam Hooper
 */
final class BottomInfoBar extends HalvedPane {
	
	private static final String
			AVERAGE_TIME_CSS = "average-time",
			FASTEST_TIME_CSS = "fastest-time";
	
	private final Label averageTime, fastestTime;
	
	BottomInfoBar() {
		averageTime = new Label();
		initAverageTime();
		fastestTime = new Label();
		initFastestTime();
		
		setHalves(averageTime, fastestTime);
	}

	private void initAverageTime() {
		averageTime.getStyleClass().add(AVERAGE_TIME_CSS);
	}

	private void initFastestTime() {
		fastestTime.getStyleClass().add(FASTEST_TIME_CSS);
	}
	
	void updateTimes(ReadOnlyTimedAccuracyStats stats) {
		updateAverageTime(stats.averageTime());
		if(stats.hasCorrect())
			updateFastestTime(stats.fastestCorrectTime());
		else
			fastestTime.setText("Fastest: N/A");
	}
	
	private void updateAverageTime(double timeInMillis) {
		averageTime.setText(String.format("Average: %s", formatTime(timeInMillis)));
	}
	
	private void updateFastestTime(double timeInMillis) {
		fastestTime.setText(String.format("Fastest: %s", formatTime(timeInMillis)));
	}
	
}
