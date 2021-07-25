package base.practice;

import javafx.scene.layout.VBox;

final class StatsLayer extends VBox {
	
	private static final String STATS_LAYER_CSS = "stats-layer";
	
	private final StatsTimeBox lastTime, averageTime;
	private final StreakBar bar;
	
	private double totalTime;
	
	StatsLayer() {
		
		lastTime = new LastTimeBox();
		averageTime = new AverageTimeBox();
		bar = new StreakBar();
		setMouseTransparent(true);
		getStyleClass().add(STATS_LAYER_CSS);
		getChildren().addAll(lastTime, averageTime, bar);
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
		lastTime.reset();
		averageTime.reset();
		totalTime = 0;
	}
	
	void addTime(double timeInMillis) {
		totalTime += timeInMillis;
		double averageTime = totalTime / pane().deckStats().total();
		setMostRecentTime(timeInMillis);
		setAverageTime(averageTime);
	}
	
	private void setMostRecentTime(double timeInMillis) {
		lastTime.setTime(timeInMillis);
	}
	
	private void setAverageTime(double timeInMillis) {
		averageTime.setTime(timeInMillis);
	}
	
	private PracticePane pane() {
		return (PracticePane) getParent();
	}
	
}
