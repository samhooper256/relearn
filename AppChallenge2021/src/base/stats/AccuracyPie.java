/**
 * 
 */
package base.stats;

import javafx.scene.chart.PieChart;

/**
 * @author Sam Hooper
 *
 */
public class AccuracyPie extends PieChart {
	
	private static final String ACCURACY_PIE_CSS = "accuracy-pie";
	
	private final PieChart.Data correctData, incorrectData;
	
	private int correct, incorrect;
	
	/** Body is equivalent to {@code this(1, 1)}.*/
	public AccuracyPie() {
		this(1, 1);
	}
	
	public AccuracyPie(ReadOnlyStats stats) {
		this(stats.correct(), stats.incorrect());
	}
	
	public AccuracyPie(int correct, int incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
		
		correctData = new Data(String.valueOf(correct), correct);
		incorrectData = new Data(String.valueOf(incorrect), incorrect);
		
		getData().addAll(correctData, incorrectData);
		
		getStyleClass().add(ACCURACY_PIE_CSS);
	}
	
	public int correct() {
		return correct;
	}
	
	public int incorrect() {
		return incorrect;
	}
	
	public void setAccuracy(ReadOnlyStats stats) {
		setAccuracy(stats.correct(), stats.incorrect());
	}
	
	public void setAccuracy(int correct, int incorrect) {
		setCorrect(correct);
		setIncorrect(incorrect);
	}

	private void setCorrect(int correct) {
		correctData.setPieValue(correct);
		correctData.setName(String.valueOf(correct));
	}
	
	private void setIncorrect(int incorrect) {
		incorrectData.setPieValue(incorrect);
		incorrectData.setName(String.valueOf(incorrect));
	}
	
}
