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
	
	public AccuracyPie(ReadOnlyStats stats) {
		this(stats.correct(), stats.incorrect());
	}
	
	public AccuracyPie(int correct, int incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
		
		correctData = new Data(String.valueOf(correct), correct);
		incorrectData = new Data(String.valueOf(incorrect), incorrect);
		
		getData().addAll(correctData, incorrectData);
		
		correctData.getNode().getStyleClass().add("correct-pie-slice");
		incorrectData.getNode().getStyleClass().add("incorrect-pie-slice");
		
		getStyleClass().add(ACCURACY_PIE_CSS);
	}
	
	public int correct() {
		return correct;
	}
	
	public int incorrect() {
		return incorrect;
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
