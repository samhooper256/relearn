/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class SetSingleTabContent extends StatsTabContent {

	private static final String
			SET_SINGLE_TAB_CONTENT_CSS = "set-single-tab-content",
			PRACTICE_COUNT_CSS = "practice-count";
	private static String practiceString(int practiceCount) {
		return String.format("Practiced %d time%s (in full)", practiceCount, practiceCount == 1 ? "" : "s");
	}
	
	private final ProblemSet set;
	private final TopicAccuracyDistribution dist;
	private final Label practiceCount;
	
	public SetSingleTabContent(ProblemSet set) {
		super(set.name());
		this.set = set;
		
		dist = new TopicAccuracyDistribution(set);
		VBox.setVgrow(dist, Priority.ALWAYS);
		
		practiceCount = new Label(practiceString(set.practiceCount()));
		practiceCount.getStyleClass().add(PRACTICE_COUNT_CSS);
		
		vBox.getChildren().addAll(practiceCount, dist);
		
		getStyleClass().add(SET_SINGLE_TAB_CONTENT_CSS);
	}
	
	public ProblemSet set() {
		return set;
	}

	@Override
	void updateStats() {
		System.out.printf("[enter] SetSingleTabContent.updateStats()%n");
		practiceCount.setText(practiceString(set.practiceCount()));
		updateOverallAccuracy(Data.accuracyStatsForSet(set));
		dist.update(Data.mapForSet(set));
	}
	
	
}
