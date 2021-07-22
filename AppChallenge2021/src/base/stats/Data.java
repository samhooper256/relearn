/**
 * 
 */
package base.stats;

import java.io.*;
import java.util.*;

import base.*;
import base.sets.ProblemSet;
import topics.TopicUtils;
import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public final class Data {
	
	interface DataMap extends Map<String, AccuracyStats> {
		
		/** Creates a new {@link AccuracyStats} (of the appropriate type) if one is not present.*/
		default AccuracyStats getStats(String topicName) {
			ensurePresent(topicName);
			return get(topicName);
		}
		
		default void ensurePresent(String topicName) {
			if(!containsKey(topicName))
				put(topicName, new AccuracyStats());
		}
		
	}
	
	static final class BasicDataMap extends HashMap<String, AccuracyStats> implements DataMap {

		private static final long serialVersionUID = 495053631322059137L;
		
		public void addCorrect(String topicName) {
			getStats(topicName).addCorrect();
		}
		
		public void addIncorrect(String topicName) {
			getStats(topicName).addIncorrect();
		}

	}
	
	static final class SetDataMap extends HashMap<String, AccuracyStats> implements DataMap {

		private static final long serialVersionUID = -6636593044618879159L;

		private final AccuracyStats overallAccuracy;
		private final TimeStats deckTimes;
		
		SetDataMap() {
			overallAccuracy = new AccuracyStats();
			deckTimes = new TimeStats();
		}
		
		void addCorrect(String topicName) {
			getStats(topicName).addCorrect();
			overallAccuracy.addCorrect();
		}
		
		void addIncorrect(String topicName) {
			getStats(topicName).addIncorrect();
			overallAccuracy.addIncorrect();
		}
		
		void addTime(double timeInMillis) {
			deckTimes.addTime(timeInMillis);
		}

		ReadOnlyAccuracyStats overallAccuracy() {
			return overallAccuracy;
		}

	}

	private static final class SetMap extends HashMap<ProblemSet, SetDataMap> {

		private static final long serialVersionUID = 9126415248488998446L;

		/** Creates a new {@link DataMap} if one is not present.*/
		private SetDataMap getDataMap(ProblemSet set) {
			final SetDataMap map;
			if(!containsKey(set))
				put(set, map = new SetDataMap());
			else
				map = get(set);
			return map;
		}
		
	}
	
	private static SetMap MAP_BY_SETS = null;
	private static BasicDataMap MAP_BY_TOPICS = null;
	private static final AccuracyStats OVERALL = new AccuracyStats();
	
	public static synchronized void load() {
		if(MAP_BY_SETS != null)
			throw new IllegalStateException("MAP should be null if nothing is loaded");
		MAP_BY_TOPICS = new BasicDataMap();
		File statsFile = new File(Main.STATS_FOLDER, "stats.dat");
		if(!statsFile.exists()) {
			MAP_BY_SETS = new SetMap();
			for(String name : TopicUtils.allNames())
				MAP_BY_TOPICS.put(name, new AccuracyStats());
		}
		else {
			try {
				MAP_BY_SETS = IO.readObject(statsFile);
			}
			catch(Exception e) {
				e.printStackTrace(); //TODO better error handling?
			}
			fillMapByTopicsFromMapBySets();
			fillInOverallFromMapByTopics();
		}
	}
	
	public static synchronized void save() {
		try {
			Main.STATS_FILE.createNewFile();
			IO.writeObject(Main.STATS_FILE, MAP_BY_SETS);
		} catch (IOException e) {
			System.err.println("Could not save stats.");
			e.printStackTrace();
		}
	}
	
	private static void fillMapByTopicsFromMapBySets() {
		for(SetDataMap m : MAP_BY_SETS.values())
			m.forEach((topicName, stats) -> MAP_BY_TOPICS.getStats(topicName).addStats(stats));
		TopicUtils.allNames().forEach(name -> MAP_BY_TOPICS.ensurePresent(name));
	}
	
	private static void fillInOverallFromMapByTopics() {
		assert OVERALL.total() == 0;
		OVERALL.addCorrect(MAP_BY_TOPICS.values().stream().mapToInt(AccuracyStats::correct).sum());
		OVERALL.addIncorrect(MAP_BY_TOPICS.values().stream().mapToInt(AccuracyStats::incorrect).sum());
	}
	
	private static AccuracyStats statsForTopicTrusted(String topicName) {
		return MAP_BY_TOPICS.getStats(topicName);
	}
	
	private static SetDataMap dataMapFor(ProblemSet set) {
		return MAP_BY_SETS.getDataMap(set);
	}
	
	public static void addCorrect(ProblemSet set, String topicName) {
		dataMapFor(set).addCorrect(topicName);
		statsForTopicTrusted(topicName).addCorrect();
		OVERALL.addCorrect();
	}
	
	public static void addIncorrect(ProblemSet set, String topicName) {
		dataMapFor(set).addIncorrect(topicName);
		statsForTopicTrusted(topicName).addIncorrect();
		OVERALL.addIncorrect();
	}
	
	/** @param set the {@link ProblemSet} the deck came from.*/
	public static void addDeckTime(ProblemSet set, double timeInMillis) {
		dataMapFor(set).addTime(timeInMillis);
	}
	
	public static ReadOnlyAccuracyStats accuracyStatsForTopic(String topicName) {
		return statsForTopicTrusted(topicName);
	}
	
	public static boolean hasDoneProblemFromTopic(String topicName) {
		return !accuracyStatsForTopic(topicName).isEmpty();
	}
	
	public static void removeStatsFor(ProblemSet set) {
		MAP_BY_SETS.remove(set);
	}
	
	/** The returned map <b>SHOULD NOT BE MODIFIED.</b>*/
	public static DataMap mapByTopics() {
		return MAP_BY_TOPICS;
	}
	
	/** The returned map <b>SHOULD NOT BE MODIFIED.</b>*/
	public static SetDataMap mapForSet(ProblemSet set) {
		return MAP_BY_SETS.getDataMap(set);
	}
	
	public static ReadOnlyAccuracyStats accuracyStatsForSet(ProblemSet set) {
		return mapForSet(set).overallAccuracy();
	}

	public static ReadOnlyAccuracyStats overall() {
		return overallTrusted();
	}
	
	private static AccuracyStats overallTrusted() {
		return OVERALL;
	}
	/** This method also calls {@link ProblemSet#clearPractices()}.*/
	public static void eraseStatsFor(ProblemSet set) {
		DataMap map = mapForSet(set);
		for(Map.Entry<String, AccuracyStats> e : map.entrySet()) {
			statsForTopicTrusted(e.getKey()).removeStats(e.getValue());
			overallTrusted().removeStats(e.getValue());
		}
		map.clear();
		set.clearPractices();
	}
	
	public static void debugPrint() {
		System.out.printf("MAP_BY_SETS:%n");
		for(Map.Entry<ProblemSet, SetDataMap> e : MAP_BY_SETS.entrySet())
			System.out.printf("\t%s = %s :: %s%n", e.getKey().name(), e.getValue().overallAccuracy(), e.getValue());
		System.out.printf("MAP_BY_TOPICS:%n");
		for(Map.Entry<String, AccuracyStats> e : MAP_BY_TOPICS.entrySet())
			System.out.printf("\t%s = %s%n", e.getKey(), e.getValue());
	}
	
}
