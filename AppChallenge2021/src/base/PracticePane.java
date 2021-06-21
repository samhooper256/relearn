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
	private static final double FIELD_WIDTH = 400;
	
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
			initReplayButton();
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
		
		private void initReplayButton() {
			replayButton.setOnAction(e -> replayAction());
		}
		
		private void replayAction() {
			startDeck(currentSet().createDeck());
			hideFinishPopup();
		}
		
	}
	
	private final VBox userArea;
	private final TextField field;
	private final Label problemDisplay, title;
	private final HBox header;
	private final BackArrow backArrow;
	private final FinishPane finishPane;
	
	private ProblemSet currentSet;
	private Deck currentDeck;
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
		
		userArea = new VBox(problemDisplay, field);
		initUserArea();
		
		getChildren().addAll(header, userArea);
		StackPane.setAlignment(userArea, Pos.CENTER);
		
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
	
	private void initUserArea() {
		field.setPrefWidth(FIELD_WIDTH);
		userArea.setFillWidth(false);
		userArea.setAlignment(Pos.CENTER);
	}
	
	private String fieldText() {
		return field.getText();
	}
	
	private void submitAction() {
		if(currentProblem.isCorrect(fieldText().strip()))
			correctAnswerAction();
	}
	
	private void correctAnswerAction() {
		if(deckIndex < currentDeck.size() - 1) {
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
	
	private void hideFinishPopup() {
		finishPane.fadeOutFrom(this);
	}
	
	public void start(ProblemSet set) {
		currentSet = set;
		startDeck(set.createDeck());
	}
	
	private void startDeck(Deck deck) {
		currentDeck = deck;
		deckIndex = 0;
		setup(currentDeck.get(deckIndex));
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
		setup(currentDeck.get(deckIndex));
	}
	
	public ProblemSet currentSet() {
		return currentSet;
	}
	
}
