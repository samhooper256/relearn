/**
 * 
 */
package base.stats;

import java.io.*;
import java.util.*;

import base.*;
import base.problems.Problem;
import base.sets.ProblemSet;
import topics.TopicUtils;
import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public final class Data {
	
	static final class DataMap extends HashMap<String, Stats> {

		private static final long serialVersionUID = 495053631322059137L;
		
		public void addCorrect(String topicName) {
			getStats(topicName).addCorrect();
		}
		
		public void addIncorrect(String topicName) {
			getStats(topicName).addIncorrect();
		}

		/** Creates a new {@link Stats} if one is not present.*/
		public Stats getStats(String topicName) {
			ensurePresent(topicName);
			return get(topicName);
		}

		public void ensurePresent(String topicName) {
			if(!containsKey(topicName))
				put(topicName, new Stats());
		}
		
	}
	
	private static final class SetMap extends HashMap<ProblemSet, DataMap> {

		private static final long serialVersionUID = 9126415248488998446L;

		/** Creates a new {@link DataMap} if one is not present.*/
		private DataMap getDataMap(ProblemSet set) {
			final DataMap map;
			if(!containsKey(set))
				put(set, map = new DataMap());
			else
				map = get(set);
			return map;
		}
		
	}
	
	private static SetMap MAP_BY_SETS = null;
	private static DataMap MAP_BY_TOPICS = null;
	
	public static synchronized void load() {
		if(MAP_BY_SETS != null)
			throw new IllegalStateException("MAP should be null if nothing is loaded");
		MAP_BY_TOPICS = new DataMap();
		File statsFile = new File(Main.STATS_FOLDER, "stats.dat");
		if(!statsFile.exists()) {
			MAP_BY_SETS = new SetMap();
			for(String name : TopicUtils.allNames())
				MAP_BY_TOPICS.put(name, new Stats());
		}
		else {
			try {
				MAP_BY_SETS = IO.readObject(statsFile);
			}
			catch(Exception e) {
				e.printStackTrace(); //TODO better error handling?
			}
			fillMapByTopicsFromMapBySets();
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
		for(DataMap m : MAP_BY_SETS.values())
			m.forEach((topicName, stats) -> MAP_BY_TOPICS.getStats(topicName).addStats(stats));
		TopicUtils.allNames().forEach(name -> MAP_BY_TOPICS.ensurePresent(name));
	}
	
	private static Stats statsForTopicTrusted(String topicName) {
		return MAP_BY_TOPICS.getStats(topicName);
	}
	
	private static DataMap dataMapFor(ProblemSet set) {
		return MAP_BY_SETS.getDataMap(set);
	}
	
	public static void addCorrect(ProblemSet set, String topicName) {
		dataMapFor(set).addCorrect(topicName);
		statsForTopicTrusted(topicName).addCorrect();
	}
	
	public static void addCorrect(ProblemSet set, Problem problem) {
		addCorrect(set, problem.topic().name());
	}
	
	public static void addIncorrect(ProblemSet set, String topicName) {
		dataMapFor(set).addIncorrect(topicName);
		statsForTopicTrusted(topicName).addIncorrect();
	}
	
	public static void addIncorrect(ProblemSet set, Problem problem) {
		addIncorrect(set, problem.topic().name());
	}
	
	public static void addResults(ProblemSet set, List<Problem> correctProblems, List<Problem> incorrectProblems) {
		for(Problem p : correctProblems)
			addCorrect(set, p);
		for(Problem p : incorrectProblems)
			addIncorrect(set, p);
	}
	
	public static ReadOnlyStats statsForTopic(String topicName) {
		return statsForTopicTrusted(topicName);
	}
	
	public static boolean hasDoneProblemFromTopic(String topicName) {
		return !statsForTopic(topicName).isEmpty();
	}
	
	public static void removeStatsFor(ProblemSet set) {
		MAP_BY_SETS.remove(set);
	}
	
	/** The returned map <b>SHOULD NOT BE MODIFIED.</b>*/
	public static DataMap mapByTopics() {
		return MAP_BY_TOPICS;
	}
	
	public static void debugPrint() {
		System.out.printf("MAP_BY_SETS:%n");
		for(Map.Entry<ProblemSet, DataMap> e : MAP_BY_SETS.entrySet())
			System.out.printf("\t%s = %s%n", e.getKey().name(), e.getValue());
		System.out.printf("MAP_BY_TOPICS:%n");
		for(Map.Entry<String, Stats> e : MAP_BY_TOPICS.entrySet())
			System.out.printf("\t%s = %s%n", e.getKey(), e.getValue());
	}
	
}
