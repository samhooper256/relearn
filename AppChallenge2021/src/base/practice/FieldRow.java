package base.practice;

import base.problems.*;
import fxutils.*;
import javafx.beans.binding.DoubleBinding;
import javafx.css.PseudoClass;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import math.BigUtils;

/**
 * @author Sam Hooper
 */
final class FieldRow extends HBox {
	
	private static final KeyCode
			SUBMIT_SHORTCUT = KeyCode.ENTER;
	static final double FIELD_WIDTH = 400;
	private static final PseudoClass FIELD_INCORRECT_PSEUDO_CLASS = PseudoClass.getPseudoClass("incorrect");
	private static final String
			FIELD_ROW_CSS = "field-row",
			FIELD_CSS = "field",
			LEFT_CSS = "left",
			RIGHT_CSS = "right",
			APPROX_CSS = "approx",
			APPROX_TOOLTIP_CSS = "approx-tooltip",
			COMMA_CSS = "comma";
	
	private static Label createComma() {
		Label comma = new Label(",");
		comma.getStyleClass().add(COMMA_CSS);
		return comma;
	}
	
	private final TextField field;
	private final HBox left;
	private final SeparatorHBox right;
	private final Label approx;
	private final Tooltip approxTooltip;
	
	FieldRow() {
		field = new TextField();
		initField();
		left = new HBox();
		right = new SeparatorHBox(FieldRow::createComma);
		initSides();
		approx = new Label("\u2248"); //\u2248 is the approximately equal symbol.
		approxTooltip = new Tooltip();
		initApprox();
		getChildren().addAll(left, field, right);
		getStyleClass().add(FIELD_ROW_CSS);
	}

	private void initField() {
		field.setPrefWidth(FIELD_WIDTH);
		field.pseudoClassStateChanged(FIELD_INCORRECT_PSEUDO_CLASS, false);
		field.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == SUBMIT_SHORTCUT)
				userArea().submitAction();
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
	
	private void initApprox() {
		approx.getStyleClass().add(APPROX_CSS);
		approx.setTooltip(approxTooltip);
		approxTooltip.setShowDelay(Duration.millis(250));
		approxTooltip.getStyleClass().add(APPROX_TOOLTIP_CSS);
	}
	
	void setupProblem(Problem problem) {
		right.clearChildren();
		left.getChildren().clear();
		if(problem instanceof MathProblem) {
			MathProblem mp = (MathProblem) problem;
			for(MathAnswerMode mode : mp.answerModes()) {
				Label icon = mode.icon();
				right.addChild(icon);
			}
			if(mp.isTolerant()) {
				approxTooltip.setText(String.format("Answers must be within %s%% of the true value",
						BigUtils.toPrettyString(mp.tolerance().multiply(BigUtils.HUNDRED))));
				left.getChildren().add(approx);
			}
		}
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
