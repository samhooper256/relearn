package base.stats;

import base.sets.ProblemSet;
import javafx.scene.layout.*;

final class SetOverallTabContent extends StatsTabContent {
	
	private final SetBoard setBoard;
	
	SetOverallTabContent() {
		super("Overall");
		setBoard = new SetBoard();
		VBox.setVgrow(setBoard, Priority.ALWAYS);
		vBox.getChildren().add(setBoard);
	}

	@Override
	void updateStats() {
		setBoard.updateStats();
	}

	void setAdded(ProblemSet set) {
		setBoard.setAdded(set);
	}
	
	void setRemoved(ProblemSet set) {
		setBoard.setRemoved(set);
	}
	
}
