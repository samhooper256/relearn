/**
 * 
 */
package base.stats;

import javafx.geometry.Side;
import javafx.scene.control.*;

/**
 * @author Sam Hooper
 *
 */
public class StatsTabPane extends TabPane {
	
	private static final String STATS_TAB_PANE_CSS = "stats-tab-pane";
	private static final double TAB_HEIGHT = 200;
	
	public StatsTabPane() {
		setSide(Side.LEFT);
		setRotateGraphic(true);
		setTabMinHeight(TAB_HEIGHT);
		setTabMaxHeight(TAB_HEIGHT);
		getStyleClass().add(STATS_TAB_PANE_CSS);
	}
	
}
