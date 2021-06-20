package base;

import javafx.scene.image.ImageView;

/**
 * @author Sam Hooper
 *
 */
public class BackArrow extends ImageView {
	
	public BackArrow() {
		setImage(Main.backArrowImage());
	}
	
	public BackArrow(Runnable action) {
		setImage(Main.backArrowImage());
		setOnAction(action);
	}
	
	public void setOnAction(Runnable r) {
		setOnMouseClicked(e -> r.run());
	}
	
}