package topics;

import base.*;
import fxutils.IntField;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IntRangeDisplay extends HBox implements IndependentlyVerifiable {
	
	private static final String
			INT_RANGE_CSS = "int-range",
			LOW_CSS = "low",
			HIGH_CSS = "high";
	private static final double FIELD_WIDTH = 40;
	
	private static final String
			EMPTY_ERROR_MESSAGE = "Values must not be empty",
			ORDER_ERROR_MESSAGE = "First value must be less than or equal to second";
	
	private final IntRange range;
	private final Label name, to;
	private final IntField low, high;
	private final Info info;
	private final ErrorMessage error;
	private final String rangeErrorMessage;
	
	public IntRangeDisplay(IntRange range) {
		this.range = range;
		name = new Label(range.name());
		to = new Label("to");
		low = IntField.withRange(range.min(), range.max(), range.low());
		high = IntField.withRange(range.min(), range.max(), range.high());
		initFields();
		error = new ErrorMessage("Values must not be empty");
		rangeErrorMessage = String.format("Values must in the range %d to %d, inclusive", range.min(), range.max());
		initError();
		info = new Info(rangeErrorMessage);
		
		getChildren().addAll(name, low, to, high, info, error);
		getStyleClass().add(INT_RANGE_CSS);
	}
	
	private void initFields() {
		initLow();
		initHigh();
	}

	private void initLow() {
		low.valueProperty().addListener(lowListener());
		low.setPrefWidth(FIELD_WIDTH);
		low.getStyleClass().add(LOW_CSS);
	}

	private void initHigh() {
		high.valueProperty().addListener(highListener());
		high.setPrefWidth(FIELD_WIDTH);
		high.getStyleClass().add(HIGH_CSS);
	}

	private ChangeListener<Number> lowListener() {
		return (o, oldValue, newValue) -> {
			if(!low.isEmpty()) {
				hideError();
				range.setLowIfPossible(newValue.intValue());
			}
		};
	}
	
	private ChangeListener<Number> highListener() {
		return (o, oldValue, newValue) -> {
			if(!high.isEmpty()) {
				hideError();
				range.setHighIfPossible(newValue.intValue());
			}
		};
	}

	private void initError() {
		hideError();
	}
	
	private void hideError() {
		error.setVisible(false);
	}
	
	private void showEmptyError() {
		showError(EMPTY_ERROR_MESSAGE);
	}

	private void showOrderError() {
		showError(ORDER_ERROR_MESSAGE);
	}
	
	private void showRangeError() {
		showError(rangeErrorMessage);
	}
	
	private void showError(String message) {
		error.setText(message);
		error.setVisible(true);
	}
	
	@Override
	public VerificationResult verify() {
		if(high.isEmpty() || low.isEmpty()) {
			showEmptyError();
			return VerificationResult.failure(EMPTY_ERROR_MESSAGE);
		}
		int lval = low.intValue(), hval = high.intValue();
		if(lval > hval) {
			showOrderError();
			return VerificationResult.failure(ORDER_ERROR_MESSAGE);
		}
		if(!range.inRange(lval) || !range.inRange(hval)) {
			showRangeError();
			return VerificationResult.failure(rangeErrorMessage);
		}
		return VerificationResult.success();
	}
	
}
