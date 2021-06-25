package base;

import java.io.*;
import java.util.Optional;

import base.sets.ProblemSet;
import base.stats.Data;
import fxutils.Images;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import math.Evaluator;

public class Main extends Application {
	
	public static final String TITLE = "ReLearn";
	public static final double BACK_ARROW_SIZE = 40;
	public static final double MIN_WIDTH = 600, MIN_HEIGHT = 400;
	public static final File USER_FOLDER =
			new File(System.getProperty("user.dir"), String.format("%s Data", TITLE));
	public static final File SETS_FOLDER = new File(USER_FOLDER, "Sets");
	public static final File SET_UID_FILE = new File(SETS_FOLDER, "uid.dat");
	public static final File STATS_FOLDER = new File(USER_FOLDER, "Stats");
	public static final File STATS_FILE = new File(STATS_FOLDER, "stats.dat"); //file is created by topics.Data
	public static final String RESOURCES_PREFIX = "/resources/";
	
	private static final Image BACK_ARROW_IMAGE = Images.get("back.png", BACK_ARROW_SIZE, BACK_ARROW_SIZE, false, true);
	
	private static Stage primaryStage;
	private static Screen primaryScreen;
	
	public static void main(String[] args) {
		preLaunchInit();
		Data.debugPrint();
		Application.launch(args);
	}

	private static void preLaunchInit() {
		USER_FOLDER.mkdir();
		SETS_FOLDER.mkdir();
		STATS_FOLDER.mkdir();
		try {
			SET_UID_FILE.createNewFile();
		} catch (IOException e) {
			e.printStackTrace(); //TODO better error handling?
		}
		ProblemSet.loadSets();
		Data.load();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		Main.primaryScreen = Screen.getPrimary();
		
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(MainScene.get());
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		MainScene.get().growth().fadeIn();
		MainScene.get().title().fadeIn();
	}
	
	@Override
	public void stop() {
		ProblemSet.saveUID();
	    Data.debugPrint();
	    Data.save();
	}
	
	public static Stage stage() {
		return primaryStage;
	}
	
	public static Screen primaryScreen() {
		return primaryScreen;
	}
	
	public static double screenWidth() {
		return primaryScreen().getVisualBounds().getWidth();
	}
	
	public static double screenHeight() {
		return primaryScreen().getVisualBounds().getHeight();
	}
	
	public static MainScene scene() {
		return MainScene.get();
	}
	
	public static StackPane menu() {
		return MainScene.get().mainMenu();
	}
	
	public static Image backArrowImage() {
		return BACK_ARROW_IMAGE;
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