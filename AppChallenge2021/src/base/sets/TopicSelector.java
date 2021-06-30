/**
 * 
 */
package base.sets;

import base.Main;
import fxutils.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelector extends PolarizedPane {
	
	private static final String
			TOPIC_SELECTOR_CSS = "topic-selector",
			ADDED_BUTTON_CSS = "added-button",
			UNSELECTED_BUTTON_CSS = "unselected-button",
			SELECTED_BUTTON_CSS = "selected-button",
			NAME_CSS = "name",
			BUTTON_GRAPIHC_CSS = "graphic";
	
	private final TopicFactory<?> factory;
	private final Label name;
	private final Button unselectedButton, selectedButton, addedButton;
	private final ImageView unselectedGraphic, selectedGraphic;
	
	public TopicSelector(TopicFactory<?> factory) {
		this.factory = factory;
		name = new Label(factory.name());
		initName();
		
		unselectedButton = new Button("Add");
		unselectedGraphic = new ImageView();
		selectedButton = new Button();
		selectedGraphic = new ImageView();
		addedButton = new Button("Added");
		initButtons();
		
		getStyleClass().add(TOPIC_SELECTOR_CSS);
		setChildren(name, unselectedButton);
	}

	private void initName() {
		name.getStyleClass().add(NAME_CSS);
	}
	
	private void initButtons() {
		initUnselectedButton();
		initSelectedButton();
		initAddedButton();
	}

	private void initUnselectedButton() {
		Images.setFitSize(unselectedGraphic, Main.BUTTON_ICON_SIZE, Main.BUTTON_ICON_SIZE);
		unselectedGraphic.getStyleClass().add(BUTTON_GRAPIHC_CSS);
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
		if(!isAdded()) {
			setButton(addedButton);
			box().notifyUnselectedOrAdded();
		}
	}
	
	public void setUnselected() {
		if(!isUnselected()) {
			setButton(unselectedButton);
			box().notifyUnselectedOrAdded();
		}
	}
	
	public void setSelected() {
		if(!isSelected()) {
			setButton(selectedButton);
			box().notifySelected();
		}
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
		setRight(button);
	}
	
	private Button currentButton() {
		return (Button) right();
	}
	
	public TopicFactory<?> factory() {
		return factory;
	}
	
	public TopicSelectorBox box() {
		return (TopicSelectorBox) getParent();
	}
}