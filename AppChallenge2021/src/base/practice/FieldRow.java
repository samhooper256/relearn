package base.practice;

import javafx.beans.binding.DoubleBinding;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

final class FieldRow extends HBox {
	
	static final double FIELD_WIDTH = 400;
	private static final PseudoClass FIELD_INCORRECT_PSEUDO_CLASS = PseudoClass.getPseudoClass("incorrect");
	private static final String
			FIELD_ROW_CSS = "field-row",
			FIELD_CSS = "field",
			LEFT_CSS = "left",
			RIGHT_CSS = "right";
	
	private final TextField field;
	private final HBox left, right;
	
	FieldRow() {
		field = new TextField();
		initField();
		left = new HBox(new Label("hi"));
		right = new HBox(new Label("helooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"));
		initSides();
		getChildren().addAll(left, field, right);
		getStyleClass().add(FIELD_ROW_CSS);
	}

	private void initField() {
		field.setPrefWidth(FIELD_WIDTH);
		field.pseudoClassStateChanged(FIELD_INCORRECT_PSEUDO_CLASS, false);
		field.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				e.consume();
				userArea().submitAction();
			}
		});
		field.getStyleClass().add(FIELD_CSS);
	}
	
	private void initSides() {
		DoubleBinding binding = widthProperty().subtract(FIELD_WIDTH).divide(2);
		left.prefWidthProperty().bind(binding);
		right.prefWidthProperty().bind(binding);
		left.getStyleClass().add(LEFT_CSS);
		right.getStyleClass().add(RIGHT_CSS);
	}
	
	void setText(String text) {
		field.setText(text);
	}
	
	void clear() {
		setText("");
	}
	
	String text() {
		return field.getText();
	}
	
	void displayAsIncorrect() {
		field.pseudoClassStateChanged(FIELD_INCORRECT_PSEUDO_CLASS, true);
	}
	
	void displayAsCorrect() {
		field.pseudoClassStateChanged(FIELD_INCORRECT_PSEUDO_CLASS, false);
	}
	
	void focusOnField() {
		field.requestFocus();
	}
	
	private UserArea userArea() {
		return (UserArea) getParent().getParent();
	}
	
}
