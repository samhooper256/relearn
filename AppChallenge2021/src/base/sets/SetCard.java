/**
 * 
 */
package base.sets;

import java.util.IdentityHashMap;

import base.Main;
import fxutils.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class SetCard extends StackPane {
	
	private static final String
			SET_CARD_CSS = "set-card",
			TITLE_CSS = "title";
	private static final double PREF_WIDTH = 300;
	private static final double PREF_HEIGHT = 150;
	private static final double PENCIL_SIZE = 30;
	private static final Image PENCIL_IMAGE = Images.get("pencil.png", PENCIL_SIZE, PENCIL_SIZE, false, true);
	private static final IdentityHashMap<ProblemSet, SetCard> CACHE = new IdentityHashMap<>();
	
	public static synchronized SetCard of(ProblemSet set) {
		SetCard cached = CACHE.get(set);
		if(cached != null)
			return cached;
		SetCard obj = new SetCard(set);
		CACHE.put(set, obj);
		return obj;
	}
	
	/** @throws IllegalArgumentException if the given {@link ProblemSet} does not already have a {@link SetCard}.*/
	public static SetCard ofExisting(ProblemSet set) {
		SetCard card = CACHE.get(set);
		if(card == null)
			throw new IllegalArgumentException(String.format("The given set did not have a SetCard: %s", set));
		return card;
	}

	public static synchronized void remove(SetCard card) {
		assert CACHE.get(card.set()) == card;
		SetsPane.get().removeCard(card);
		CACHE.remove(card.set());
	}
	
	private final ProblemSet set;
	private final VBox vBox;
	private final Button practiceButton;
	private final ImageView pencilView;
	private final Label title;
	
	private SetCard(ProblemSet set) {
		this.set = set;
		this.setBorder(Borders.of(Color.GREEN));
		this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		
		title = new Label();
		
		practiceButton = new Button("Practice");
		
		vBox = new VBox(title, practiceButton);
		initVBox();
		
		pencilView = new ImageView(PENCIL_IMAGE);
		initPencil();
		
		getStyleClass().add(SET_CARD_CSS);
		getChildren().addAll(vBox, pencilView);
	}

	private void initVBox() {
		initTitle();
		initPracticeButton();
	}
	
	private void initTitle() {
		title.textProperty().bind(set.nameProperty());
		title.getStyleClass().add(TITLE_CSS);
	}
	
	private void initPencil() {
		pencilView.setOnMouseClicked(e -> requestEdit());
		StackPane.setAlignment(pencilView, Pos.TOP_RIGHT);
	}
	
	private void requestEdit() {
		SetsPane.get().requestEdit(set());
	}
	
	private void initPracticeButton() {
		practiceButton.setOnAction(e -> Main.scene().startPractice(set()));
	}
	
	public ProblemSet set() {
		return set;
	}
	
	void updateName() {
		title.setText(set.name());
	}
	
}
