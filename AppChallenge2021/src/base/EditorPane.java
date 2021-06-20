/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane {
	
	private static final String HEADER = "Set Editor";
	
	private ProblemSet set;
	private final VBox outerBox;
	private final HBox topLayer;
	private final ImageView backArrowView;
	private final VBox topicPaneContainer;
	
	public EditorPane() {
		set = null;
		topLayer = new HBox();
		backArrowView = new ImageView(Main.backArrowImage());
		topicPaneContainer = new VBox();
		initBackArrow();
		Label headerLabel = new Label(HEADER);
		headerLabel.setFont(Font.font(24));
		topLayer.getChildren().addAll(backArrowView, headerLabel);
		
		outerBox = new VBox(topLayer, topicPaneContainer);
		
		getChildren().add(outerBox);
	}
	
	private void initBackArrow() {
		backArrowView.setOnMouseClicked(e -> {
			set.saveToFile();
			Main.mainScene().showSets();
		});
	}
	
	public void edit(ProblemSet set) {
		topicPaneContainer.getChildren().clear();
		this.set = set;
		for(Topic t : set.config().topics())
			topicPaneContainer.getChildren().add(TopicPane.of(t));
	}
	
}
