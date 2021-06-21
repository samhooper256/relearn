/**
 * 
 */
package base;

import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * @author Sam Hooper
 *
 */
public class PracticePane extends StackPane {
	
	private static final String TITLE = "Practice";
	
	private final VBox vBox;
	private final TextField field;
	private final Label problemDisplay, title;
	private final HBox header;
	private final BackArrow backArrow;
	
	private Deck deck;
	private Problem currentProblem;
	private int deckIndex;
	
	public PracticePane() {
		field = new TextField();
		initializeField();
		problemDisplay = new Label();

		backArrow = new BackArrow();
		title = new Label(TITLE);
		header = new HBox(backArrow, title);
		initHeader();
		
		vBox = new VBox(header, problemDisplay, field);
		getChildren().add(vBox);
		deckIndex = -1;
		currentProblem = null;
	}
	
	private void initializeField() {
		field.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				e.consume();
				submitAction();
			}
		});
		
	}
	
	private void initHeader() {
		title.setFont(Font.font(24));
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.mainScene().showSets();
	}
	
	private String fieldText() {
		return field.getText();
	}
	
	private void submitAction() {
		if(currentProblem.isCorrect(fieldText().strip()))
			correctAnswerAction();
	}
	
	private void correctAnswerAction() {
		if(deckIndex < deck.size() - 1) {
			setupNext();
		}
		else {
			problemDisplay.setText("You done boi!"); //TODO
		}
	}
	
	public void start(Deck deck) {
		this.deck = deck;
		deckIndex = 0;
		setup(deck.get(deckIndex));
	}
	
	private void setup(Problem problem) {
		this.currentProblem = problem;
		clearField();
		problemDisplay.setText(problem.displayText());
		
	}

	private void clearField() {
		field.clear();
	}
	
	private void setupNext() {
		deckIndex++;
		setup(deck.get(deckIndex));
	}
	
}
