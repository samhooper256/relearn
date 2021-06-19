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
		topicPaneContainer.setBorder(Borders.of(Color.BLUE));
		
		getChildren().add(outerBox);
	}
	
	private void initBackArrow() {
		backArrowView.setOnMouseClicked(e -> {
			Main.mainScene().showSets();
		});
	}
	
	public void edit(ProblemSet set) {
		System.out.printf("EditorPane.edit(set=%s)%n", set);
		topicPaneContainer.getChildren().clear();
		this.set = set;
		for(Topic t : set.config().topics()) {
			System.out.printf("t=%s%n", t);
			topicPaneContainer.getChildren().add(TopicPane.of(t));
		}
	}
	
}
