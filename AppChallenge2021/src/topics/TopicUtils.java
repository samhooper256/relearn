/**
 * 
 */
package topics;

import java.util.*;

/**
 * @author Sam Hooper
 *
 */
public final class TopicUtils {

	private TopicUtils() {
		
	}
	
	private static final LinkedHashSet<TopicFactory<?>> FACTORIES;
	
	static {
		FACTORIES = new LinkedHashSet<>();
		Collections.addAll(FACTORIES, 
				Addition.FACTORY
		);
	}
	
	public static Set<TopicFactory<?>> allFactories() {
		return Collections.unmodifiableSet(FACTORIES);
	}
	
}
