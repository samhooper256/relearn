/**
 * 
 */
package base.sets;

import base.*;
import base.graphics.FadePopup;
import base.stats.AccuracyPie;
import fxutils.PolarizedPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public final class FinishPracticePopup extends FadePopup {
	
	private static final String
			FINISH_PRACTICE_POPUP_CSS = "finish-practice-popup",
			TITLE_CSS = "title",
			PERCENTAGE_CSS = "percentage",
			BUTTON_BAR_CSS = "button-bar",
			VBOX_CSS = "vbox",
			HEADER_CSS = "header",
			EXIT_BUTTON_CSS = "exit-button",
			REPLAY_BUTTON_CSS = "replay-button";
	private static final double PIE_HEIGHT = 200, PIE_WIDTH = 300;
	private static final FinishPracticePopup INSTANCE = new FinishPracticePopup();
	
	public static FinishPracticePopup get() {
		return INSTANCE;
	}
	
	private final PolarizedPane header;
	private final VBox vBox;
	private final Label title, percentage;
	private final HBox buttonBar;
	private final Button exitButton, replayButton;
	private final AccuracyPie pie;
	
	private FinishPracticePopup() {
		super(PracticePane.get());
		
		title = new Label();
		percentage = new Label();
		header = new PolarizedPane(title, percentage);
		
		exitButton = new Button("Back");
		replayButton = new Button("Replay");
		buttonBar = new HBox(exitButton, replayButton);
		
		pie = new AccuracyPie(0, 0);
		
		vBox = new VBox(header, pie, buttonBar);
		initVBox();
		
		setGlassCloseable(false);
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getChildren().add(vBox);
		getStyleClass().add(FINISH_PRACTICE_POPUP_CSS);
	}
	
	private void initVBox() {
		initHeader();
		initButtonBar();
		initPie();
		vBox.getStyleClass().add(VBOX_CSS);
	}
	
	private void initHeader() {
		title.getStyleClass().add(TITLE_CSS);
		percentage.getStyleClass().add(PERCENTAGE_CSS);
		header.getStyleClass().add(HEADER_CSS);
	}

	private void initButtonBar() {
		initExitButton();
		initReplayButton();
		buttonBar.getStyleClass().add(BUTTON_BAR_CSS);
	}
	
	private void initExitButton() {
		exitButton.setOnAction(e -> exitButtonAction());
		exitButton.getStyleClass().add(EXIT_BUTTON_CSS);
	}
	
	private void exitButtonAction() {
		Main.scene().showSets();
		hidePopup();
	}
	
	private void initReplayButton() {
		replayButton.setOnAction(e -> replayAction());
		replayButton.getStyleClass().add(REPLAY_BUTTON_CSS);
	}
	
	private void replayAction() {
		PracticePane.get().replay();
	}
	
	private void initPie() {
		pie.setPrefSize(PIE_WIDTH, PIE_HEIGHT);
	}

	void updateAccuracy(int correct, int incorrect) {
		pie.setAccuracy(correct, incorrect);
		percentage.setText(String.format("%.0f%%", 100d * correct / (correct + incorrect)));
	}
	
	void setTitle(String title) {
		this.title.setText(title);
	}
}