package base.stats;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

final class AccuracyDisplay extends StackPane {
	
	private static final String
			ACCURACY_DISPLAY_CSS = "accuracy-display",
			CORRECT_CSS = "correct",
			COLON_CSS = "colon",
			INCORRECT_CSS = "incorrect",
			NONE_CSS = "none",
			FRACTION_BOX_CSS = "fraction-box",
			TOTAL_CSS = "total",
			VBOX_CSS = "vbox";
	
	private final Circle circle;
	private final Label correct, colon, incorrect, none, total;
	private final HBox fractionBox;
	private final VBox vBox;
	
	private AccuracyPie realPie;
	private DeadPie deadPie;
	
	public AccuracyDisplay() {
		circle = new Circle();
		initCircle();
		correct = new Label();
		colon = new Label(":");
		incorrect = new Label();
		fractionBox = new HBox(correct, colon, incorrect);
		none = new Label("None");
		total = new Label();
		vBox = new VBox(fractionBox, total);
		initVBox();
		getChildren().addAll(circle, vBox);
		getStyleClass().add(ACCURACY_DISPLAY_CSS);
		
		setToNone();
	}

	private void initCircle() {
		circle.radiusProperty().bind(Bindings.min(widthProperty(), heightProperty()).divide(4));
		circle.getStyleClass().add("circle");
	}
	
	private void initVBox() {
		initFractionBox();
		total.getStyleClass().add(TOTAL_CSS);
		vBox.getStyleClass().add(VBOX_CSS);
	}
	
	private void initFractionBox() {
		fractionBox.getStyleClass().add(FRACTION_BOX_CSS);
		correct.getStyleClass().add(CORRECT_CSS);
		colon.getStyleClass().add(COLON_CSS);
		incorrect.getStyleClass().add(INCORRECT_CSS);
		none.getStyleClass().add(NONE_CSS);
	}

	void setAccuracy(ReadOnlyAccuracyStats stats) {
		if(stats.total() == 0)
			setToNone();
		else
			setNonZeroAccuracy(stats);
	}
	
	private void setToNone() {
		fractionBox.getChildren().clear();
		fractionBox.getChildren().add(none);
		vBox.getChildren().remove(total);
		setPie(deadPie());
	}
	
	private void setNonZeroAccuracy(ReadOnlyAccuracyStats stats) {
		setFraction(stats);
		total.setText(String.valueOf(stats.total()));
		if(!vBox.getChildren().contains(total))
			vBox.getChildren().add(total);
		realPie().setAccuracy(stats);
		setPie(realPie());
	}
	
	private void setFraction(ReadOnlyAccuracyStats stats) {
		ObservableList<Node> children = fractionBox.getChildren();
		if(children.size() != 3) {
			children.clear();
			children.addAll(correct, colon, incorrect);
		}
		correct.setText(String.valueOf(stats.correct()));
		incorrect.setText(String.valueOf(stats.incorrect()));
	}
	
	private void setPie(PieChart pie) {
		ObservableList<Node> children = getChildren();
		if(children.get(0) instanceof PieChart)
			children.set(0, pie);
		else
			children.add(0, pie);
	}
	
	private AccuracyPie realPie() {
		if(realPie == null)
			realPie = new AccuracyPie();
		realPie.setLabelsVisible(false);
		realPie.setStartAngle(90);
		return realPie;
	}
	
	private DeadPie deadPie() {
		if(deadPie == null)
			deadPie = new DeadPie();
		return deadPie;
	}
	
	
	
}
