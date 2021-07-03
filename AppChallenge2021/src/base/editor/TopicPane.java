/**
 * 
 */
package base.editor;

import java.util.IdentityHashMap;

import base.*;
import fxutils.IntField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public final class TopicPane extends TitledPane implements IndependentlyVerifiable {
	
	private static final String
			TOPIC_PANE_CSS = "topic-pane",
			FIELD_CSS = "topic-count-field",
			GRAPHIC_CSS = "graphic";
	private static final IdentityHashMap<Topic, TopicPane> CACHE = new IdentityHashMap<>();
	private static final double FIELD_WIDTH = 40;
	private static final int FIELD_MIN_VALUE = 1, FIELD_MAX_VALUE = 999;
	
	private final Topic topic;
	private final IntField field;
	private final StackPane content;
	private final HBox graphic;
	private final SettingsBox settingsBox;
	private final Label topicTitle;
	private final TrashCan trashCan;
	
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
		field = new IntField(FIELD_MIN_VALUE, FIELD_MAX_VALUE);
		topicTitle = new Label(topic.name());
		trashCan = new TrashCan();
		graphic = new HBox(field, topicTitle, trashCan);
		initGraphic();
		
		settingsBox = new SettingsBox(topic.settings());
		content = new StackPane(settingsBox);
		
		setContent(content);
		setText("");
		setGraphic(graphic);
		getStyleClass().add(TOPIC_PANE_CSS);
	}
	
	private void initGraphic() {
		initField();
		initTrashCan();
		HBox.setHgrow(topicTitle, Priority.ALWAYS);
		graphic.getStyleClass().add(GRAPHIC_CSS);
	}
	
	private void initField() {
		field.setText(String.valueOf(topic.count()));
		topic.countProperty().bind(field.valueProperty());
		field.valueProperty().addListener((x, y, z) -> {
			TopicPortionBar.get().update(EditorPane.get().currentSet().config().topics());
		});
		field.setPrefWidth(FIELD_WIDTH);
		field.getStyleClass().add(FIELD_CSS);
	}
	
	private void initTrashCan() {
		trashCan.setVisible(false);
		trashCan.setPickOnBounds(true);
		trashCan.setOnAction(this::trashCanAction);
	}
	
	private void trashCanAction() {
		EditorPane.get().removeTopicPane(this);
	}
	
	public void setTrashCanVisible(boolean visible) {
		if(visible)
			showTrashCan();
		else
			hideTrashCan();
	}
	
	public void showTrashCan() {
		trashCan.setVisible(true);
	}
	
	public void hideTrashCan() {
		trashCan.setVisible(false);
	}
	
	public Topic topic() {
		return topic;
	}

	@Override
	public VerificationResult verify() {
		return settingsBox.verify();
	}
	
}
