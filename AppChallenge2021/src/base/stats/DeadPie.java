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
	
	private final PieChart.Data deadData;
	
	public DeadPie() {
		
		deadData = new Data(String.valueOf("None"), 1);
		
		getData().addAll(deadData);
		
		deadData.getNode().getStyleClass().add("dead-pie-slice");
		
		this.setLegendVisible(false);
	}
	
}