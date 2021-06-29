/**
 * 
 */
package base.sets;

import java.util.*;

import base.*;
import base.graphics.BackArrow;
import base.stats.Data;
import fxutils.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public final class PracticePane extends StackPane {
	
	private static final String TITLE = "Practice";
	private static final double FIELD_WIDTH = 400;
	private static final Border INCORRECT_ANSWER_BORDER = Borders.of(Color.RED);
	private static final PracticePane INSTANCE = new PracticePane();
	private static final String PROBLEM_DISPLAY_CSS = "problem-display";
	
	public static PracticePane get() {
		return INSTANCE;
	}
	
	private final VBox userArea;
	private final TextField field;
	private final Label problemDisplay, title;
	private final HBox header, buttonBar, backArrowBox;
	private final BackArrow backArrow;
	private final Button submitButton, showAnswerButton;
	private final List<Problem> correctProblems, incorrectProblems;
	
	private ProblemSet currentSet;
	private Deck currentDeck;
	private Problem currentProblem;
	private int deckIndex;
	/** {@code true} if an incorrect answer has been given to the {@link #currentProblem()}.*/
	private boolean incorrectAnswerGiven, answerShown;
	
	private PracticePane() {
		backArrow = new BackArrow();
		backArrowBox = new HBox(backArrow);
		
		title = new Label(TITLE);
		header = new HBox(title);
		initHeader();
		
		problemDisplay = new Label();
		field = new TextField();
		submitButton = new Button("Submit");
		showAnswerButton = new Button("Show Answer");
		buttonBar = new HBox(showAnswerButton, submitButton);
		userArea = new VBox(problemDisplay, field, buttonBar);
		initUserArea();
		
		getChildren().addAll(backArrowBox, header, userArea);
		StackPane.setAlignment(userArea, Pos.CENTER);
		
		correctProblems = new ArrayList<>();
		incorrectProblems = new ArrayList<>();
		
		deckIndex = -1;
		currentProblem = null;
		incorrectAnswerGiven = false;
		answerShown = false;
	}
	
	private void initHeader() {
		backArrow.setOnAction(this::backArrowAction);
		header.setAlignment(Pos.CENTER);
	}
	
	private void backArrowAction() {
		Main.scene().showSets();
	}
	
	private void initUserArea() {
		initProblemDisplay();
		initField();
		initButtonBar();
		userArea.setFillWidth(false);
		userArea.setAlignment(Pos.CENTER);
		userArea.setPickOnBounds(false);
	}
	
	private void initProblemDisplay() {
		problemDisplay.getStyleClass().add(PROBLEM_DISPLAY_CSS);
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
		initSubmitButton();
		initShowAnswerButton();
	}
	
	private void initSubmitButton() {
		submitButton.setFocusTraversable(false);
		submitButton.setOnAction(e -> submitAction());
	}
	
	private void initShowAnswerButton() {
		showAnswerButton.setFocusTraversable(false);
		showAnswerButton.setOnAction(e -> showAnswerAction());
	}
	
	private String fieldText() {
		return field.getText();
	}
	
	private void showAnswerAction() {
		answerShown = true;
		incorrectProblems.add(currentProblem());
		field.setText(currentProblem().sampleAnswer());
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
		if(!incorrectAnswerGiven && !answerShown)
			recordCorrect();
		if(deckIndex < currentDeck.size() - 1)
			setupNext();
		else
			deckFinished();
	}

	private void deckFinished() {
		cleanUpOnFinish();
		FinishPracticePopup.get().updateAccuracy(correctProblems.size(), incorrectProblems.size());
		showFinishPopup();
	}
	
	private void incorrectAnswerAction() {
		if(!incorrectAnswerGiven)
			recordIncorrect();
		incorrectAnswerGiven = true;
		field.setBorder(INCORRECT_ANSWER_BORDER);
	}
	
	private void recordCorrect() {
		correctProblems.add(currentProblem());
		Data.addCorrect(currentSet(), currentProblem());
	}
	
	private void recordIncorrect() {
		incorrectProblems.add(currentProblem());
		Data.addIncorrect(currentSet(), currentProblem());
	}
	
	/** Cleans up the {@link PracticePane} before the {@link #showFinishPopup() finish popup} is shown.*/
	private void cleanUpOnFinish() {
		clearField();
	}
	
	private void showFinishPopup() {
		FinishPracticePopup.get().fadeOnto(this);
	}
	
	private void hideFinishPopup() {
		FinishPracticePopup.get().fadeOutFrom(this);
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
	
	public void replay() {
		startDeck(currentSet().createDeck());
		hideFinishPopup();
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
