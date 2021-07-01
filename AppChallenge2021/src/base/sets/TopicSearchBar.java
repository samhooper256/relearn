package base.sets;

import base.Main;
import fxutils.Images;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Sam Hooper
 *
 */
public class TopicSearchBar extends HBox {
	
	private static final String MAGNIFYING_GLASS_CSS = "magnifying-glass";
	
	private static TopicSelectorBox selectorBox() {
		return TopicSelectionPopup.get().selectorBox();
	}
	
	private final ImageView magnifyingGlass;
	
	public TopicSearchBar() {
		magnifyingGlass = new ImageView();
		initMagnifyingGlass();
		
		getChildren().addAll(magnifyingGlass);
	}

	private void initMagnifyingGlass() {
		Images.setFitSize(magnifyingGlass, Main.BUTTON_ICON_SIZE);
		magnifyingGlass.getStyleClass().add(MAGNIFYING_GLASS_CSS);
	}
	
	
	
}
