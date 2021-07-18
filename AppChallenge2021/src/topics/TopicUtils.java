/**
 * 
 */
package topics;

import java.util.*;
import java.util.stream.*;

import fxutils.Backgrounds;
import javafx.scene.layout.Background;
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
	private static final LinkedHashMap<String, Background> BACKGROUNDS;
	
	static {
		List<TopicFactory<?>> factoryList = new ArrayList<>();
		Collections.addAll(factoryList, 
				AddingIntegers.FACTORY,
				SubtractingIntegers.FACTORY,
				MultiplyingIntegers.FACTORY,
				DividingIntegers.FACTORY,
				AddingFractions.FACTORY,
				PEMDAS.FACTORY,
				Percentages.FACTORY,
				Quadrants.FACTORY,
				Squares.FACTORY,
				SquareRoots.FACTORY,
				Fractions.FACTORY,
				SubtractingFractions.FACTORY
		);
		Collections.sort(factoryList);
		FACTORIES = new LinkedHashSet<>();
		FACTORIES.addAll(factoryList);
		Random r = new Random(COLOR_GENERATOR_SEED);
		COLORS = new LinkedHashMap<>();
		for(TopicFactory<?> factory : FACTORIES)
			COLORS.put(factory.name(), new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1));
		BACKGROUNDS = new LinkedHashMap<>();
		for(Map.Entry<String, Color> e : COLORS.entrySet())
			BACKGROUNDS.put(e.getKey(), Backgrounds.of(e.getValue()));
	}
	
	/** Returns an unmodifiable view of all the {@link TopicFactory topic factories}. The factories are sorted by
	 * {@link TopicFactory#name() name}.*/
	public static Set<TopicFactory<?>> allFactories() {
		return Collections.unmodifiableSet(FACTORIES);
	}
	
	public static Stream<String> streamNames() {
		return FACTORIES.stream().map(TopicFactory::name);
	}

	/** Returns a new, freshly created {@link Set} with all the {@link Topic} {@link Topic#name() names} in
	 * {@link String#compareTo(String) sorted} order. The returned set may be freely modified.*/
	public static Set<String> allNames() {
		return streamNames().collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public static Color colorOf(Topic topic) {
		return colorOf(topic.name());
	}
	
	public static Color colorOf(String topicName) {
		return COLORS.get(topicName);
	}
	
	public static Background backgroundFor(Topic topic) {
		return backgroundFor(topic.name());
	}
	
	public static Background backgroundFor(String topicName) {
		return BACKGROUNDS.get(topicName);
	}
	
}
