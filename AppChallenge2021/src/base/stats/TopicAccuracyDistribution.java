package base.stats;

import java.util.Map;

import base.sets.ProblemSet;
import base.stats.Data.DataMap;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import topics.Topic;

final class TopicAccuracyDistribution extends StackPane {
	
	private static final double MAX_CATEGORY_WIDTH = 100, MIN_CATEGORY_GAP = 10;
	private static final String
			TOPIC_ACCURACY_DISTRIBUTION_CSS = "topic-accuracy-distribution",
			NAME_AXIS_CSS = "name-axis",
			ACCURACY_AXIS_CSS = "accuracy-axis";
	
	private final StackedBarChart<Number, String> chart;
	private final CategoryAxis nameAxis;
	private final NumberAxis accuracyAxis;
	
	TopicAccuracyDistribution(ProblemSet set) {
		nameAxis = new CategoryAxis();
		nameAxis.getStyleClass().add(NAME_AXIS_CSS);
		for(Topic t : set.topics())
			nameAxis.getCategories().add(t.name());
		accuracyAxis = new NumberAxis(0, 1, 1);
		accuracyAxis.getStyleClass().add(ACCURACY_AXIS_CSS);
//		accuracyAxis.setVisible(false);
//		accuracyAxis.setMinorTickVisible(false);
//		accuracyAxis.setTickLabelsVisible(false);
		chart = new StackedBarChart<>(accuracyAxis, nameAxis);
		
		setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP);
		chart.widthProperty().addListener((obs, b, b1) -> {
			Platform.runLater(() -> setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP));
		});
		
		chart.getStyleClass().add(TOPIC_ACCURACY_DISTRIBUTION_CSS);
		System.out.printf("chart style class: [%s]%n", chart.getStyleClass());
		printChildren(chart, 1);
		getChildren().add(chart);
	}
	
	private static void printChildren(Parent n, int numTabs) {
		String tabs = "\t".repeat(numTabs);
		for(Node child : n.getChildrenUnmodifiable()) {
			System.out.printf("%s%s, {style: [%s]}%n", tabs, child, child.getStyleClass());
			if(child instanceof Parent p)
				printChildren(p, numTabs + 1);
		}
	}
	@SuppressWarnings("unchecked")
	void update(DataMap map) {
		ObservableList<XYChart.Data<Number, String>> correctSeriesData = FXCollections.observableArrayList();
		ObservableList<XYChart.Data<Number, String>> incorrectSeriesData = FXCollections.observableArrayList();
		
		for(Map.Entry<String, Stats> e : map.entrySet()) {
			String name = e.getKey();
			Stats s = e.getValue();
			if(!s.isEmpty()) {
				correctSeriesData.add(new XYChart.Data<>(s.accuracy(), name));
				incorrectSeriesData.add(new XYChart.Data<>(1 - s.accuracy(), name));
			}
		}
		
		ObservableList<Series<Number, String>> data = chart.getData();
		data.clear();
		data.addAll(new Series<>(correctSeriesData), new Series<>(incorrectSeriesData));
	}
	
	private void setMaxCategoryWidth(double maxCategoryWidth, double minCategoryGap){
	    double catSpace = nameAxis.getCategorySpacing();
	    chart.setCategoryGap(catSpace - Math.min(maxCategoryWidth, catSpace - minCategoryGap));
	}
	
}
