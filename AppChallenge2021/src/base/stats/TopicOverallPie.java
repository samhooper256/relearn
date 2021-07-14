package base.stats;

import java.util.*;

import base.stats.Data.DataMap;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import topics.TopicUtils;

final class TopicOverallPie extends PieChart {
	
	private record SliceInfo(PieChart.Data data, Tooltip tooltip) {};
	
	private static final String TOOLTIP_CSS = "topic-overall-pie-tooltip";
	private static final Duration TOOLTIP_DELAY = Duration.ZERO;

	private final Map<String, SliceInfo> sliceMap;
	
	TopicOverallPie() {
		sliceMap = new HashMap<>();
		setLegendVisible(false);
		setLabelsVisible(false);
	}
	
	void update(DataMap map) {
		if(sliceMap.isEmpty())
			initSlices(map);
		for(Map.Entry<String, Stats> e : map.entrySet())
			dataFor(e.getKey()).setPieValue(e.getValue().total());
		getData().clear();
		TopicUtils.streamNames().forEachOrdered(topicName -> {
			if(map.get(topicName).total() > 0) {
				updateTooltip(topicName, map, base.stats.Data.overall().total());
				getData().add(dataFor(topicName));
			}
		});
	}
	
	private void initSlices(DataMap map) {
		TopicUtils.streamNames().forEachOrdered(topicName -> sliceMap.put(topicName,
				new SliceInfo(createSlice(topicName, map), createTooltip(topicName))));
	}
	
	private PieChart.Data createSlice(String topicName, DataMap map) {
		PieChart.Data data = new PieChart.Data(topicName, map.get(topicName).total());
		data.nodeProperty().addListener((x, oldNode, newNode) -> {
			Tooltip tool = tooltipFor(topicName);
			if(oldNode != null)
				Tooltip.uninstall(oldNode, tool);
			if(newNode instanceof Region r) {
				r.setBackground(TopicUtils.backgroundFor(topicName));
				Tooltip.install(r, tool);
			}
		});
		return data;
	}
	
	private Tooltip createTooltip(String topicName) {
		Tooltip t = new Tooltip(topicName);
		t.setShowDelay(TOOLTIP_DELAY);
		t.setHideDelay(TOOLTIP_DELAY);
		t.getStyleClass().add(TOOLTIP_CSS);
		return t;
	}
	
	final PieChart.Data dataFor(String topicName) {
		return sliceMap.get(topicName).data();
	}
	
	final Tooltip tooltipFor(String topicName) {
		return sliceMap.get(topicName).tooltip();
	}
	
	private void updateTooltip(String topicName, DataMap map, int total) {
		Tooltip t = tooltipFor(topicName);
		t.setText(String.format("%.0f%% %s", 100d * map.get(topicName).total() / total, topicName));
	}
}
