/**
 * 
 */
package base;

import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class PracticePane extends StackPane {
	
	private Deck currentDeck;
	
	private final VBox vBox;
	private final TextField field;
	private final Label problemDisplay;
	
	private int deckIndex = -1;
	
	public PracticePane() {
		field = new TextField();
		problemDisplay = new Label();
		vBox = new VBox(problemDisplay, field);
		getChildren().add(vBox);
	}
	
	public void start(Deck deck) {
		this.currentDeck = deck;
		deckIndex = 0;
		setup(deck.at(deckIndex));
	}
	
	private void setup(Problem problem) {
		problemDisplay.setText(problem.displayText());
		
	}
	
}
