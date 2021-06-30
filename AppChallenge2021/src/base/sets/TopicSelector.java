/**
 * 
 */
package base.sets;

import base.Main;
import fxutils.Images;
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
			NAME_LAYER = "name-layer",
			NAME_CSS = "name",
			BUTTON_LAYER_CSS = "button-layer",
			BUTTON_GRAPIHC_CSS = "graphic";
	
	private final TopicFactory<?> factory;
	private final HBox nameLayer, buttonLayer;
	private final Label name;
	private final Button unselectedButton, selectedButton, addedButton;
	private final ImageView unselectedGraphic, selectedGraphic;
	
	public TopicSelector(TopicFactory<?> factory) {
		this.factory = factory;
		name = new Label(factory.name());
		nameLayer = new HBox(name);
		initLabelLayer();
		
		unselectedButton = new Button("Add");
		unselectedGraphic = new ImageView();
		selectedButton = new Button();
		selectedGraphic = new ImageView();
		addedButton = new Button("Added");
		buttonLayer = new HBox(unselectedButton);
		initButtonLayer();
		
		getStyleClass().add(TOPIC_SELECTOR_CSS);
		getChildren().addAll(nameLayer, buttonLayer);
		HBox.setHgrow(unselectedButton, Priority.ALWAYS);
	}

	private void initLabelLayer() {
		name.getStyleClass().add(NAME_CSS);
		nameLayer.getStyleClass().add(NAME_LAYER);
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

	private void initUnselectedButton() {
		unselectedButton.getStyleClass().add(UNSELECTED_BUTTON_CSS);
		unselectedButton.setOnAction(e -> unselectedButtonAction());
		unselectedButton.setGraphic(unselectedGraphic);
	}
	
	private void unselectedButtonAction() {
		setSelected();
	}
	
	private void initSelectedButton() {
		Images.setFitSize(selectedGraphic, Main.BUTTON_ICON_SIZE, Main.BUTTON_ICON_SIZE);
		selectedGraphic.getStyleClass().add(BUTTON_GRAPIHC_CSS);
		selectedButton.setGraphic(selectedGraphic);
		selectedButton.getStyleClass().add(SELECTED_BUTTON_CSS);
		selectedButton.setOnAction(e -> selectedButtonAction());
	}
	
	private void selectedButtonAction() {
		setUnselected();
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
		box().notifyUnselected();
	}
	
	public void setSelected() {
		setButton(selectedButton);
		box().notifySelected();
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
	
	public TopicSelectorBox box() {
		return (TopicSelectorBox) getParent();
	}
}