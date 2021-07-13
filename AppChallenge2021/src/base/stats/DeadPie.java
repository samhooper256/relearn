/**
 * 
 */
package base.stats;

import javafx.scene.chart.PieChart;

/**
 * @author Sam Hooper
 *
 */
public class DeadPie extends PieChart {
	
	private static final String DEAD_PIE_CSS = "dead-pie";
	
	private final PieChart.Data deadData;
	
	public DeadPie() {
		deadData = new Data(String.valueOf("None"), 1);
		getData().addAll(deadData);
		deadData.getNode().getStyleClass().add("dead-pie-slice");
		
		getStyleClass().add(DEAD_PIE_CSS);
		setLegendVisible(false);
		setLabelsVisible(false);
	}
	
}