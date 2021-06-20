/**
 * 
 */
package base;

import fxutils.Backgrounds;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelectionPane extends StackPane {
	
	private static final double MAX_WIDTH = 400, MAX_HEIGHT = 300;
	
	public TopicSelectionPane() {
		setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		setBackground(Backgrounds.of(Color.BLUE));
		
	}
	
}
