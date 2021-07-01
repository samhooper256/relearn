/**
 * 
 */
package base.editor;

import java.util.*;

import base.Named;
import fxutils.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicPortionBar extends GridPane {
	
	private static final class Segment extends StackPane implements Named {
		
		public static final Map<String, Segment> CACHE = new HashMap<>();
		
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
			tooltip = new Tooltip(topicName);
			tooltip.setShowDelay(Duration.ZERO);
			label = new Label(topicName);
			label.visibleProperty().bind(this.widthProperty().greaterThan(label.widthProperty()));
			Tooltip.install(this, tooltip);
			this.setBackground(Backgrounds.of(TopicUtils.colorOf(topicName)));
			getChildren().add(label);
		}

		@Override
		public String name() {
			return topicName;
		}
		
	}
	
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
	
	private TopicPortionBar() {
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		getRowConstraints().add(r);
	}
	
	public void update(Iterable<Topic> topics) {
		getChildren().clear();
		getColumnConstraints().clear();
		double total = total(topics);
		int index = 0;
		for(Topic t : topics) {
			ColumnConstraints c = new ColumnConstraints();
			getColumnConstraints().add(c);
			c.setPercentWidth(100d * t.count() / total);
			Segment seg = Segment.of(t.name());
			add(seg, index, 0);
			index++;
		}
	}
	
	
}
