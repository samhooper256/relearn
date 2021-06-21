/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Sam Hooper
 *
 */
public class SetsPane extends StackPane {
	
	private static final String TITLE = "Your Sets";
	
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
	private final BackArrow backArrow;
	private final HBox header;
	private final Button createButton;
	
	private SetsPane() {
		headerLabel = new Label(TITLE);
		backArrow = new BackArrow();
		createButton = new Button("+ Create");
		header = new HBox(backArrow, headerLabel, createButton);
		flow = new FlowPane();
		scroll = new ScrollPane(flow);
		vBox = new VBox(header, scroll);
		initVBox();
		ProblemSet.addOnRegisterAction(ps -> addCardForSafe(ps));
		getChildren().add(vBox);
	}
	
	private void initVBox() {
		initHeader();
		initScroll();
		vBox.setSpacing(10);
	}
	
	private void initHeader() {
		headerLabel.setFont(Font.font(24));
		header.setSpacing(20);
		header.setAlignment(Pos.CENTER_LEFT);
		initBackArrow();
		initCreateButton();
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.mainScene().showMainMenu();
	}
	
	private void initCreateButton() {
		createButton.setOnAction(e -> createButtonAction());
	}
	
	private void createButtonAction() {
		Main.mainScene().createFreshSet();
	}
	
	private void initFlow() {
		flow.setBorder(Borders.of(Color.RED));
		flow.setHgap(10);
		flow.setVgap(10);
		Insets padding = new Insets(20);
		flow.setPadding(padding);
		VBox.setVgrow(scroll, Priority.ALWAYS);
		for(ProblemSet set : ProblemSet.allSets())
			addCardForSafe(set);
		flow.prefWrapLengthProperty().bind(vBox.widthProperty().subtract(padding.getLeft() + padding.getRight()));
	}

	
	private void initScroll() {
		initFlow();
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	}
	
	private void addCardForSafe(ProblemSet set) {
		addCardSafe(SetCard.of(set));
	}
	
	/** Assumes that the given {@link SetCard} is not already displayed by the {@link SetsPane}.*/
	private void addCardSafe(SetCard card) {
		flow.getChildren().addAll(card);
	}
	
}
