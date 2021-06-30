/**
 * 
 */
package base.sets;

import base.Main;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelector extends StackPane {
	
	private static final String
			TOPIC_SELECTOR_CSS = "topic-selector",
			ADDED_BUTTON_CSS = "added-button",
			UNSELECTED_BUTTON_CSS = "unselected-button",
			SELECTED_BUTTON_CSS = "selected-button",
			LABEL_LAYER_CSS = "label-layer",
			BUTTON_LAYER_CSS = "button-layer";
	
	private final TopicFactory<?> factory;
	private final HBox labelLayer, buttonLayer;
	private final Label label;
	private final Button addedButton, unselectedButton, selectedButton;
	
	public TopicSelector(TopicFactory<?> factory) {
		this.factory = factory;
		label = new Label(factory.name());
		labelLayer = new HBox(label);
		initLabelLayer();
		
		addedButton = new Button("Added");
		unselectedButton = new Button("Add");
		selectedButton = new Button();
		buttonLayer = new HBox(unselectedButton);
		initButtonLayer();
		
		getStyleClass().add(TOPIC_SELECTOR_CSS);
		getChildren().addAll(labelLayer, buttonLayer);
		HBox.setHgrow(unselectedButton, Priority.ALWAYS);
	}

	private void initLabelLayer() {
		labelLayer.getStyleClass().add(LABEL_LAYER_CSS);
	}

	private void initButtonLayer() {
		initButtons();
		buttonLayer.getStyleClass().add(BUTTON_LAYER_CSS);
	}
	
	private void initButtons() {
		initUnselectedButton();
		initSelectedButton();
		initAddedButton();
	}

	private void initSelectedButton() {
		selectedButton.getStyleClass().add(SELECTED_BUTTON_CSS);
		selectedButton.setGraphic(new ImageView(Main.BLUE_CHECK));
	}

	private void initUnselectedButton() {
		unselectedButton.getStyleClass().add(UNSELECTED_BUTTON_CSS);
		unselectedButton.setOnAction(e -> unselectedButtonAction());
		unselectedButton.setGraphic(new ImageView(Main.GREEN_PLUS));
	}
	
	private void unselectedButtonAction() {
		setSelected();
	}
	
	private void initAddedButton() {
		addedButton.getStyleClass().add(ADDED_BUTTON_CSS);
		addedButton.setDisable(true);
	}
	
	public void setAdded() {
		setButton(addedButton);
	}
	
	public void setUnselected() {
		setButton(unselectedButton);
	}
	
	public void setSelected() {
		setButton(selectedButton);
	}
	
	public boolean isAdded() {
		return currentButton() == addedButton;
	}
	
	public boolean isUnselected() {
		return currentButton() == unselectedButton;
	}
	
	public boolean isSelected() {
		return currentButton() == selectedButton;
	}
	
	private void setButton(Button button) {
		buttonLayer.getChildren().set(0, button);
	}
	
	private Button currentButton() {
		return (Button) buttonLayer.getChildren().get(0);
	}
	
	public TopicFactory<?> factory() {
		return factory;
	}
	
}