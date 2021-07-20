package base.practice;

import base.*;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class AccuracyBar extends Pane {
	
	public static final double ICON_SIZE = 32;
	public static final Duration SLIDE_DURATION = Duration.millis(300);
	
	private static final double SPACING = 4;
	private static final Duration FADE_START = SLIDE_DURATION.divide(2);
	
	private static void addFade(Timeline timeline, ImageView iv, Duration start, Duration end) {
		KeyFrame fadeStart = new KeyFrame(start,
				new KeyValue(iv.opacityProperty(), 0),
				new KeyValue(iv.scaleXProperty(), 0.5),
				new KeyValue(iv.scaleYProperty(), 0.5));
		KeyFrame fadeEnd = new KeyFrame(end,
				new KeyValue(iv.opacityProperty(), 1),
				new KeyValue(iv.scaleXProperty(), 1),
				new KeyValue(iv.scaleYProperty(), 1));
		timeline.getKeyFrames().addAll(fadeStart, fadeEnd);
	}
	
	private final Group group;
	
	AccuracyBar() {
		group = new Group();
		getChildren().add(group);
	}
	
	void addCheck() {
		addIcon(Main.CHECK_BOX);
	}
	
	void addX() {
		addIcon(Main.X_BOX);
	}
	
	private void addIcon(Image icon) {
		ImageView iv = new ImageView(icon);
		if(hasIcon()) {
			iv.setLayoutX(-group.getTranslateX() - ICON_SIZE - SPACING);
			startTransition(iv);
		}
		else {
			iv.setOpacity(0);
			group.getChildren().add(iv);
			Timeline time = new Timeline();
			addFade(time, iv, Duration.ZERO, FADE_START);
			time.playFromStart();
		}
	}
	
	private boolean hasIcon() {
		return group.getChildren().size() > 0;
	}
	
	private void startTransition(ImageView iv) {
		final double x = group.getTranslateX();
		iv.setOpacity(0);
		group.getChildren().add(iv);
		Timeline time = new Timeline();
		KeyFrame slide = new KeyFrame(SLIDE_DURATION,
				new KeyValue(group.translateXProperty(), x + ICON_SIZE + SPACING, Interpolator.EASE_OUT));
		time.getKeyFrames().add(slide);
		addFade(time, iv, FADE_START, SLIDE_DURATION);
		time.playFromStart();
	}
	
	void clear() {
		group.getChildren().clear();
		group.setTranslateX(0);
	}
	
}
