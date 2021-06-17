/**
 * 
 */
package base;

import fxutils.Borders;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class SetCard extends StackPane {
	
	private static final double PREF_WIDTH = 300;
	private static final double PREF_HEIGHT = 150;
	
	private final ProblemSet set;
	private final VBox vBox;
	private final Button practiceButton;

	public SetCard(ProblemSet set) {
		this.set = set;
		this.setBorder(Borders.of(Color.GREEN));
		this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		
		Label title = new Label(set.name());
		
		practiceButton = new Button("Practice");
		initPracticeButton();
		
		vBox = new VBox(title, practiceButton);
		getChildren().add(vBox);
		
	}
	
	private void initPracticeButton() {
		practiceButton.setOnAction(e -> Main.mainScene().startPractice(getSet().createDeck()));
	}
	
	public ProblemSet getSet() {
		return set;
	}
	
}
