/**
 * 
 */
package base;

import java.util.*;
import java.util.stream.*;

import fxutils.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelectionPane extends FadePopup {
	
	private static class TopicSelector extends HBox {
		
		private final TopicFactory<?> factory;
		private final Label label;
		private final CheckBox checkBox;
		
		public TopicSelector(TopicFactory<?> factory) {
			this.factory = factory;
			label = new Label(factory.name());
			StackPane labelWrap = new StackPane(label);
			labelWrap.setAlignment(Pos.CENTER_LEFT);
			checkBox = new CheckBox();
			getChildren().addAll(labelWrap, checkBox);
			TopicSelector.this.setBorder(Borders.of(Color.RED));
			HBox.setHgrow(labelWrap, Priority.ALWAYS);
		}
		
		public TopicFactory<?> factory() {
			return factory;
		}
		
		public boolean isSelected() {
			return checkBox.isSelected();
		}
		
	}
	
	private final VBox outerVBox, selectorBox;
	private final Button addSelectedButton;
	private final HBox buttonBar;
	private final List<TopicSelector> selectors;
	
	public TopicSelectionPane() {
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setBackground(Backgrounds.of(Color.LIGHTBLUE));
		selectorBox = new VBox();
		selectors = new ArrayList<>();
		for(TopicFactory<?> factory : TopicUtils.allFactories()) {
			TopicSelector ts = new TopicSelector(factory);
			selectors.add(ts);
			selectorBox.getChildren().add(ts);
		}
		addSelectedButton = new Button("Add Selected");
		initAddSelectedButton();
		buttonBar = new HBox(addSelectedButton);
		outerVBox = new VBox(selectorBox, buttonBar);
		VBox.setVgrow(selectorBox, Priority.ALWAYS);
		getChildren().add(outerVBox);
	}
	
	private void initAddSelectedButton() {
		addSelectedButton.setOnAction(e -> addSelectedAction());
	}
	
	private void addSelectedAction() {
		EditorPane.get().addTopics(getSelectedTopics());
		hide();
	}
	
	private void hide() {
		EditorPane.get().hideTopicSelectionPane();
	}
	
	private Stream<TopicFactory<?>> streamSelectedFactories() {
		return selectors.stream().filter(TopicSelector::isSelected).map(TopicSelector::factory);
	}
	
	public Set<TopicFactory<?>> getSelectedFactories() {
		return streamSelectedFactories().collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Stream<Topic> streamSelectedTopics() {
		return streamSelectedFactories().map(TopicFactory::create);
	}
	
	public Set<Topic> getSelectedTopics() {
		return streamSelectedTopics().collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
}
