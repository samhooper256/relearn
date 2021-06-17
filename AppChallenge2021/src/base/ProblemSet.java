/**
 * 
 */
package base;

/**
 * @author Sam Hooper
 *
 */
public final class ProblemSet {
	
	private final String name;
	private final SetConfiguration config;
	
	public ProblemSet(String name) {
		this(name, new SetConfiguration());
	}
	
	public ProblemSet(String name, SetConfiguration config) {
		this.name = name;
		this.config = config;
	}
	
	public String name() {
		return name;
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
}
