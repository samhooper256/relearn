/**
 * 
 */
package topics;

import java.util.*;
import java.util.stream.*;

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
				Addition.FACTORY,
				Subtraction.FACTORY,
				Multiplication.FACTORY
		);
	}
	
	public static Set<TopicFactory<?>> allFactories() {
		return Collections.unmodifiableSet(FACTORIES);
	}
	
	public static Stream<String> streamNames() {
		return FACTORIES.stream().map(TopicFactory::name);
	}
	
	public static Set<String> allNames() {
		return streamNames().collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
}
