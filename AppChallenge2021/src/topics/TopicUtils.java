/**
 * 
 */
package topics;

import java.util.*;
import java.util.stream.*;

import javafx.scene.paint.Color;

/**
 * @author Sam Hooper
 *
 */
public final class TopicUtils {

	private static final long COLOR_GENERATOR_SEED = 0xFAB399AF339C72CDL; //random number

	private TopicUtils() {
		
	}
	
	private static final LinkedHashSet<TopicFactory<?>> FACTORIES;
	private static final LinkedHashMap<String, Color> COLORS;
	
	static {
		FACTORIES = new LinkedHashSet<>();
		Collections.addAll(FACTORIES, 
				Addition.FACTORY,
				Subtraction.FACTORY,
				Multiplication.FACTORY,
				Division.FACTORY,
				PEMDAS.FACTORY,
				Percentages.FACTORY
		);
		Random r = new Random(COLOR_GENERATOR_SEED);
		COLORS = new LinkedHashMap<>();
		for(TopicFactory<?> factory : FACTORIES)
			COLORS.put(factory.name(), new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1));
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
	
	public static Color colorOf(Topic topic) {
		return colorOf(topic.name());
	}
	
	public static Color colorOf(String topicName) {
		return COLORS.get(topicName);
	}
	
}
