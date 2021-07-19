package base.graphics;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;

/**
 * <p>The {@code protected} {@link VBox} {@link #extra} has the style class {@code extra}.</p>
 * @author Sam Hooper
 *
 */
public class MinimalisticFadePopup extends FadePopup {
	
	private static final String
			MINIMALISTIC_FADE_POPUP_CSS = "minimalistic-popup",
			HEADING_CSS = "heading",
			SUBHEADING_CSS = "subheading",
			EXTRA_CSS = "extra",
			BUTTON_BAR_CSS = "button-bar";
	
	private final VBox vBox;
	private final Label heading, subheading;
	
	protected final VBox extra;
	protected final HBox buttonBar;
	
	public MinimalisticFadePopup(StackPane over, String heading, String subheading) {
		super(over);
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT / 2);
		this.heading = new Label(heading);
		this.subheading = new Label(subheading);
		extra = new VBox(); //empty by default, allows subclasses to add extra content
		buttonBar = new HBox();
		vBox = new VBox(this.heading, new TextFlow(this.subheading), extra, buttonBar);
		initVBox();
		getChildren().add(vBox);
		getStyleClass().add(MINIMALISTIC_FADE_POPUP_CSS);
	}
	
	private void initVBox() {
		extra.getStyleClass().add(EXTRA_CSS);
		buttonBar.getStyleClass().add(BUTTON_BAR_CSS);
		initLabels();
	}
	
	private void initLabels() {
		heading.getStyleClass().add(HEADING_CSS);
		subheading.getStyleClass().add(SUBHEADING_CSS);
		subheading.maxWidthProperty().bind(vBox.widthProperty());
		subheading.setWrapText(true);
	}
	
}
