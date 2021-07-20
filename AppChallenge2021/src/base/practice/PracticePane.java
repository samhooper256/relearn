/**
 * 
 */
package base.practice;

import java.util.*;

import base.*;
import base.graphics.BackArrow;
import base.problems.Problem;
import base.sets.*;
import base.stats.Data;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public final class PracticePane extends StackPane {
	
	private static final String TITLE = "Practice";
	private static final PracticePane INSTANCE = new PracticePane();
	private static final String
			PRACTICE_PANE_CSS = "practice-pane",
			PROGRESS_BAR_LAYER_CSS = "progress-bar-layer",
			HEADER_CSS = "header",
			TITLE_CSS = "title";
	
	public static PracticePane get() {
		return INSTANCE;
	}
	
	private final UserArea userArea;
	private final Label title;
	private final HBox header;
	private final BackArrow backArrow;
	private final StreakBar streakBar;
	private final ProgressBar progressBar;
	private final VBox progressBarLayer;
	private final List<Problem> correctProblems, incorrectProblems;
	private ProblemSet set;
	private Deck deck;
	private int deckIndex;
	
	private PracticePane() {
		backArrow = new BackArrow();
		
		title = new Label(TITLE);
		header = new HBox(backArrow, title);
		initHeader();
		
		userArea = new UserArea();
		
		streakBar = new StreakBar();
		
		progressBar = new ProgressBar();
		progressBarLayer = new VBox(progressBar);
		initProgressBarLayer();
		
		StackPane.setAlignment(streakBar, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(progressBarLayer, Pos.TOP_CENTER);
		getChildren().addAll(userArea, header, progressBarLayer, streakBar);
		getStyleClass().add(PRACTICE_PANE_CSS);
		
		correctProblems = new ArrayList<>();
		incorrectProblems = new ArrayList<>();
		
		deckIndex = -1;
	}
	
	private void initHeader() {
		backArrow.setOnAction(this::backArrowAction);
		header.getStyleClass().add(HEADER_CSS);
		header.setPickOnBounds(false); //cannot be set from CSS.
		title.getStyleClass().add(TITLE_CSS);
	}
	
	private void backArrowAction() {
		Main.scene().showSets();
	}
	
	private void initProgressBarLayer() {
		progressBarLayer.getStyleClass().add(PROGRESS_BAR_LAYER_CSS);
		progressBarLayer.setMouseTransparent(true); //cannot be set from CSS.
	}
	
	private void deckFinished() {
		cleanUpOnFinish();
		FinishPracticePopup.get().updateAccuracy(correctProblems.size(), incorrectProblems.size());
		FinishPracticePopup.get().updateLongestStreak(streakBar.count().longest());
		FinishPracticePopup.get().setTitle(set().name());
		set().addPractice();
		showFinishPopup();
	}
	
	void notifyCorrect(Problem p) {
		recordCorrect(p);
		streakBar.notifyCorrect();
		notifyFinished();
	}

	private void recordCorrect(Problem p) {
		correctProblems.add(p);
		Data.addCorrect(set(), p);
	}
	
	void notifyIncorrect(Problem p) {
		recordIncorrect(p);
		streakBar.notifyIncorrect();
		notifyFinished();
	}

	private void recordIncorrect(Problem p) {
		incorrectProblems.add(p);
		Data.addIncorrect(set(), p);
	}
	
	/** Called whenever a problem is finished, whether it was correct or incorrect.*/
	private void notifyFinished() {
		progressBar.addFinished();
	}
	
	/** Cleans up the {@link PracticePane} before the {@link #showFinishPopup() finish popup} is shown.*/
	private void cleanUpOnFinish() {
		userArea.cleanUpOnFinish();
	}
	
	private void showFinishPopup() {
		FinishPracticePopup.get().fadeIn();
	}
	
	private void hideFinishPopup() {
		FinishPracticePopup.get().fadeOut();
	}
	
	public void start(ProblemSet set) {
		this.set = set;
		startDeck(set.createDeck());
	}
	
	private void startDeck(Deck deck) {
		this.deck = deck;
		deckIndex = 0;
		correctProblems.clear();
		incorrectProblems.clear();
		userArea.focusOnField();
		streakBar.resetAll();
		progressBar.resetWithTotal(deck.size());
		setup(deck.get(deckIndex));
	}
	
	private void setup(Problem problem) {
		userArea.setup(problem);
	}
	
	void replay() {
		startDeck(set().createDeck());
		hideFinishPopup();
	}
	
	void setupNext() {
		deckIndex++;
		setup(deck.get(deckIndex));
	}
	
	private ProblemSet set() {
		return set;
	}
	
	boolean hasMoreProblemsInDeck() {
		return deckIndex < deck.size() - 1;
	}
	
	/** Called by {@link UserArea} whenever a {@link Problem }is completed - that is, it is answered correctly.
	 * This method assumes that the problem has already been recorded (see {@link #notifyCorrect(Problem)} and
	 * {@link #notifyIncorrect(Problem)}).*/
	void problemCompleted() {
		if(hasMoreProblemsInDeck())
			setupNext();
		else
			deckFinished();
	}
	
}
