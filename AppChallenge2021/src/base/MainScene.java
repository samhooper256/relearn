/**
 * 
 */
package base;

import base.sets.*;
import base.stats.StatsPane;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public final class MainScene extends Scene {
	
	private static final MainScene INSTANCE = new MainScene(new StackPane(), Main.MIN_WIDTH, Main.MIN_HEIGHT);
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private final StackPane mainMenu;
	private final Button setsButton, statsButton, settingsButton;
	private final Label titleLabel;
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.mainMenu = (StackPane) root;
		titleLabel = new Label(Main.TITLE);
		setsButton = new Button("Sets");
		statsButton = new Button("Stats");
		settingsButton = new Button("Settings");
		initButtons();
		
		VBox vBox = new VBox(5, titleLabel, setsButton, statsButton, settingsButton);
		vBox.setAlignment(Pos.CENTER);
		root.getChildren().add(vBox);
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
	}
	
	private void initButtons() {
		setsButton.setOnAction(e -> showSets());
		statsButton.setOnAction(e -> showStats());
	}
	
	public void showMainMenu() {
		setRoot(mainMenu);
	}
	
	public void showSets() {
		setRoot(SetsPane.get());
	}
	
	public void showStats() {
		setRoot(StatsPane.get());
	}
	
	public void startPractice(ProblemSet set) {
		PracticePane.get().start(set);
		setRoot(PracticePane.get());
	}
	
	public void edit(ProblemSet set) {
		EditorPane.get().edit(set);
		setRoot(EditorPane.get());
	}
	
	public void createFreshSet() {
		EditorPane.get().createFreshSet();
		setRoot(EditorPane.get());
	}
	
}
