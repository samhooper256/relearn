package base.stats;

import java.util.*;

import base.stats.Data.DataMap;

import javafx.scene.chart.PieChart;
import topics.TopicUtils;

final class TopicOverallPie extends PieChart {
	
	private final Map<String, PieChart.Data> sliceMap;
	
	TopicOverallPie() {
		sliceMap = new HashMap<>();
	}
	
	void update(DataMap map) {
		if(sliceMap.isEmpty())
			initSlices(map);
		for(Map.Entry<String, Stats> e : map.entrySet())
			dataFor(e.getKey()).setPieValue(e.getValue().total());
		getData().clear();
		TopicUtils.streamNames().forEachOrdered(topicName -> {
			if(map.get(topicName).total() > 0)
				getData().add(dataFor(topicName));
		});
	}
	
	private void initSlices(DataMap map) {
		TopicUtils.streamNames().forEachOrdered(topicName -> sliceMap.put
				(topicName, new PieChart.Data(topicName, map.get(topicName).total())));
	}
	
	private final PieChart.Data dataFor(String topicName) {
		return sliceMap.get(topicName);
	}
	
}
