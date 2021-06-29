/**
 * 
 */
package base.sets;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelector extends HBox {
	
	private static final String
			TOPIC_SELECTOR_CSS = "topic-selector",
			ADDED_BUTTON_CSS = "added-button",
			UNSELECTED_BUTTON_CSS = "unselected-button",
			SELECTED_BUTTON_CSS = "selected-button";
	
	private final TopicFactory<?> factory;
	private final Label label;
	private final Button addedButton, unselectedButton, selectedButton;
	
	public TopicSelector(TopicFactory<?> factory) {
		this.factory = factory;
		label = new Label(factory.name());
		
		addedButton = new Button("Added");
		unselectedButton = new Button("+ Add");
		selectedButton = new Button("CHECK Add");
		initButtons();
		
		getStyleClass().add(TOPIC_SELECTOR_CSS);
		getChildren().addAll(label, unselectedButton);
		HBox.setHgrow(label, Priority.ALWAYS);
	}

	private void initButtons() {
		initAddedButton();
		initUnselectedButton();
		selectedButton.getStyleClass().add(SELECTED_BUTTON_CSS);
	}

	private void initAddedButton() {
		addedButton.getStyleClass().add(ADDED_BUTTON_CSS);
		addedButton.setDisable(true);
	}
	
	private void initUnselectedButton() {
		unselectedButton.getStyleClass().add(UNSELECTED_BUTTON_CSS);
		unselectedButton.setOnAction(e -> unselectedButtonAction());
	}
	
	private void unselectedButtonAction() {
		setSelected();
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
		getChildren().set(1, button);
	}
	
	private Button currentButton() {
		return (Button) getChildren().get(1);
	}
	
	public TopicFactory<?> factory() {
		return factory;
	}
	
}