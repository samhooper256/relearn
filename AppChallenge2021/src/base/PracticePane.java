/**
 * 
 */
package base;

import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class PracticePane extends StackPane {
	
	private Deck deck;
	
	private final VBox vBox;
	private final TextField field;
	private final Label problemDisplay;
	private Problem currentProblem;
	
	private int deckIndex = -1;
	
	public PracticePane() {
		field = new TextField();
		initializeField();
		problemDisplay = new Label();
		vBox = new VBox(problemDisplay, field);
		getChildren().add(vBox);
		
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
	
	private String fieldText() {
		return field.getText();
	}
	
	private void submitAction() {
		if(currentProblem.isCorrect(fieldText().strip())) {
			correctAnswerAction();
		}
	}
	
	private void correctAnswerAction() {
		if(deckIndex < deck.size() - 1) {
			setupNext();
		}
		else {
			System.out.println("DONE");
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
	
	public Problem current() {
		return currentProblem;
	}
	
}
