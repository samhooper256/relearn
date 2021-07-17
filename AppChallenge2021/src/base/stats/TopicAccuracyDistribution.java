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
	
	private final StackedBarChart<Number, String> chart;
	private final CategoryAxis nameAxis;
	private final NumberAxis accuracyAxis;
	
	TopicAccuracyDistribution(ProblemSet set) {
		nameAxis = new CategoryAxis();
		for(Topic t : set.topics())
			nameAxis.getCategories().add(t.name());
		accuracyAxis = new NumberAxis(0, 1, 1);
		chart = new StackedBarChart<>(accuracyAxis, nameAxis);
		
		setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP);
		chart.widthProperty().addListener((obs, b, b1) -> {
			Platform.runLater(() -> setMaxCategoryWidth(MAX_CATEGORY_WIDTH, MIN_CATEGORY_GAP));
		});
		
		getChildren().add(chart);
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
