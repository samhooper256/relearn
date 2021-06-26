/**
 * 
 */
package base.sets;

import java.util.*;

import fxutils.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicPortionBar extends GridPane {
	
	private static final class Segment extends StackPane {
		
		public static final Map<String, Segment> CACHE = new HashMap<>();
		
		public static Segment of(String topicName) {
			if(CACHE.containsKey(topicName))
				return CACHE.get(topicName);
			Segment segment = new Segment(topicName);
			CACHE.put(topicName, segment);
			return segment;
		}
		
		private final String topicName;
		
		private Segment(String topicName) {
			this.topicName = topicName;
			this.setBackground(Backgrounds.of(TopicUtils.colorOf(topicName)));
			getChildren().add(new Label(topicName));
		}
		
	}
	
	private static final TopicPortionBar INSTANCE = new TopicPortionBar();
	
	public static TopicPortionBar get() {
		return INSTANCE;
	}
	
	private static int total(Collection<Topic> topics) {
		return topics.stream().mapToInt(Topic::count).sum();
	}
	
	private TopicPortionBar() {
		setBorder(Borders.of(Color.RED));
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		getRowConstraints().add(r);
	}
	
	public void update(Collection<Topic> topics) {
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
