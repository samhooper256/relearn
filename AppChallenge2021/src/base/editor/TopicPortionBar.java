/**
 * 
 */
package base.editor;

import java.util.*;

import base.Named;
import fxutils.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicPortionBar extends GridPane {
	
	private static final class Segment extends StackPane implements Named {
		
		public static final Map<String, Segment> CACHE = new HashMap<>();
		
		private static final String SEGMENT_TOOLTIP_CSS = "segment-tooltip";
		
		public static Segment of(String topicName) {
			if(CACHE.containsKey(topicName))
				return CACHE.get(topicName);
			Segment segment = new Segment(topicName);
			CACHE.put(topicName, segment);
			return segment;
		}
		
		private final String topicName;
		private final Tooltip tooltip;
		private final Label label;
		
		private Segment(String topicName) {
			this.topicName = topicName;
			label = new Label(topicName);
			label.visibleProperty().bind(this.widthProperty().greaterThan(label.widthProperty()));
			
			tooltip = new Tooltip(topicName);
			initTooltip();
			this.setBackground(Backgrounds.of(TopicUtils.colorOf(topicName)));
			getChildren().add(label);
		}

		private void initTooltip() {
			tooltip.setShowDelay(Duration.ZERO);
			Tooltip.install(this, tooltip);
			tooltip.getStyleClass().add(SEGMENT_TOOLTIP_CSS);
		}

		@Override
		public String name() {
			return topicName;
		}
		
	}
	
	private static final class Empty extends StackPane {
		
		private static final String EMPTY_CSS = "empty";
		private static final Empty INSTANCE = new Empty();
		
		public static Empty get() {
			return INSTANCE;
		}
		
		private final Label label;
		
		private Empty() {
			label = new Label("No topics");
			getStyleClass().add(EMPTY_CSS);
			getChildren().add(label);
		}
		
	}
	
	private static final String TOPIC_PORTION_BAR_CSS = "topic-portion-bar";
	private static final TopicPortionBar INSTANCE = new TopicPortionBar();
	
	public static TopicPortionBar get() {
		return INSTANCE;
	}
	
	private static int total(Iterable<Topic> topics) {
		int total = 0;
		for(Topic t : topics)
			total += t.count();
		return total;
	}
	
	private boolean isShowingEmpty;
	private final ColumnConstraints emptyConstraints;
	
	private TopicPortionBar() {
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		getRowConstraints().add(r);
		emptyConstraints = new ColumnConstraints();
		emptyConstraints.setPercentWidth(100);
		getStyleClass().add(TOPIC_PORTION_BAR_CSS);
		showEmpty();
	}
	
	public void update(Iterable<Topic> topics) {
		double total = total(topics);
		if(total == 0) {
			showEmpty();
			return;
		}
		hideEmpty();
		int index = 0;
		getChildren().clear();
		getColumnConstraints().clear();
		for(Topic t : topics) {
			ColumnConstraints c = new ColumnConstraints();
			getColumnConstraints().add(c);
			c.setPercentWidth(100d * t.count() / total);
			Segment seg = Segment.of(t.name());
			add(seg, index, 0);
			index++;
		}
	}
	
	private void showEmpty() {
		if(isShowingEmpty)
			return;
		isShowingEmpty = true;
		getChildren().clear();
		getChildren().add(Empty.get());
		getColumnConstraints().clear();
		getColumnConstraints().add(emptyConstraints);
	}
	
	private void hideEmpty() {
		if(!isShowingEmpty)
			return;
		isShowingEmpty = false;
		getChildren().clear();
		getColumnConstraints().clear();
	}
	
	public boolean isShowingEmpty() {
		return isShowingEmpty;
	}
	
}
