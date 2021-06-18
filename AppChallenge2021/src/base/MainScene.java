/**
 * 
 */
package base;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class MainScene extends Scene {
	
	public static MainScene create(double width, double height) {
		return new MainScene(new StackPane(), width, height);
	}
	
	private final StackPane root;
	private final Button setsButton, statsButton, settingsButton;
	private final Label titleLabel;
	private final SetsPane setsPane;
	private final PracticePane practicePane;
	private final EditorPane builderPane;
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.root = (StackPane) root;
		titleLabel = new Label(Main.TITLE);
		setsButton = new Button("Sets");
		statsButton = new Button("Stats");
		settingsButton = new Button("Settings");
		setsPane = new SetsPane();
		practicePane = new PracticePane();
		builderPane = new EditorPane();
		initButtons();
		VBox vBox = new VBox(5, titleLabel, setsButton, statsButton, settingsButton);
		vBox.setAlignment(Pos.CENTER);
		root.getChildren().add(vBox);
	}
	
	private void initButtons() {
		setsButton.setOnAction(e -> showSets());
	}

	public void showSets() {
		setRoot(setsPane);
	}
	
	private void setRoot(Pane newRoot) {
		if(newRoot != setsPane && newRoot != practicePane && newRoot != builderPane)
			throw new IllegalArgumentException(String.format("Cannot change root to: %s", newRoot));
		super.setRoot(newRoot);
	}
	
	public void startPractice(Deck deck) {
		practicePane.start(deck);
		setRoot(practicePane);
	}
	
	public void edit(ProblemSet set) {
		setRoot(builderPane);
	}
	
}
