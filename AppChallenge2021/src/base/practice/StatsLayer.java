package base.practice;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

final class StatsLayer extends VBox {
	
	private static final String
			STATS_LAYER_CSS = "stats-layer",
			LAST_TIME_LABEL_CSS = "last-time-label";
	
	private final Label lastTimeLabel;
	private final StreakBar bar;
	
	StatsLayer() {
		lastTimeLabel = new Label("Last time: ---");
		lastTimeLabel.getStyleClass().add(LAST_TIME_LABEL_CSS);
		bar = new StreakBar();
		getStyleClass().add(STATS_LAYER_CSS);
		getChildren().addAll(lastTimeLabel, bar);
	}
	
	int longestStreak() {
		return bar.count().longest();
	}
	
	void notifyCorrect() {
		bar.notifyCorrect();
	}
	
	void notifyIncorrect() {
		bar.notifyIncorrect();
	}
	
	void resetAll() {
		bar.resetAll();
	}
	
	/** @param time in millis*/
	void setMostRecentTime(double time) {
		lastTimeLabel.setText(String.format("Last time: %s", formatTime(time)));
	}
	
	private String formatTime(double time) {
		return String.format("%.2fs", time / 1000);
	}
	
}
