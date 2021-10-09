/**
 * 
 */
package base;

import base.editor.EditorPane;
import base.graphics.*;
import base.practice.PracticePane;
import base.sets.*;
import base.settings.SettingsPane;
import base.stats.*;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public final class MainScene extends Scene {
	
	private static final String
			MAIN_MENU_CSS = "main-menu",
			VBOX_CSS = "vbox";
	private static final Duration
			GROWTH_START = Duration.seconds(1),
			BUTTON_INTRO_START = Duration.millis(100),
			BUTTON_INTRO_DELAY = Duration.millis(150);
	private static final MainScene INSTANCE = new MainScene(new StackPane(), Main.MIN_WIDTH, Main.MIN_HEIGHT);
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private final StackPane mainMenu;
	private final TitleBox title;
	private final Growth growth;
	private final VBox vBox;
	private final Timeline introTimeline;
	
	private boolean animatedIn;
	
	private MainScene(StackPane root, double width, double height) {
		super(root, width, height);
		this.mainMenu = root;
		
		growth = new Growth();
		Pane growthPane = new Pane(growth);
		growth.layoutXProperty().bind(mainMenu.widthProperty());
		
		title = new TitleBox(Main.TITLE);
		vBox = new VBox(title, MainMenuButton.SETS, MainMenuButton.STATS, MainMenuButton.SETTINGS);
		mainMenu.getChildren().addAll(growthPane, vBox);
		initMainMenu();
		
		introTimeline = new Timeline();
		initIntroTimeline();
		
		animatedIn = false;
		
		this.setOnMouseClicked(eh -> {
			if(!animatedIn) {
				animatedIn = true;
				animateIn();
			}
		});
		
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
	}
	
	private void initMainMenu() {
		initVBox();
		mainMenu.getStyleClass().add(MAIN_MENU_CSS);
	}

	private void initVBox() {
		initButtons();
		vBox.setPickOnBounds(false); //cannot be set from CSS
		vBox.getStyleClass().add(VBOX_CSS);
	}
	
	private void initButtons() {
		MainMenuButton.SETS.setOnAction(this::showSets);
		MainMenuButton.STATS.setOnAction(this::showStats);
		MainMenuButton.SETTINGS.setOnAction(this::showSettings);
	}
	
	private void initIntroTimeline() {
		KeyFrame f1 = new KeyFrame(BUTTON_INTRO_START, e -> MainMenuButton.SETS.animateIn());
		KeyFrame f2 = new KeyFrame(BUTTON_INTRO_START.add(BUTTON_INTRO_DELAY), e -> MainMenuButton.STATS.animateIn());
		KeyFrame f3 = new KeyFrame(BUTTON_INTRO_START.add(BUTTON_INTRO_DELAY.multiply(2)),
				e -> MainMenuButton.SETTINGS.animateIn());
		KeyFrame growthFrame = new KeyFrame(GROWTH_START, e -> growth().animateIn());
		introTimeline.getKeyFrames().addAll(f1, f2, f3, growthFrame);
	}
	
	void animateIn() {
		title().animateIn();
		introTimeline.playFromStart();
	}
	
	public StackPane mainMenu() {
		return mainMenu;
	}
	
	public void showMainMenu() {
		setRoot(mainMenu());
	}
	
	public void showSets() {
		setRoot(SetsPane.get());
	}
	
	public void showStats() {
		StatsPane.get().updateAllStats();
		setRoot(StatsPane.get());
	}
	
	public void showSettings() {
		setRoot(SettingsPane.get());
	}
	
	public void startPractice(ProblemSet set) {
		PracticePane.get().start(set);
		setRoot(PracticePane.get());
	}
	
	public void edit(ProblemSet set) {
		Data.eraseStatsFor(set);
		EditorPane.get().edit(set);
		setRoot(EditorPane.get());
	}
	
	public void createFreshSet() {
		EditorPane.get().createFreshSet();
		setRoot(EditorPane.get());
	}
	
	public Growth growth() {
		return growth;
	}
	
	public TitleBox title() {
		return title;
	}
	
}
