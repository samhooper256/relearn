/**
 * 
 */
package base;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane {
	
	private static final String HEADER = "Set Editor";
	private static EditorPane INSTANCE = null;
	
	private ProblemSet set;
	private String nameOnOpening;
	private final VBox outerBox, topicPaneContainer;
	private final HBox topLayer, nameLayer, topicLayer;
	private final ImageView backArrowView;
	private final Button addTopicButton;
	private final TextField nameField;
	private final Label nameLabel;
	
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
		backArrowView = new ImageView(Main.backArrowImage());
		topicPaneContainer = new VBox();
		initBackArrow();
		Label headerLabel = new Label(HEADER);
		headerLabel.setFont(Font.font(24));
		topLayer = new HBox(backArrowView, headerLabel);
		
		//name layer:
		nameLabel = new Label("Name:");
		nameField = new TextField();
		nameLayer = new HBox(nameLabel, nameField);
		initNameLayer();
		
		//topic layer
		addTopicButton = new Button("+ Add Topic");
		topicLayer = new HBox(addTopicButton, topicPaneContainer);
		initMidLayer();
		
		outerBox = new VBox(topLayer, nameLayer, topicLayer);
		
		getChildren().add(outerBox);
	}
	
	private void initBackArrow() {
		backArrowView.setOnMouseClicked(e -> backArrowAction());
	}

	private void backArrowAction() {
		set.setName(nameField.getText().strip());
		set.saveToFile(nameOnOpening);
		Main.mainScene().showSets();
	}
	
	private void initMidLayer() {
		topicLayer.setSpacing(20);
		topicLayer.setPadding(new Insets(25));
		HBox.setHgrow(topicPaneContainer, Priority.ALWAYS);
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
			topicPaneContainer.getChildren().add(TopicPane.of(t));
	}
	
	public void createFreshSet() {
		edit(new ProblemSet());
	}
	
}
