package base.practice;

import base.problems.Problem;
import fxutils.Borders;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

/**
 * 
 * @author Sam Hooper
 *
 */
public class UserArea extends GridPane {
	
	private static final double FIELD_WIDTH = 400;
	private static final Border INCORRECT_ANSWER_BORDER = Borders.of(Color.RED);
	private static final String
			USER_AREA_CSS = "user-area",
			INPUT_AREA_CSS = "input-area",
			BUTTON_BAR_CSS = "button-bar",
			SUBMIT_BUTTON_CSS = "submit-button",
			SHOW_ANSWER_BUTTON_CSS = "show-answer-button",
			PROBLEM_DISPLAY_CSS = "problem-display";
			
	private final VBox inputArea, displayArea;
	private final StackPane problemDisplayWrap;
	private final TextField field;
	private final WebView problemDisplay;
	private final HBox buttonBar;
	private final Button submitButton, showAnswerButton;
	
	private Problem problem;
	private boolean incorrectAnswerGiven, answerShown;
	
	public UserArea() {
		
		problemDisplay = new WebView();
		problemDisplayWrap = new StackPane(problemDisplay);
		
		field = new TextField();
		submitButton = new Button("Submit");
		showAnswerButton = new Button("Show Answer");
		buttonBar = new HBox(showAnswerButton, submitButton);
		
		displayArea = new VBox(problemDisplay);
		inputArea = new VBox(field, buttonBar);
		
		init();
		
		problem = null;
		incorrectAnswerGiven = false;
		answerShown = false;
	}

	private void init() {
		initProblemDisplay();
		initInputArea();
		
		RowConstraints r1 = new RowConstraints();
		r1.setPercentHeight(40);
		
		RowConstraints r2 = new RowConstraints();
		r2.setPercentHeight(60);
		
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(100);
		
		getRowConstraints().addAll(r1, r2);
		getColumnConstraints().add(c1);
		add(displayArea, 0, 0);
		add(inputArea, 0, 1);
		setPickOnBounds(false);
		getStyleClass().add(USER_AREA_CSS);
	}

	private void initInputArea() {
		initField();
		initButtonBar();
		inputArea.getStyleClass().add(INPUT_AREA_CSS);
	}
	
	private void initProblemDisplay() {
		problemDisplay.getEngine().setUserStyleSheetLocation(getClass().getResource("problemdisplay.css").toString());
		problemDisplayWrap.setPrefSize(FIELD_WIDTH, 100);
		problemDisplay.getStyleClass().add(PROBLEM_DISPLAY_CSS);
		problemDisplay.setMouseTransparent(true); //cannot be set from CSS
		problemDisplay.setDisable(true); //this ensures that it never receives focus - also cannot be set from CSS.
	}
	
	private void initField() {
		field.setPrefWidth(FIELD_WIDTH);
		field.setBorder(Border.EMPTY);
		field.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				e.consume();
				submitAction();
			}
		});
	}
	
	private void initButtonBar() {
		initSubmitButton();
		initShowAnswerButton();
		buttonBar.getStyleClass().add(BUTTON_BAR_CSS);
	}
	
	private void initSubmitButton() {
		submitButton.setOnAction(e -> submitAction());
		submitButton.getStyleClass().add(SUBMIT_BUTTON_CSS);
	}
	
	private void submitAction() {
		String text = fieldText().strip();
		if(text.isBlank())
			return; //do nothing - the user clicked "Submit" when they hadn't entered anything.
		if(problem.isCorrect(text))
			correctAnswerAction();
		else
			incorrectAnswerAction();
	}

	private void initShowAnswerButton() {
		showAnswerButton.setOnAction(e -> showAnswerAction());
		showAnswerButton.getStyleClass().add(SHOW_ANSWER_BUTTON_CSS);
	}
	
	private void showAnswerAction() {
		answerShown = true;
		pane().addIncorrectProblem(problem());
		field.setText(problem().sampleAnswer());
	}
	
	private void correctAnswerAction() {
		field.setBorder(Border.EMPTY);
		if(!incorrectAnswerGiven && !answerShown)
			pane().notifyCorrect(problem());
		else
			pane().notifyIncorrect(problem());
		pane().problemCompleted();
	}
	
	private void incorrectAnswerAction() {
		System.out.printf("incorrectAnswerAction%n");
		incorrectAnswerGiven = true;
		field.setBorder(INCORRECT_ANSWER_BORDER); //TODO better - this changes the layout of everything
	}
	
	private void clearField() {
		field.clear();
	}
	
	private String fieldText() {
		return field.getText();
	}
	
	private void setProblemHTML(String html) {
		problemDisplay.getEngine().loadContent(html);
	}
	
	void focusOnField() {
		field.requestFocus();
	}
	
	Problem problem() {
		return problem;
	}
	
	void setup(Problem problem) {
		incorrectAnswerGiven = false;
		this.problem = problem;
		clearField();
		setProblemHTML(problem.statement().html());
	}
	
	void cleanUpOnFinish() {
		clearField();
	}
	
	PracticePane pane() {
		return (PracticePane) getParent();
	}
	
}
