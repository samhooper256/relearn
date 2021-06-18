package base;

import java.io.InputStream;
import java.util.Optional;

import fxutils.Images;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.*;

public class Main extends Application {
	
	public static final String TITLE = "ReLearn";
	public static final double BACK_ARROW_SIZE = 40;
	
	private static final String RESOURCES_PREFIX = "/resources/";
	
	private static Image backArrowImage;
	private static Stage primaryStage;
	private static Screen primaryScreen;
	private static MainScene mainScene;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initImages();
		Main.primaryStage = primaryStage;
		Main.primaryScreen = Screen.getPrimary();
		
		Rectangle2D primaryBounds = primaryScreen.getBounds();
		
		mainScene = MainScene.create(primaryBounds.getWidth() / 2, primaryBounds.getHeight() / 2);
		
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.show();
	}
	
	private static void initImages() {
		backArrowImage = Images.get("back.png", BACK_ARROW_SIZE, BACK_ARROW_SIZE, false, true);
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
	
	public static Image backArrowImage() {
		return backArrowImage;
	}
	
	/**
	 * Produces an {@link Optional} of the {@link InputStream} for a resource in the "resources" folder.
	 * If the resource could not be located, the returned {@code Optional} will be empty. Otherwise, it
	 * will contain the {@code InputStream}.
	 * @param filename the name of the resource file, including its file extension. Must be in the "resources" folder.
	 * @return an {@link Optional} possibly containing the {@link InputStream}.
	 */
	public static Optional<InputStream> getOptionalResourceStream(String filename) {
		return Optional.ofNullable(Main.class.getResourceAsStream(RESOURCES_PREFIX + filename));
	}
	
	/**
	 * Produces the {@link InputStream} for a resource in the "resources" folder.
	 * @param filename the name of the file, including its file extension. Must be in the "resources" folder.
	 * @return the {@link InputStream} for the resource indicated by the given filename.
	 * @throws IllegalArgumentException if the file does not exist.
	 */
	public static InputStream getResourceStream(String filename) {
		Optional<InputStream> stream = getOptionalResourceStream(filename);
		if(stream.isEmpty())
			throw new IllegalArgumentException("The resource at " + RESOURCES_PREFIX + filename + " does not exist");
		return stream.get();
	}
	
}