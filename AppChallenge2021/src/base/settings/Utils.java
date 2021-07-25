package base.settings;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

/**
 * 
 * @author Sam Hooper
 *
 */
final class Utils {
	
	private Utils() {
		
	}
	
	static CheckBox createCheckBox(String name, BooleanProperty backingProperty) {
		CheckBox box = new CheckBox(name);
		box.setSelected(backingProperty.get());
		box.selectedProperty().bindBidirectional(backingProperty);
		return box;
	}
	
}
