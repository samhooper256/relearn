package base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.*;
import javafx.stage.Stage;

public class WebTest extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		WebView view = new WebView();
		WebEngine engine = view.getEngine();
		engine.loadContent("<html>2<sup>3</sup>=8</html>");
		
		Scene scene = new Scene(view);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
