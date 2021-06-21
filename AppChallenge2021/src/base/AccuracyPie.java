/**
 * 
 */
package base;

import javafx.scene.chart.PieChart;

/**
 * @author Sam Hooper
 *
 */
public class AccuracyPie extends PieChart {
	
	private final Data correctData, incorrectData;
	
	private int correct, incorrect;
	
	public AccuracyPie(int correct, int incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
		
		correctData = new Data(String.valueOf(correct), correct);
		incorrectData = new Data(String.valueOf(incorrect), incorrect);
		
		getData().addAll(correctData, incorrectData);
		
		correctData.getNode().getStyleClass().add("correct-pie-slice");
		incorrectData.getNode().getStyleClass().add("incorrect-pie-slice");
		
		this.setLegendVisible(false);
	}
	
	public int correct() {
		return correct;
	}
	
	public int incorrect() {
		return incorrect;
	}
	
	public void setAccuracy(int correct, int incorrect) {
		correctData.setPieValue(correct);
		correctData.setName(String.valueOf(correct));
		incorrectData.setPieValue(incorrect);
		incorrectData.setName(String.valueOf(incorrect));
	}
}
