/**
 * 
 */
package base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Sam Hooper
 *
 */
public class FontTester extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		VBox vBox = new VBox();
		ScrollPane scroll = new ScrollPane(vBox);
		root.getChildren().add(scroll);
		for(String name : Font.getFamilies()) {
			Label l = new Label(name);
			l.setFont(Font.font(name, 24));
			vBox.getChildren().add(l);
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
}
