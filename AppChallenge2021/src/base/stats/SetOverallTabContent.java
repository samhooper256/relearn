package base.stats;

import base.sets.ProblemSet;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

final class SetOverallTabContent extends StatsTabContent {
	
	private static final String
			SET_OVERALL_TAB_CONTENT_CSS = "set-overall-tab-content",
			EMPTY_LABEL_CSS = "empty-label";
	
	private final SetBoardContainer setBoardContainer;
	private final Label emptyLabel;
	
	SetOverallTabContent() {
		super("Overall");
		setBoardContainer = new SetBoardContainer();
		emptyLabel = new Label("You have not created any sets yet.");
		initEmptyLabel();
		VBox.setVgrow(setBoardContainer, Priority.ALWAYS);
		getStyleClass().add(SET_OVERALL_TAB_CONTENT_CSS);
		vBox.getChildren().add(setBoardContainer);
	}

	private void initEmptyLabel() {
		emptyLabel.getStyleClass().add(EMPTY_LABEL_CSS);
	}
	
	@Override
	void updateStats() {
		setBoardContainer.board().updateStats();
		updateOverallAccuracy(Data.overall());
		if(ProblemSet.all().isEmpty())
			setDisplay(emptyLabel);
		else
			setDisplay(setBoardContainer);
	}
	
	private void setDisplay(Node node) {
		vBox.getChildren().set(1, node);
	}

	void setAdded(ProblemSet set) {
		setBoardContainer.board().setAdded(set);
	}
	
	void setRemoved(ProblemSet set) {
		setBoardContainer.board().setRemoved(set);
	}
	
}
