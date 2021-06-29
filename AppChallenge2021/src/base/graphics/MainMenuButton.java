/**
 * 
 */
package base.graphics;

import java.util.*;

import base.Main;
import fxutils.*;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class MainMenuButton extends StackPane {
	
	protected static final Duration SLIDE_DURATION = Duration.millis(200);
	
	private static final int BUTTON_EXTENSION = 100;
	
	public static final MainMenuButton
			SETS = new MainMenuButton(Main.setsIcon(), "Sets"),
			STATS = new MainMenuButton(Main.statsIcon(), "Stats"),
			SETTINGS = new SettingsMainMenuButton();
	
	private static final String
			MAIN_MENU_BUTTON_CSS = "main-menu-button",
			OVERLAY_CSS = "overlay",
			TITLE_CSS = "title";
	
	protected final List<Animation> hoverAnimations;
	
	private final TranslateTransition slide;
	private final HBox overlay;
	private final Label title;
	private final ResizableImage banner = new ResizableImage(Main.bannerImage());
	private final ImageView iconView;
	
	protected MainMenuButton(Image icon, String text) {
		title = new Label(text);
		iconView = new ImageView(icon);
		overlay = new HBox(iconView, title);
		initOverlay();
		
		this.setTranslateX(-BUTTON_EXTENSION);
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
	
	private void initHover() {
		initSlide();
		hoverAnimations.add(slide);
		this.setOnMouseEntered(e -> {
			for(Animation a : hoverAnimations)
				activateAnimation(a);
		});
		this.setOnMouseExited(e -> {
			for(Animation a : hoverAnimations)
				deactivateAnimation(a);
		});
	}

	private void deactivateAnimation(Animation animation) {
		if(animation.getRate() > 0)
			animation.setRate(-animation.getRate());
		if(animation.getCurrentTime().compareTo(Duration.ZERO) > 0)
			animation.play();
	}

	private void activateAnimation(Animation animation) {
		if(animation.getRate() < 0)
			animation.setRate(-animation.getRate());
		if(animation.getCurrentTime().compareTo(Duration.ZERO) == 0)
			animation.play();
	}
	
	private void initSlide() {
		slide.setFromX(-BUTTON_EXTENSION);
		slide.setToX(0);
		slide.setInterpolator(Interpolator.EASE_OUT);
	}
	
	public void setOnAction(Runnable r) {
		this.setOnMouseClicked(e -> r.run());
	}
	
	protected ImageView iconView() {
		return iconView;
	}
	
}
