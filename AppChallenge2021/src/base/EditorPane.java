/**
 * 
 */
package base;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.Addition;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane {
	
	private static final String HEADER = "Set Editor";
	
	private final VBox outerBox;
	private final HBox topLayer;
	private final ImageView backArrowView;
	
	public EditorPane() {
		topLayer = new HBox();
		backArrowView = new ImageView(Main.backArrowImage());
		initBackArrow();
		Label headerLabel = new Label(HEADER);
		headerLabel.setFont(Font.font(24));
		topLayer.getChildren().addAll(backArrowView, headerLabel);
		
		outerBox = new VBox(topLayer, TopicPane.of(new Addition()));
		
		getChildren().add(outerBox);
	}
	
	private void initBackArrow() {
		backArrowView.setOnMouseClicked(e -> {
			Main.mainScene().showSets();
		});
	}
	
}
