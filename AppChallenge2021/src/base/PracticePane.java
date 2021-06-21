/**
 * 
 */
package base;

import fxutils.Backgrounds;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Sam Hooper
 *
 */
public class PracticePane extends StackPane {
	
	private static final String TITLE = "Practice";
	
	private class FinishPane extends FadePopup {
		
		private final HBox buttonBar;
		private final Button backToSetsButton, replayButton;
		
		public FinishPane() {
			setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			backToSetsButton = new Button("Back");
			replayButton = new Button("Replay");
			buttonBar = new HBox(backToSetsButton, replayButton);
			initButtonBar();
			setBackground(Backgrounds.of(Color.GOLD));
			getChildren().add(buttonBar);
		}
		
		private void initButtonBar() {
			initBackToSetsButton();
			buttonBar.setSpacing(20);
			buttonBar.setAlignment(Pos.CENTER);
		}
		
		private void initBackToSetsButton() {
			backToSetsButton.setOnAction(e -> backToSetsAction());
		}
		
		private void backToSetsAction() {
			Main.mainScene().showSets();
			hideFrom(PracticePane.this);
		}
		
	}
	
	private final VBox vBox;
	private final TextField field;
	private final Label problemDisplay, title;
	private final HBox header;
	private final BackArrow backArrow;
	private final FinishPane finishPane;
	
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
		
		finishPane = new FinishPane();
		
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
			cleanUpOnFinish();
			showFinishPopup();
		}
	}
	
	/** Cleans up the {@link PracticePane} before the {@link #showFinishPopup() finish popup} is shown.*/
	private void cleanUpOnFinish() {
		clearField();
		setProblemText("Done!");
	}
	
	private void showFinishPopup() {
		finishPane.fadeOnto(this);
	}
	
	public void start(Deck deck) {
		this.deck = deck;
		deckIndex = 0;
		setup(deck.get(deckIndex));
	}
	
	private void setup(Problem problem) {
		this.currentProblem = problem;
		clearField();
		setProblemText(problem.displayText());
		
	}
	
	private void setProblemText(String text) {
		problemDisplay.setText(text);
	}
	
	private void clearField() {
		field.clear();
	}
	
	private void setupNext() {
		deckIndex++;
		setup(deck.get(deckIndex));
	}
	
}
