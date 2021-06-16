/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class SetCard extends StackPane {
	
	private static final double PREF_WIDTH = 300;
	private static final double PREF_HEIGHT = 150;
	
	private final ProblemSet set;
	private final VBox vBox;
	
	public SetCard(ProblemSet set) {
		this.set = set;
		this.setBorder(Borders.of(Color.GREEN));
		this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		Label title = new Label(set.name());
		vBox = new VBox(title);
		getChildren().add(vBox);
		
	}
	
	public ProblemSet getSet() {
		return set;
	}
	
}
