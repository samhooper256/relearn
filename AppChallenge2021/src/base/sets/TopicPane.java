/**
 * 
 */
package base.sets;

import java.util.IdentityHashMap;

import fxutils.IntField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public final class TopicPane extends TitledPane {
	
	private static final int INT_FIELD_WIDTH = 35;
	private static final IdentityHashMap<Topic, TopicPane> CACHE = new IdentityHashMap<>();
	
	private final Topic topic;
	private final IntField field;
	private final StackPane content;
	private final VBox vBox;
	
	public static TopicPane of(Topic topic) {
		TopicPane cached = CACHE.get(topic);
		if(cached != null)
			return cached;
		TopicPane obj = new TopicPane(topic);
		CACHE.put(topic, obj);
		return obj;
	}
	
	private TopicPane(Topic topic) {
		this.topic = topic;
		field = new IntField(INT_FIELD_WIDTH);
		initField();
		
		content = new StackPane();
		vBox = new VBox();
		initContent();
		
		setContent(content);
		setText(topic.name());
		setGraphic(this.field);
	}
	
	private void initField() {
		field.setText(String.valueOf(topic.count()));
		topic.countProperty().bind(field.valueProperty());
		field.valueProperty().addListener((x, y, z) -> {
			TopicPortionBar.get().update(EditorPane.get().currentSet().config().topics());
		});
	}

	private void initContent() {
		for(TopicSetting setting : topic.settings())
			vBox.getChildren().add(TopicSetting.settingNodeFor(setting));
		content.getChildren().add(vBox);
	}
	
}
