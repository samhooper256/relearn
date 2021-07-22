/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;
import base.stats.Data.SetDataMap;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class SetSingleTabContent extends StatsTabContent {

	private static final String SUBHEADING_SEPARATOR = "\u2022"; //a bullet point
	private static final String
			SET_SINGLE_TAB_CONTENT_CSS = "set-single-tab-content",
			PRACTICE_COUNT_CSS = "practice-count";
	private static String practiceString(int practiceCount) {
		return String.format("Practiced %d time%s (in full)", practiceCount, practiceCount == 1 ? "" : "s");
	}
	
	private final ProblemSet set;
	private final TopicAccuracyDistribution dist;
	private final Label subheading;
	
	public SetSingleTabContent(ProblemSet set) {
		super(set.name());
		this.set = set;
		
		dist = new TopicAccuracyDistribution(set);
		VBox.setVgrow(dist, Priority.ALWAYS);
		
		subheading = new Label(practiceString(set.practiceCount()));
		subheading.getStyleClass().add(PRACTICE_COUNT_CSS);
		
		vBox.getChildren().addAll(subheading, dist);
		
		getStyleClass().add(SET_SINGLE_TAB_CONTENT_CSS);
	}
	
	public ProblemSet set() {
		return set;
	}

	@Override
	void updateStats() {
		SetDataMap map = Data.mapForSet(set);
		updateSubheading(set().practiceCount(), map.deckTimes());
		updateOverallAccuracy(Data.accuracyStatsForSet(set));
		dist.update(map);
	}
	
	private void updateSubheading(int practiceCount, ReadOnlyTimeStats stats) {
		if(stats.isEmpty())
			subheading.setText(practiceString(practiceCount));
		else
			subheading.setText(String.format("%s %s Average time: %s %2$s Fastest time: %s",
				practiceString(set.practiceCount()),
				SUBHEADING_SEPARATOR,
				Data.formatTime(stats.averageTime()),
				Data.formatTime(stats.fastestTime())
			));
	}
	
}
