package base;

import java.io.*;
import java.util.Optional;

import base.practice.*;
import base.sets.*;
import base.stats.Data;
import fxutils.Images;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import utils.Assertions;

public class Main extends Application {
	
	public static final String TITLE = "ReLearn";
	public static final double
			MAIN_MENU_BUTTON_WIDTH = 500, MAIN_MENU_BUTTON_HEIGHT = 100,
			MAIN_MENU_ICON_SIZE = 80,
			MIN_WIDTH = 600, MIN_HEIGHT = 400,
			BACK_ARROW_SIZE = 40,
			TRASH_CAN_SIZE = 20,
			BUTTON_ICON_SIZE = 16;
	public static final File 
			USER_FOLDER = new File(System.getProperty("user.dir"), String.format("%s Data", TITLE)),
			SETS_FOLDER = new File(USER_FOLDER, "Sets"),
			SETS_FILE = new File(SETS_FOLDER, "sets.dat"),
			STATS_FOLDER = new File(USER_FOLDER, "Stats"),
			STATS_FILE = new File(STATS_FOLDER, "stats.dat"); //file is created by topics.Data
	public static final String RESOURCES_PREFIX = "/resources/";
	
	public static final Image 
			BACK_ARROW_IMAGE = Images.get("back.png", BACK_ARROW_SIZE, BACK_ARROW_SIZE, false, true),
			TRASH_CAN_IMAGE = Images.get("trash.png", TRASH_CAN_SIZE, TRASH_CAN_SIZE, false, true),
			BANNER = Images.get("banner.png", MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT, false, true),
			SETTINGS_ICON = 
					Images.get("settingsicon.png", MAIN_MENU_ICON_SIZE, MAIN_MENU_ICON_SIZE, false, true),
			GREEN_PLUS = Images.get("plus_green.png", BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, false, true),
			BLUE_CHECK = Images.get("check_blue.png", BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, false, true),
			BLUE_X = Images.get("x_blue.png", BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, false, true),
			INFO = Images.get("info.png", BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, false, true),
			X_BOX = Images.get("x_box.png", AccuracyBar.ICON_SIZE, AccuracyBar.ICON_SIZE, false, true),
			CHECK_BOX = Images.get("check_box.png", AccuracyBar.ICON_SIZE, AccuracyBar.ICON_SIZE, false, true),
			TARGET = Images.get("target.png", InfoBar.ICON_SIZE, InfoBar.ICON_SIZE, false, true),
			FIRE = Images.get("fire.png", InfoBar.ICON_SIZE, InfoBar.ICON_SIZE, false, true);
	
	private static Stage primaryStage;
	private static Screen primaryScreen;
	
	public static void main(String[] args) {
		Assertions.require();
		preLaunchInit();
		Application.launch(args);
	}

	private static void preLaunchInit() {
		USER_FOLDER.mkdir();
		SETS_FOLDER.mkdir();
		STATS_FOLDER.mkdir();
		try {
			SETS_FILE.createNewFile();
		} catch (IOException e) {
			e.printStackTrace(); //TODO better error handling?
		}
		ProblemSet.load();
		Data.load();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		Main.primaryScreen = Screen.getPrimary();
		
		PracticePane.get(); //The PracticePane takes a long time to load because it has a WebView, so we do it on
		//application startup.
		
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(MainScene.get());
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		MainScene.get().growth().fadeIn();
		MainScene.get().title().fadeIn();
	}
	
	@Override
	public void stop() {
		ProblemSet.save();
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