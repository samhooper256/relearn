package base.practice;

import static base.stats.Data.formatTime;

import fxutils.AnimatedLabel;
import javafx.scene.layout.VBox;

final class StatsLayer extends VBox {
	
	private static final String
			STATS_LAYER_CSS = "stats-layer",
			LAST_TIME_LABEL_CSS = "last-time-label",
			AVERAGE_TIME_LABEL_CSS = "average-time-label";
	
	private final AnimatedLabel lastTimeLabel, averageTimeLabel;
	private final StreakBar bar;
	
	private double totalTime;
	
	StatsLayer() {
		lastTimeLabel = new AnimatedLabel("Last time: ---");
		lastTimeLabel.getStyleClass().add(LAST_TIME_LABEL_CSS);
		averageTimeLabel = new AnimatedLabel("Average time: ---");
		averageTimeLabel.getStyleClass().add(AVERAGE_TIME_LABEL_CSS);
		bar = new StreakBar();
		setMouseTransparent(true);
		getStyleClass().add(STATS_LAYER_CSS);
		getChildren().addAll(lastTimeLabel, averageTimeLabel, bar);
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
	
	void addTime(double timeInMillis) {
		totalTime += timeInMillis;
		double averageTime = totalTime / pane().deckStats().total();
		setMostRecentTime(timeInMillis);
		setAverageTime(averageTime);
	}
	
	private void setMostRecentTime(double timeInMillis) {
		lastTimeLabel.animateLRto(String.format("Last time: %s", formatTime(timeInMillis)));
	}
	
	private void setAverageTime(double timeInMillis) {
		averageTimeLabel.animateLRto(String.format("Average time: %s", formatTime(timeInMillis)));
	}
	
	private PracticePane pane() {
		return (PracticePane) getParent();
	}
	
}
