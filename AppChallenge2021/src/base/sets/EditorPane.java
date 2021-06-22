/**
 * 
 */
package base.sets;

import java.util.*;

import base.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane {
	
	private static final String TITLE = "Set Editor";
	
	private static EditorPane INSTANCE = null;
	private ProblemSet set;
	private String nameOnOpening;
	private final VBox primaryLayer, topicPaneContainer;
	private final HBox topLayer, nameLayer, topicLayer;
	private final BackArrow backArrow;
	private final Button addTopicButton;
	private final TextField nameField;
	private final Label nameLabel;
	private final TopicSelectionPane tsp;
	
	public static EditorPane get() {
		//double-checked locking pattern
		if(INSTANCE == null){
	        synchronized (EditorPane.class) {
	            if(INSTANCE == null){
	            	INSTANCE = new EditorPane();
	            }
	        }
	    }
	    return INSTANCE;
	}
	
	private EditorPane() {
		set = null;
		
		//top layer:
		backArrow = new BackArrow();
		topicPaneContainer = new VBox();
		initBackArrow();
		Label headerLabel = new Label(TITLE);
		headerLabel.setFont(Font.font(24));
		topLayer = new HBox(backArrow, headerLabel);
		
		//name layer:
		nameLabel = new Label("Name:");
		nameField = new TextField();
		nameLayer = new HBox(nameLabel, nameField);
		initNameLayer();
		
		//topic layer
		addTopicButton = new Button("+ Add Topic");
		topicLayer = new HBox(addTopicButton, topicPaneContainer);
		initTopicLayer();
		
		primaryLayer = new VBox(topLayer, nameLayer, topicLayer);
		
		tsp = new TopicSelectionPane();
		
		getChildren().add(primaryLayer);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}

	private void backArrowAction() {
		set.setName(nameField.getText().strip());
		set.saveToFile(nameOnOpening);
		if(!set.isRegistered())
			set.register();
		Main.scene().showSets();
	}
	
	private void initTopicLayer() {
		topicLayer.setSpacing(20);
		topicLayer.setPadding(new Insets(25));
		HBox.setHgrow(topicPaneContainer, Priority.ALWAYS);
		initAddTopicButton();
	}
	
	private void initAddTopicButton() {
		addTopicButton.setOnAction(e -> addTopicAction());
	}
	
	private void addTopicAction() {
		showTopicSelectionPane();
	}
	
	private void showTopicSelectionPane() {
		tsp.fadeOnto(this);
	}
	
	private void initNameLayer() {
		nameLayer.setSpacing(20);
		nameLayer.setAlignment(Pos.CENTER);
	}
	
	public void edit(ProblemSet set) {
		this.set = set;
		nameOnOpening = set.name();
		nameField.setText(nameOnOpening);
		topicPaneContainer.getChildren().clear();
		for(Topic t : set.config().topics())
			addTopicPaneFor(t);
	}
	
	public void createFreshSet() {
		edit(new ProblemSet());
	}
	
	public void addTopics(Collection<Topic> topics) {
		set.addTopics(topics);
		addTopicPanesFor(topics);
	}
	
	private void addTopicPanesFor(Collection<Topic> topics) {
		for(Topic t : topics)
			addTopicPaneFor(t);
	}
	
	private void addTopicPaneFor(Topic topic) {
		topicPaneContainer.getChildren().add(TopicPane.of(topic));
	}
	
	public void hideTopicSelectionPane() {
		tsp.fadeOutFrom(this);
	}
	
}
