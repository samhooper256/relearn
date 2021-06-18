/**
 * 
 */
package base;

import javafx.beans.property.*;

/**
 * @author Sam Hooper
 *
 */
public final class ProblemSet {
	
	private StringProperty name;
	private final SetConfiguration config;
	
	public ProblemSet(String name) {
		this(name, new SetConfiguration());
	}
	
	public ProblemSet(String name, SetConfiguration config) {
		this.name = new SimpleStringProperty(name);
		this.config = config;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String name() {
		return nameProperty().get();
	}
	
	public void setName(String name) {
		nameProperty().set(name);
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
}
