/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import topics.Addition;

/**
 * @author Sam Hooper
 *
 */
public class SetsPane extends StackPane {
	
	private static final String HEADER = "Your Sets";
	
	private final ScrollPane scroll;
	private final FlowPane flow;
	private final VBox vBox;
	
	public SetsPane() {
		vBox = new VBox();
		getChildren().add(vBox);
		Label headerLabel = new Label(HEADER);
		headerLabel.setFont(Font.font(24));
		flow = new FlowPane();
		scroll = new ScrollPane(flow);
		initFlow();
		initVBox(headerLabel);
		initScroll();
	}

	private void initVBox(Label headerLabel) {
		vBox.getChildren().addAll(headerLabel, scroll);
		vBox.setSpacing(10);
	}
	
	private void initFlow() {
		flow.setBorder(Borders.of(Color.RED));
		flow.setHgap(10);
		flow.setVgap(10);
		Insets padding = new Insets(20);
		flow.setPadding(padding);
		VBox.setVgrow(scroll, Priority.ALWAYS);
		ProblemSet temp = new ProblemSet("My Fun Set");
		temp.config().putTopic(new Addition(), 10);
		flow.getChildren().addAll(new SetCard(temp), new SetCard(temp), new SetCard(temp), new SetCard(temp),
				new SetCard(temp), new SetCard(temp), new SetCard(temp), new SetCard(temp));
		flow.prefWrapLengthProperty().bind(vBox.widthProperty().subtract(padding.getLeft() + padding.getRight()));
	}
	
	private void initScroll() {
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	}
	
}
