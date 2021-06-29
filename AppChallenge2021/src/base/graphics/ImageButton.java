/**
 * 
 */
package base.graphics;

import javafx.scene.image.*;

/**
 * @author Sam Hooper
 *
 */
public abstract class ImageButton extends ImageView {
	
	public ImageButton(Image image) {
		setImage(image);
	}
	
	public ImageButton(Image image, Runnable action) {
		setImage(image);
		setOnAction(action);
	}
	
	public void setOnAction(Runnable r) {
		setOnMouseClicked(e -> r.run());
	}
	
}
