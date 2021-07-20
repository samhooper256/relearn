/**
 * 
 */
package base.stats;

import base.*;
import base.graphics.BackArrow;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public final class StatsPane extends StackPane {
	
	private static final String
		STATS_PANE_CSS = "stats-pane",
		HEADER_CSS = "header",
		TITLE_CSS = "title",
		VBOX_CSS = "vbox",
		TOGGLE_LAYER_CSS = "toggle-layer";
	private static final StatsPane INSTANCE = new StatsPane();
	private static final String TITLE = "Stats";
	
	public static final StatsPane get() {
		return INSTANCE;
	}
	
	private final VBox vBox, toggleLayer;
	private final HBox header;
	private final BackArrow backArrow;
	private final Label title;
	private final StatsToggle toggle;
	private final StackPane tabPaneHolder;
	
	private StatsPane() {
		backArrow = new BackArrow();
		title = new Label(TITLE);
		toggle = new StatsToggle();
		toggleLayer = new VBox(toggle);
		header = new HBox(backArrow, title);
		tabPaneHolder = new StackPane(TopicTabPane.get());
		vBox = new VBox(header, tabPaneHolder);
		initVBox();
		initToggleLayer();
		getStyleClass().add(STATS_PANE_CSS);
		getChildren().addAll(vBox, toggleLayer);
	}
	
	private void initVBox() {
		vBox.getStyleClass().add(VBOX_CSS);
		VBox.setVgrow(tabPaneHolder, Priority.ALWAYS);
		initHeader();
	}
	
	private void initHeader() {
		header.getStyleClass().add(HEADER_CSS);
		initBackArrow();
		initTitle();
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
	private void initTitle() {
		title.getStyleClass().add(TITLE_CSS);
	}
	
	private void initToggleLayer() {
		toggleLayer.getStyleClass().add(TOGGLE_LAYER_CSS);
		toggleLayer.setPickOnBounds(false); //cannot be set from CSS.
	}
	
	void switchToSets() {
		switchTo(SetTabPane.get());
	}
	
	void switchToTopics() {
		switchTo(TopicTabPane.get());
	}
	
	private void switchTo(TabPane tp) {
		tabPaneHolder.getChildren().set(0, tp);
	}
	
	public void updateAllStats() {
		TopicTabPane.get().updateAllStats();
		SetTabPane.get().updateAllStats();
	}
	
}
