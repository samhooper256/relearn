/**
 * 
 */
package base.graphics;

import java.util.*;

import base.Main;
import fxutils.*;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class MainMenuButton extends StackPane {
	
	protected static final Duration
			INTRO_DURATION = Duration.millis(500),
			SLIDE_DURATION = Duration.millis(200);
	
	private static final double BUTTON_EXTENSION = 100, INITIAL_TRANSLATE_X = -Main.MAIN_MENU_BUTTON_WIDTH;
	
	public static final MainMenuButton
			SETS = new SetsMainMenuButton(),
			STATS = new StatsMainMenuButton(),
			SETTINGS = new SettingsMainMenuButton();
	
	private static final String
			MAIN_MENU_BUTTON_CSS = "main-menu-button",
			OVERLAY_CSS = "overlay",
			TITLE_CSS = "title";
	
	protected final List<Animation> hoverAnimations;
	
	private final TranslateTransition slide, intro;
	private final HBox overlay;
	private final Label title;
	private final ResizableImage banner = new ResizableImage(Main.BANNER);
	private final ImageView iconView;
	
	protected MainMenuButton(Image icon, String text) {
		title = new Label(text);
		iconView = new ImageView(icon);
		overlay = new HBox(iconView, title);
		initOverlay();
		
		intro = new TranslateTransition(INTRO_DURATION, this);
		initIntro();
		
		this.setTranslateX(INITIAL_TRANSLATE_X);
		slide = new TranslateTransition(SLIDE_DURATION, this);
		hoverAnimations = new ArrayList<>();
		initHover();
		
		setPrefSize(Main.MAIN_MENU_BUTTON_WIDTH, Main.MAIN_MENU_BUTTON_HEIGHT);
		getChildren().addAll(banner, overlay);
		getStyleClass().add(MAIN_MENU_BUTTON_CSS);
	}
	
	private void initOverlay() {
		initTitle();
		overlay.getStyleClass().add(OVERLAY_CSS);
		overlay.setTranslateX(BUTTON_EXTENSION);
	}
	
	private void initTitle() {
		title.getStyleClass().add(TITLE_CSS);
	}
	
	private void initIntro() {
		intro.setFromX(INITIAL_TRANSLATE_X);
		intro.setToX(-BUTTON_EXTENSION);
	}
	
	private void initHover() {
		initSlide();
		hoverAnimations.add(slide);
		this.setOnMouseEntered(e -> {
			if(!isIntroRunning())
				for(Animation a : hoverAnimations)
					Animations.activate(a);
		});
		this.setOnMouseExited(e -> {
			if(!isIntroRunning())
				for(Animation a : hoverAnimations)
					Animations.deactivate(a);
		});
	}

	private void initSlide() {
		slide.setFromX(-BUTTON_EXTENSION);
		slide.setToX(0);
		slide.setInterpolator(Interpolator.EASE_OUT);
	}

	private boolean isIntroRunning() {
		return intro.getStatus() == Status.RUNNING;
	}
	
	public void animateIn() {
		intro.playFromStart();
	}
	
	public void setOnAction(Runnable r) {
		this.setOnMouseClicked(e -> r.run());
	}
	
	protected ImageView iconView() {
		return iconView;
	}
	
}
