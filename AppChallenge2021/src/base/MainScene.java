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
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.root = (StackPane) root;
		titleLabel = new Label(Main.TITLE);
		setsButton = new Button("Sets");
		statsButton = new Button("Stats");
		settingsButton = new Button("Settings");
		VBox vBox = new VBox(5, titleLabel, setsButton, statsButton, settingsButton);
		vBox.setAlignment(Pos.CENTER);
		root.getChildren().add(vBox);
	}
	
}
