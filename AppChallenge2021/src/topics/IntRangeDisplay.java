package topics;

import base.*;
import fxutils.IntField;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IntRangeDisplay extends HBox implements IndependentlyVerifiable {
	
	private static final String INT_RANGE_CSS = "int-range";
	private static final double FIELD_WIDTH = 40;
	
	private final IntRange range;
	private final Label name, to;
	private final IntField low, high;
	private final ErrorMessage error;
	
	public IntRangeDisplay(IntRange range) {
		this.range = range;
		name = new Label(range.name());
		to = new Label("to");
		low = new IntField(range.min(), range.max(), range.low());
		high = new IntField(range.min(), range.max(), range.high());
		initFields();
		error = new ErrorMessage("Values must not be empty");
		initError();
		
		getChildren().addAll(name, low, to, high, error);
		getStyleClass().add(INT_RANGE_CSS);
	}
	
	private void initFields() {
		initLow();
		initHigh();
	}

	private void initLow() {
		low.valueProperty().addListener(lowListener());
		low.setPrefWidth(FIELD_WIDTH);
	}

	private void initHigh() {
		high.valueProperty().addListener(highListener());
		high.setPrefWidth(FIELD_WIDTH);
	}

	private ChangeListener<Number> lowListener() {
		return (o, oldValue, newValue) -> {
			if(!low.isEmpty()) {
				hideError();
				range.setLow(newValue.intValue());
			}
		};
	}
	
	private ChangeListener<Number> highListener() {
		return (o, oldValue, newValue) -> {
			if(!high.isEmpty()) {
				hideError();
				range.setHigh(newValue.intValue());
			}
		};
	}

	private void initError() {
		hideError();
	}
	
	private void hideError() {
		error.setVisible(false);
	}
	
	private void showError() {
		error.setVisible(true);
	}
	
	@Override
	public VerificationResult verify() {
		if(high.isEmpty() || low.isEmpty()) {
			showError();
			return VerificationResult.failure();
		}
		return VerificationResult.success();
	}
	
}
