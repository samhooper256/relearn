/**
 * 
 */
package base;

import fxutils.IntField;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public final class TopicPane extends TitledPane {
	
	private static final int INT_FIELD_WIDTH = 35;
	
	private final Topic topic;
	private final IntField field;
	private final StackPane content;
	private final VBox vBox;
	
	public static TopicPane of(Topic topic) {
		return new TopicPane(topic);
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
		field.textProperty().addListener(ov -> {
			if(field.hasValidInt())
				topic.setCount(field.intValue());
		});
	}

	private void initContent() {
		for(TopicSetting setting : topic.settings())
			vBox.getChildren().add(TopicSetting.settingNodeFor(setting));
		content.getChildren().add(vBox);
	}
	
}