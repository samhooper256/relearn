package base;

import base.editor.EditorPane;
import base.graphics.MinimalisticFadePopup;
import base.sets.ProblemSet;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DeletePopup extends MinimalisticFadePopup {
	
	private static final String
			DELETE_POPUP_CSS = "delete-popup",
			YES_CSS = "yes",
			NO_CSS = "no";
	
	private final Button yes, no;
	private final Runnable afterYesAction;
	
	private ProblemSet set;
	
	public DeletePopup(StackPane over, Runnable afterYesAction) {
		super(over, "Are you sure?", "The current set will be permanently erased.");
		this.afterYesAction = afterYesAction;
		yes = new Button("Yes, delete the set");
		no = new Button("No, keep the set");
		initButtonBar();
		getStyleClass().add(DELETE_POPUP_CSS);
	}

	private void initButtonBar() {
		buttonBar.getChildren().addAll(no, yes);
		initYes();
		initNo();
	}

	private void initYes() {
		yes.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, YES_CSS);
		yes.setOnAction(e -> yesAction());
	}
	
	private void yesAction() {
		ProblemSet.remove(set);
		afterYesAction.run();
	}
	
	private void initNo() {
		no.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, NO_CSS);
		no.setOnAction(e -> noAction());
	}
	
	private void noAction() {
		fadeOut();
	}
	
	public void fadeIn(ProblemSet set) {
		setProblemSet(set);
		fadeIn();
	}
	
	public void setProblemSet(ProblemSet set) {
		this.set = set;
	}
	
	public ProblemSet problemSet() {
		return set;
	}

	@Override
	public void fadeOut() {
		set = null;
		super.fadeOut();
	}

	@Override
	public void hidePopup() {
		set = null;
		super.hidePopup();
	}
	
}
