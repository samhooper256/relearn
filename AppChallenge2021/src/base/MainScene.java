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
	
	private final StackPane mainMenu;
	private final Button setsButton, statsButton, settingsButton;
	private final Label titleLabel;
	private final PracticePane practicePane;
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.mainMenu = (StackPane) root;
		titleLabel = new Label(Main.TITLE);
		setsButton = new Button("Sets");
		statsButton = new Button("Stats");
		settingsButton = new Button("Settings");
		practicePane = new PracticePane();
		initButtons();
		VBox vBox = new VBox(5, titleLabel, setsButton, statsButton, settingsButton);
		vBox.setAlignment(Pos.CENTER);
		root.getChildren().add(vBox);
	}
	
	private void initButtons() {
		setsButton.setOnAction(e -> showSets());
	}
	
	private void setRoot(Pane newRoot) {
		if(newRoot != mainMenu && newRoot != SetsPane.get() && newRoot != practicePane && newRoot != EditorPane.get())
			throw new IllegalArgumentException(String.format("Cannot change root to: %s", newRoot));
		super.setRoot(newRoot);
	}
	
	public void showMainMenu() {
		setRoot(mainMenu);
	}
	
	public void showSets() {
		setRoot(SetsPane.get());
	}
	
	public void startPractice(Deck deck) {
		practicePane.start(deck);
		setRoot(practicePane);
	}
	
	public void edit(ProblemSet set) {
		EditorPane.get().edit(set);
		setRoot(EditorPane.get());
	}
	
}
