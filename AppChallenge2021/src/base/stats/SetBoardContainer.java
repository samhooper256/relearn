package base.stats;

import fxutils.PolarizedPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

final class SetBoardContainer extends VBox {
	
	private static final String
			SET_BOARD_CONTAINER_CSS = "set-board-container",
			HEADER_CSS = "header",
			HEADER_LABEL_CSS = "header-label";
	
	private final PolarizedPane header;
	private final Label setName, numPractices;
	private final SetBoard board;
	
	SetBoardContainer() {
		setName = new Label("Set Name");
		numPractices = new Label("Times Practiced");
		header = new PolarizedPane(setName, numPractices);
		initHeader();
		board = new SetBoard();
		
		VBox.setVgrow(board, Priority.ALWAYS);
		getStyleClass().add(SET_BOARD_CONTAINER_CSS);
		getChildren().addAll(header, board);
	}
	
	private void initHeader() {
		header.getStyleClass().add(HEADER_CSS);
		setName.getStyleClass().add(HEADER_LABEL_CSS);
		numPractices.getStyleClass().add(HEADER_LABEL_CSS);
	}

	SetBoard board() {
		return board;
	}
	
}
