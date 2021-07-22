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
class StatsTabPane extends TabPane {
	
	static final double TAB_HEIGHT = 200;
	
	private static final String STATS_TAB_PANE_CSS = "stats-tab-pane";
	
	public StatsTabPane() {
		setSide(Side.LEFT);
		setRotateGraphic(true);
		setTabMinHeight(TAB_HEIGHT);
		setTabMaxHeight(TAB_HEIGHT);
		getStyleClass().add(STATS_TAB_PANE_CSS);
	}
	
}
