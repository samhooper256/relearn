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
public final class FinishPracticePopup extends FadePopup {
	
	private static final double PIE_HEIGHT = 200, PIE_WIDTH = 300;
	private static final FinishPracticePopup INSTANCE = new FinishPracticePopup();
	
	public static FinishPracticePopup get() {
		return INSTANCE;
	}
	
	private final VBox vBox;
	private final HBox buttonBar;
	private final Button backToSetsButton, replayButton;
	private final AccuracyPie pie;
	
	
	private FinishPracticePopup() {
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
		hideFrom(PracticePane.get());
	}
	
	private void initReplayButton() {
		replayButton.setOnAction(e -> replayAction());
	}
	
	private void replayAction() {
		PracticePane.get().replay();
	}
	
	private void initPie() {
		pie.setPrefSize(PIE_WIDTH, PIE_HEIGHT);
	}

	public void updateAccuracy(int correct, int incorrect) {
		pie.setAccuracy(correct, incorrect);
	}
	
}