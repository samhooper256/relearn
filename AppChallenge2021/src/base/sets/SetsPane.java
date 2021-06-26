/**
 * 
 */
package base.sets;

import base.*;
import fxutils.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class SetsPane extends StackPane {
	
	private static final String TITLE = "Your Sets";
	private static final String
			SETS_PANE_CSS = "sets-pane",
			HEADER_CSS = "header",
			CREATE_BUTTON_CSS = "create-button",
			FLOW_CSS = "flow",
			SCROLL_CSS = "scroll",
			ROOT_LAYER_CSS = "root-layer",
			BACK_ARROW_BOX_CSS = "back-arrow-box";
	
	private static final double FLOW_PADDING = 20, CREATE_BUTTON_SCALE_FACTOR = 1.05;
	
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
	private final VBox rootLayer, header;
	private final Label title;
	private final BackArrow backArrow;
	private final HBox backArrowBox;
	private final Button createButton;
	
	private SetsPane() {
		title = new Label(TITLE);
		backArrow = new BackArrow();
		backArrowBox = new HBox(backArrow);
		createButton = new HoverExpandButton("+ Create", CREATE_BUTTON_SCALE_FACTOR);
		header = new VBox(title, createButton);
		flow = new FlowPane();
		scroll = new ScrollPane(flow);
		rootLayer = new VBox(backArrowBox, header, scroll);
		initVBox();
		ProblemSet.all().addAddListener(ps -> addCardForSafe(ps));
		getStyleClass().add(SETS_PANE_CSS);
		getChildren().add(rootLayer);
	}
	
	private void initVBox() {
		initBackArrowLayer();
		initHeader();
		initScroll();
		rootLayer.getStyleClass().add(ROOT_LAYER_CSS);
	}
	
	private void initBackArrowLayer() {
		initBackArrow();
		backArrowBox.getStyleClass().add(BACK_ARROW_BOX_CSS);
	}
	
	private void initHeader() {
		header.getStyleClass().add(HEADER_CSS);
		initCreateButton();
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
	private void initCreateButton() {
		createButton.getStyleClass().add(CREATE_BUTTON_CSS);
		createButton.setOnAction(e -> createButtonAction());
	}
	
	private void createButtonAction() {
		Main.scene().createFreshSet();
	}
	
	private void initFlow() {
		flow.getStyleClass().add(FLOW_CSS);
		VBox.setVgrow(scroll, Priority.ALWAYS);
		for(ProblemSet set : ProblemSet.all())
			addCardForSafe(set);
		flow.prefWrapLengthProperty().bind(rootLayer.widthProperty()
				.subtract(FLOW_PADDING * 2));
	}

	
	private void initScroll() {
		initFlow();
		scroll.getStyleClass().add(SCROLL_CSS);
	}
	
	private void addCardForSafe(ProblemSet set) {
		addCardSafe(SetCard.of(set));
	}
	
	/** Assumes that the given {@link SetCard} is not already displayed by the {@link SetsPane}.*/
	private void addCardSafe(SetCard card) {
		flow.getStyleClass().add("flow");
		flow.getChildren().addAll(card);
	}
	
}
