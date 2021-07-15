package topics.settings;

final class BooleanSetting implements TopicSetting {

	private static final long serialVersionUID = 7197813030034542907L;
	
	private final String name;
	
	private boolean value;
	
	BooleanSetting(String name, boolean defaultValue) {
		this.name = name;
		value = defaultValue;
	}

	@Override
	public String name() {
		return name;
	}
	
	boolean value() {
		return value;
	}
	
	void set(boolean newValue) {
		value = newValue;
	}
	
}
