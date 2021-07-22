/**
 * 
 */
package base.stats;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;

/**
 * @author Sam Hooper
 *
 */
class StatsTab extends Tab {
	
	private static final String STATS_TAB_CSS = "stats-tab";
	
	private final Label title;
	
	StatsTab(String text) {
		super("");
		setClosable(false);
		title = new Label(text);
		title.setRotate(90);
		title.setWrapText(true);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setMaxWidth(StatsTabPane.TAB_HEIGHT - 10);
		StackPane sp = new StackPane(new Group(title));
		sp.setRotate(90);
		setGraphic(sp);
		getStyleClass().add(STATS_TAB_CSS);
	}
	
	public StringProperty nameProperty() {
		return title.textProperty();
	}
	
	StatsTabContent content() {
		return (StatsTabContent) getContent();
	}
	
}
