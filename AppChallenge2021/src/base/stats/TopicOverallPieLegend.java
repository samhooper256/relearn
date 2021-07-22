package base.stats;

import java.util.HashMap;

import base.stats.Data.DataMap;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import topics.TopicUtils;

/**
 * 
 * @author Sam Hooper
 *
 */
final class TopicOverallPieLegend extends VBox {
	
	private static class LegendItem extends HBox {
		
		private static final String
			LEGEND_ITEM_CSS = "legend-item",
			LABEL_CSS = "label";
			
		private static final HashMap<String, LegendItem> CACHE = new HashMap<>();
		
		static LegendItem of(String topicName) {
			if(CACHE.containsKey(topicName))
				return CACHE.get(topicName);
			LegendItem item = new LegendItem(topicName);
			CACHE.put(topicName, item);
			return item;
		}
		
		private final Label label;
		private final Circle circle;
		
		private LegendItem(String topicName) {
			circle = new Circle(8);
			circle.setFill(TopicUtils.colorOf(topicName));
			label = new Label(topicName);
			label.getStyleClass().add(LABEL_CSS);
			getChildren().addAll(circle, label);
			getStyleClass().add(LEGEND_ITEM_CSS);
		}
		
	}
	
	private static final String
			TOPIC_OVERALL_PIE_LEGEND_CSS = "topic-overall-pie-legend",
			SCROLL_CSS = "scroll",
			VBOX_CSS = "vbox";
	
	private final ScrollPane scroll;
	private final VBox vBox;
	
	TopicOverallPieLegend() {
		vBox = new VBox();
		scroll = new ScrollPane(vBox);
		initScroll();
		getChildren().add(scroll);
		getStyleClass().add(TOPIC_OVERALL_PIE_LEGEND_CSS);
	}
	
	private void initScroll() {
		scroll.getStyleClass().add(SCROLL_CSS);
		vBox.getStyleClass().add(VBOX_CSS);
	}
	
	void addTopic(String topicName) {
		vBox.getChildren().add(LegendItem.of(topicName));
	}
	
	boolean removeTopic(String topicName) {
		return vBox.getChildren().remove(LegendItem.of(topicName));
	}
	
	void update() {
		if(Data.overall().isEmpty())
			setVisible(false);
		else {
			setVisible(true);
			vBox.getChildren().clear();
			DataMap map = Data.mapByTopics();
			TopicUtils.streamNames().forEachOrdered(topicName -> {
				ReadOnlyAccuracyStats stats = map.get(topicName);
				if(!stats.isEmpty())
					vBox.getChildren().add(LegendItem.of(topicName));
			});
		}
	}
	
}
