package base.stats;

public class SetOverallTab extends StatsTab {
	
	SetOverallTab() {
		super("Overall");
		setContent(new SetOverallTabContent());
	}

	@Override
	SetOverallTabContent content() {
		return (SetOverallTabContent) getContent();
	}
	
}
