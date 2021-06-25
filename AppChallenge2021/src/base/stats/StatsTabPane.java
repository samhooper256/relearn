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
	
	private static final double TAB_HEIGHT = 200;
	
	public StatsTabPane() {
		setSide(Side.LEFT);
		setRotateGraphic(true);
		setTabMinHeight(TAB_HEIGHT);
		setTabMaxHeight(TAB_HEIGHT);
	}
	
}
