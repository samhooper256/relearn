/**
 * 
 */
package base;

import java.util.IdentityHashMap;

import fxutils.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public class SetCard extends StackPane {
	
	private static final double PREF_WIDTH = 300;
	private static final double PREF_HEIGHT = 150;
	private static final double PENCIL_SIZE = 30;
	private static final IdentityHashMap<ProblemSet, SetCard> CACHE = new IdentityHashMap<>();
	
	
	public static synchronized SetCard of(ProblemSet set) {
		SetCard cached = CACHE.get(set);
		if(cached != null)
			return cached;
		SetCard obj = new SetCard(set);
		CACHE.put(set, obj);
		return obj;
	}
	
	private final ProblemSet set;
	private final VBox vBox;
	private final Button practiceButton;
	private final ImageView pencilView;
	
	private SetCard(ProblemSet set) {
		this.set = set;
		this.setBorder(Borders.of(Color.GREEN));
		this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		
		Label title = new Label(set.name());
		set.nameProperty().addListener(ov -> {
			title.setText(set.name());
		});
		
		practiceButton = new Button("Practice");
		initPracticeButton();
		
		vBox = new VBox(title, practiceButton);
		
		pencilView = new ImageView();
		initPencil();
		
		getChildren().addAll(vBox, pencilView);
	}

	private void initPencil() {
		Image pencilImage = Images.get("pencil.png", PENCIL_SIZE, PENCIL_SIZE, false, true);
		pencilView.setImage(pencilImage);
		pencilView.setOnMouseClicked(e -> {
			Main.mainScene().edit(set);
		});
		StackPane.setAlignment(pencilView, Pos.TOP_RIGHT);
	}
	
	private void initPracticeButton() {
		practiceButton.setOnAction(e -> Main.mainScene().startPractice(getSet().createDeck()));
	}
	
	public ProblemSet getSet() {
		return set;
	}
	
}
