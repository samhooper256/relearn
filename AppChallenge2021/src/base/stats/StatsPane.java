/**
 * 
 */
package base.stats;

import base.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public final class StatsPane extends StackPane {
	
	private static final StatsPane INSTANCE = new StatsPane();
	private static final String TITLE = "Stats";
	
	public static final StatsPane get() {
		return INSTANCE;
	}
	
	private final VBox vBox;
	private final HBox header;
	private final BackArrow backArrow;
	private final Label title;
	
	private StatsPane() {
		backArrow = new BackArrow();
		initBackArrow();
		title = new Label(TITLE);
		header = new HBox(backArrow, title);
		vBox = new VBox(header, TopicTabPane.get());
		
		getChildren().add(vBox);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
	public void updateAllStats() {
		TopicTabPane.get().updateAllStats();
	}
}
