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
	private final ProgressBar progressBar;
	private final VBox progressBarLayer;
	private final StatsLayer statsLayer;
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
		
		statsLayer = new StatsLayer();
		initStatsLayer();
		
		progressBar = new ProgressBar();
		progressBarLayer = new VBox(progressBar);
		initProgressBarLayer();
		
		getChildren().addAll(userArea, header, progressBarLayer, statsLayer);
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
	
	private void initStatsLayer() {
		StackPane.setAlignment(statsLayer, Pos.BOTTOM_LEFT);
	}
	
	private void initProgressBarLayer() {
		StackPane.setAlignment(progressBarLayer, Pos.TOP_CENTER);
		progressBarLayer.getStyleClass().add(PROGRESS_BAR_LAYER_CSS);
		progressBarLayer.setMouseTransparent(true); //cannot be set from CSS.
	}
	
	private void deckFinished() {
		cleanUpOnFinish();
		FinishPracticePopup.get().updateAccuracy(correctProblems.size(), incorrectProblems.size());
		FinishPracticePopup.get().updateLongestStreak(statsLayer.longestStreak());
		FinishPracticePopup.get().setTitle(set().name());
		set().addPractice();
		showFinishPopup();
	}
	
	void notifyCorrect(Problem p) {
		recordCorrect(p);
		statsLayer.notifyCorrect();
		notifyFinished();
	}

	private void recordCorrect(Problem p) {
		correctProblems.add(p);
		Data.addCorrect(set(), p);
	}
	
	void notifyIncorrect(Problem p) {
		recordIncorrect(p);
		statsLayer.notifyIncorrect();
		notifyFinished();
	}

	private void recordIncorrect(Problem p) {
		incorrectProblems.add(p);
		Data.addIncorrect(set(), p);
	}
	
	/** Called whenever a problem is finished, whether it was correct or incorrect. Should only be called by
	 * {@link #notifyCorrect(Problem)} and {@link #notifyIncorrect(Problem)}.*/
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
		statsLayer.resetAll();
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
	
	/** Called by {@link UserArea} whenever a {@link Problem} is completed - that is, it is answered correctly.
	 * This method assumes that the problem has already been recorded (see {@link #notifyCorrect(Problem)} and
	 * {@link #notifyIncorrect(Problem)}).
	 * @param time the duration (in milliseconds) it took the user to enter a correct answer
	 * */
	void problemCompleted(double time) {
		statsLayer.setMostRecentTime(time);
		if(hasMoreProblemsInDeck())
			setupNext();
		else
			deckFinished();
	}
	
}
