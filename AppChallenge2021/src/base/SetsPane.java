/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
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
	
	private static SetsPane INSTANCE = null;
	
	public static SetsPane get() {
		//double-checked locking pattern
		if(INSTANCE == null){
	        synchronized (EditorPane.class) {
	            if(INSTANCE == null){
	            	INSTANCE = new SetsPane();
	            }
	        }
	    }
	    return INSTANCE;
	}
	
	private final ScrollPane scroll;
	private final FlowPane flow;
	private final VBox vBox;
	private final Label headerLabel;
	private final ImageView backArrowView;
	private final HBox header;
	
	private SetsPane() {
		headerLabel = new Label(HEADER);
		backArrowView = new ImageView(Main.backArrowImage());
		header = new HBox(backArrowView, headerLabel);
		flow = new FlowPane();
		scroll = new ScrollPane(flow);
		vBox = new VBox(header, scroll);
		initVBox();
		getChildren().add(vBox);
	}

	private void initHeader() {
		headerLabel.setFont(Font.font(24));
		initBackArrow();
	}
	
	private void initBackArrow() {
		backArrowView.setOnMouseClicked(e -> backArrowAction());
	}
	
	private void backArrowAction() {
		Main.mainScene().showMainMenu();
	}

	private void initVBox() {
		initHeader();
		initScroll();
		vBox.setSpacing(10);
	}
	
	private void initFlow() {
		flow.setBorder(Borders.of(Color.RED));
		flow.setHgap(10);
		flow.setVgap(10);
		Insets padding = new Insets(20);
		flow.setPadding(padding);
		VBox.setVgrow(scroll, Priority.ALWAYS);
		for(ProblemSet set : ProblemSet.allSets())
			flow.getChildren().addAll(SetCard.of(set));
		flow.prefWrapLengthProperty().bind(vBox.widthProperty().subtract(padding.getLeft() + padding.getRight()));
	}
	
	private void initScroll() {
		initFlow();
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	}
	
}
