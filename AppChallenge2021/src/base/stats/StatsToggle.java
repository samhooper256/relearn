package base.stats;

import org.controlsfx.control.ToggleSwitch;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class StatsToggle extends StackPane {
	
	private static final PseudoClass LABEL_ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");
	private static final String
			STATS_TOGGLE_CSS = "stats-toggle",
			HBOX_CSS = "hbox",
			TOGGLE_SWITCH_CSS = "switch",
			TOPICS_LABEL_CSS = "topics-label",
			SETS_LABEL_CSS = "sets-label";
	
	private final HBox hBox;
	private final ToggleSwitch toggleSwitch;
	private final Label topicsLabel, setsLabel;
	
	StatsToggle() {
		toggleSwitch = new ToggleSwitch();
		toggleSwitch.setMaxSize(0, 0);
		
		Pane switchWrap = new Pane(toggleSwitch);
		switchWrap.setMaxSize(50, 18);
		//ToggleSwitches are very finicky.
		//In the above line, the first value, the max width, is approximately the width of a ToggleSwitch. The second
		//value, the max height, is a guess that makes the toggle switch roughly centered vertically. The switchWrap
		//is needed because, by default, the ToggleSwitch is aligned to the top-right of its area, and that area
		//is way bigger than it needs to be. I don't know why. It just is.
		
		topicsLabel = new Label("By Topic");
		setsLabel = new Label("By Set");
		hBox = new HBox(topicsLabel, switchWrap, setsLabel);
		initHBox();
		setPickOnBounds(false); //cannot be set from CSS.
		getStyleClass().add(STATS_TOGGLE_CSS);
		getChildren().add(hBox);
	}
	
	private void initHBox() {
		hBox.setPickOnBounds(false); //cannot be set from CSS.
		hBox.getStyleClass().add(HBOX_CSS);
		initSwitch();
		initLabels();
	}

	private void initSwitch() {
		toggleSwitch.getStyleClass().add(TOGGLE_SWITCH_CSS);
		toggleSwitch.selectedProperty().addListener(e -> {
			boolean selected = toggleSwitch.isSelected();
			if(selected)
				switchToSets();
			else
				switchToTopics();
		});
	}

	private void switchToSets() {
		topicsLabel.pseudoClassStateChanged(LABEL_ACTIVE_PSEUDO_CLASS, false);
		setsLabel.pseudoClassStateChanged(LABEL_ACTIVE_PSEUDO_CLASS, true);
		StatsPane.get().switchToSets();
	}
	
	private void switchToTopics() {
		topicsLabel.pseudoClassStateChanged(LABEL_ACTIVE_PSEUDO_CLASS, true);
		setsLabel.pseudoClassStateChanged(LABEL_ACTIVE_PSEUDO_CLASS, false);
		StatsPane.get().switchToTopics();
	}
	
	private void initLabels() {
		topicsLabel.pseudoClassStateChanged(LABEL_ACTIVE_PSEUDO_CLASS, true);
		topicsLabel.getStyleClass().add(TOPICS_LABEL_CSS);
		setsLabel.getStyleClass().add(SETS_LABEL_CSS);
	}
	
}
