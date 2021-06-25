/**
 * 
 */
package base.stats;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

/**
 * @author Sam Hooper
 *
 */
public class StatsTab extends Tab {
	
	private final Label title;
	
	public StatsTab(String text) {
		super("");
		setClosable(false);
		title = new Label(text);
		title.setRotate(90);
		StackPane sp = new StackPane(new Group(title));
		sp.setRotate(90);
		setGraphic(sp);
		
	}
	
	public StringProperty nameProperty() {
		return title.textProperty();
	}
	
}
