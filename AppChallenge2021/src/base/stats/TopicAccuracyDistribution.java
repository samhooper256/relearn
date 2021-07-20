package base.stats;

import java.util.Map;

import base.sets.ProblemSet;
import base.stats.Data.DataMap;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import topics.Topic;

final class TopicAccuracyDistribution extends StackPane {
	
	private static final double MAX_CATEGORY_WIDTH = 100, MIN_CATEGORY_GAP = 10;
	private static final String
			CHART_CSS = "stacked-accuracy-chart",
			NAME_AXIS_CSS = "name-axis",
			ACCURACY_AXIS_CSS = "accuracy-axis";
	
	private final StackedBarChart<Number, String> chart;
	private final CategoryAxis nameAxis;
	private final NumberAxis accuracyAxis;
	
	TopicAccuracyDistribution(ProblemSet set) {
		nameAxis = new CategoryAxis();
		initNameAxis(set);
		accuracyAxis = new NumberAxis(0, 1, 1);
		accuracyAxis.getStyleClass().add(ACCURACY_AXIS_CSS);
		chart = new StackedBarChart<>(accuracyAxis, nameAxis);
		setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP);
		chart.widthProperty().addListener((obs, b, b1) -> {
			Platform.runLater(() -> setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP));
		});
		chart.setAnimated(false);
		chart.getStyleClass().add(CHART_CSS);
		getChildren().add(chart);
	}
	
	private void initNameAxis(ProblemSet set) {
		nameAxis.getStyleClass().add(NAME_AXIS_CSS);
		for(Topic t : set.topics())
			nameAxis.getCategories().add(t.name());
	}
	
	@SuppressWarnings("unchecked")
	void update(DataMap map) {
		ObservableList<XYChart.Data<Number, String>> correctSeriesData = FXCollections.observableArrayList();
		ObservableList<XYChart.Data<Number, String>> incorrectSeriesData = FXCollections.observableArrayList();
		
		int nonZeroTopicCount = 0;
		for(Map.Entry<String, Stats> e : map.entrySet()) {
			String name = e.getKey();
			Stats s = e.getValue();
			if(!s.isEmpty()) {
				nonZeroTopicCount++;
				correctSeriesData.add(new XYChart.Data<>(s.accuracy(), name));
				incorrectSeriesData.add(new XYChart.Data<>(1 - s.accuracy(), name));
			}
		}
		setMaxHeight(MAX_CATEGORY_WIDTH * nonZeroTopicCount + 50);
		
		ObservableList<Series<Number, String>> data = chart.getData();
		data.clear();
		data.addAll(new Series<>(correctSeriesData), new Series<>(incorrectSeriesData));
	}
	
	private void setMaxCategoryWidth(double maxCategoryWidth, double minCategoryGap){
	    double catSpace = nameAxis.getCategorySpacing();
	    chart.setCategoryGap(catSpace - Math.min(maxCategoryWidth, catSpace - minCategoryGap));
	}
	
}
