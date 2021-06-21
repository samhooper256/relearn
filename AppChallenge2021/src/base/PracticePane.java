/**
 * 
 */
package base;

import java.util.*;

import fxutils.*;
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
	private static final Border INCORRECT_ANSWER_BORDER = Borders.of(Color.RED);
	
	private class FinishPane extends FadePopup {
		
		private final VBox vBox;
		private final HBox buttonBar;
		private final Button backToSetsButton, replayButton;
		private final Label accuracyLabel;
		
		public FinishPane() {
			setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			
			backToSetsButton = new Button("Back");
			replayButton = new Button("Replay");
			buttonBar = new HBox(backToSetsButton, replayButton);
			
			accuracyLabel = new Label();
			
			vBox = new VBox(accuracyLabel, buttonBar);
			initVBox();
			
			setBackground(Backgrounds.of(Color.GOLD));
			getChildren().add(vBox);
		}
		
		private void initVBox() {
			initButtonBar();
			vBox.setAlignment(Pos.CENTER);
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
		
		public void updateAccuracyLabel() {
			accuracyLabel.setText(String.format("%d / %d", correctProblems.size(), currentDeck.size()));
		}
	}
	
	private final VBox userArea;
	private final TextField field;
	private final Label problemDisplay, title;
	private final HBox header, buttonBar;
	private final BackArrow backArrow;
	private final FinishPane finishPane;
	private final Button submitButton;
	private final List<Problem> correctProblems, incorrectProblems;
	
	private ProblemSet currentSet;
	private Deck currentDeck;
	private Problem currentProblem;
	private int deckIndex;
	/** {@code true} if an incorrect answer has been given to the {@link #currentProblem()}.*/
	private boolean incorrectAnswerGiven;
	
	public PracticePane() {
		backArrow = new BackArrow();
		title = new Label(TITLE);
		header = new HBox(backArrow, title);
		initHeader();
		
		problemDisplay = new Label();
		field = new TextField();
		buttonBar = new HBox();
		submitButton = new Button("Submit");
		userArea = new VBox(problemDisplay, field, buttonBar);
		initUserArea();
		
		getChildren().addAll(header, userArea);
		StackPane.setAlignment(userArea, Pos.CENTER);
		
		finishPane = new FinishPane();
		
		correctProblems = new ArrayList<>();
		incorrectProblems = new ArrayList<>();
		
		deckIndex = -1;
		currentProblem = null;
		incorrectAnswerGiven = false;
	}
	
	private void initHeader() {
		title.setFont(Font.font(24));
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.mainScene().showSets();
	}
	
	private void initUserArea() {
		initField();
		initButtonBar();
		userArea.setFillWidth(false);
		userArea.setAlignment(Pos.CENTER);
	}
	
	private void initField() {
		field.setPrefWidth(FIELD_WIDTH);
		field.setBorder(Border.EMPTY);
		field.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				e.consume();
				submitAction();
			}
		});
	}
	
	private void initButtonBar() {
		buttonBar.getChildren().addAll(submitButton);
		initSubmitButton();
	}
	
	private void initSubmitButton() {
		submitButton.setFocusTraversable(false);
		submitButton.setOnAction(e -> submitAction());
	}
	
	private String fieldText() {
		return field.getText();
	}
	
	private void submitAction() {
		String text = fieldText().strip();
		if(text.isBlank())
			return; //do nothing - the user clicked "Submit" when they hadn't entered anything.
		if(currentProblem.isCorrect(text))
			correctAnswerAction();
		else
			incorrectAnswerAction();
	}
	
	private void correctAnswerAction() {
		field.setBorder(Border.EMPTY);
		if(!incorrectAnswerGiven)
			correctProblems.add(currentProblem);
		if(deckIndex < currentDeck.size() - 1)
			setupNext();
		else
			deckFinished();
	}

	private void deckFinished() {
		cleanUpOnFinish();
		finishPane.updateAccuracyLabel();
		showFinishPopup();
	}
	
	private void incorrectAnswerAction() {
		if(!incorrectAnswerGiven)
			incorrectProblems.add(currentProblem);
		incorrectAnswerGiven = true;
		field.setBorder(INCORRECT_ANSWER_BORDER);
	}
	
	/** Cleans up the {@link PracticePane} before the {@link #showFinishPopup() finish popup} is shown.*/
	private void cleanUpOnFinish() {
		clearField();
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
		correctProblems.clear();
		incorrectProblems.clear();
		setup(currentDeck.get(deckIndex));
	}
	
	private void setup(Problem problem) {
		currentProblem = problem;
		incorrectAnswerGiven = false;
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
	
	public Problem currentProblem() {
		return currentProblem;
	}
	
}
