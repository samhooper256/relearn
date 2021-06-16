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
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.root = (StackPane) root;
		titleLabel = new Label(Main.TITLE);
		setsButton = new Button("Sets");
		statsButton = new Button("Stats");
		settingsButton = new Button("Settings");
		setsPane = new SetsPane();
		initButtons();
		VBox vBox = new VBox(5, titleLabel, setsButton, statsButton, settingsButton);
		vBox.setAlignment(Pos.CENTER);
		root.getChildren().add(vBox);
	}
	
	private void initButtons() {
		setsButton.setOnAction(e -> setRoot(setsPane));
	}
	
	private void setRoot(Pane newRoot) {
		if(newRoot != setsPane)
			throw new IllegalArgumentException(String.format("Cannot change root to: %s", newRoot));
		super.setRoot(newRoot);
	}
	
}
