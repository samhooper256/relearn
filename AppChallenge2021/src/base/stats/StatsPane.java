/**
 * 
 */
package base.stats;

import base.*;
import base.graphics.BackArrow;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import org.controlsfx.control.ToggleSwitch;

/**
 * @author Sam Hooper
 *
 */
public final class StatsPane extends StackPane {
	
	private static final String
		STATS_PANE_CSS = "stats-pane",
		VBOX_CSS = "vbox";
	private static final StatsPane INSTANCE = new StatsPane();
	private static final String TITLE = "Stats";
	
	public static final StatsPane get() {
		return INSTANCE;
	}
	
	private final VBox vBox;
	private final HBox header;
	private final BackArrow backArrow;
	private final Label title;
	private final ToggleSwitch toggle;
	private final StackPane tabPaneHolder;
	
	private StatsPane() {
		backArrow = new BackArrow();
		initBackArrow();
		title = new Label(TITLE);
		toggle = new ToggleSwitch("Goof");
		initToggle();
		header = new HBox(backArrow, title, toggle);
		tabPaneHolder = new StackPane(TopicTabPane.get());
		vBox = new VBox(header, tabPaneHolder);
		initVBox();
		
		getStyleClass().add(STATS_PANE_CSS);
		getChildren().add(vBox);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
	private void initToggle() {
		toggle.selectedProperty().addListener(e -> {
			boolean selected = toggle.isSelected();
			if(selected)
				switchToSets();
			else
				switchToTopics();
		});
	}
	
	private void initVBox() {
		vBox.getStyleClass().add(VBOX_CSS);
		VBox.setVgrow(tabPaneHolder, Priority.ALWAYS);
	}
	
	private void switchToSets() {
		switchTo(SetsTabPane.get());
	}
	
	private void switchToTopics() {
		switchTo(TopicTabPane.get());
	}
	
	private void switchTo(TabPane tp) {
		tabPaneHolder.getChildren().set(0, tp);
	}
	
	public void updateAllStats() {
		TopicTabPane.get().updateAllStats();
	}
}
