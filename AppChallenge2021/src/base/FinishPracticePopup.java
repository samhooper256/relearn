/**
 * 
 */
package base;

import fxutils.Backgrounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class FinishPracticePopup extends FadePopup {
	
	private static final double PIE_HEIGHT = 200, PIE_WIDTH = 300;
	
	private final VBox vBox;
	private final HBox buttonBar;
	private final Button backToSetsButton, replayButton;
	private final AccuracyPie pie;
	private final PracticePane practicePane;
	
	public FinishPracticePopup(final PracticePane practicePane) {
		this.practicePane = practicePane;
		
		backToSetsButton = new Button("Back");
		replayButton = new Button("Replay");
		buttonBar = new HBox(backToSetsButton, replayButton);
		
		pie = new AccuracyPie(0, 0);
		
		vBox = new VBox(pie, buttonBar);
		initVBox();
		
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setBackground(Backgrounds.of(Color.GOLD));
		getChildren().add(vBox);
	}
	
	private void initVBox() {
		initButtonBar();
		initPie();
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
		hideFrom(practicePane);
	}
	
	private void initReplayButton() {
		replayButton.setOnAction(e -> replayAction());
	}
	
	private void replayAction() {
		practicePane.replay();
	}
	
	private void initPie() {
		pie.setPrefSize(PIE_WIDTH, PIE_HEIGHT);
	}

	public void updateAccuracy(int correct, int incorrect) {
		pie.setAccuracy(correct, incorrect);
	}
	
}