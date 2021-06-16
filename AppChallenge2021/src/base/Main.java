package base;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.*;

public class Main extends Application {
	
	public static final String TITLE = "ReLearn";
	
	private static Stage primaryStage;
	private static Screen primaryScreen;
	private static MainScene mainScene;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		Main.primaryScreen = Screen.getPrimary();
		
		Rectangle2D primaryBounds = primaryScreen.getBounds();
		
		mainScene = MainScene.create(primaryBounds.getWidth() / 2, primaryBounds.getHeight() / 2);
		
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.show();
	}
	
	public static Stage stage() {
		return primaryStage;
	}
	
	public static Screen primaryScreen() {
		return primaryScreen;
	}
	
	public static Scene currentScene() {
		return stage().getScene();
	}
	
	public static MainScene mainScene() {
		return mainScene;
	}
	
}