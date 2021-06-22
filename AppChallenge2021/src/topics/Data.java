/**
 * 
 */
package topics;

import java.io.*;
import java.util.*;

import base.*;
import utils.IO;

/**
 * @author Sam Hooper
 *
 */
public final class Data {
	
	public static final class Stats implements Serializable {
		
		private static final long serialVersionUID = -4346811725723203551L;
		
		private int correct;
		private int incorrect;
		
		public Stats() {
			this(0, 0);
		}
		
		public Stats(int correct, int incorrect) {
			this.correct = correct;
			this.incorrect = incorrect;
		}
		
		public int correct() {
			return correct;
		}
		
		public int incorrect() {
			return incorrect;
		}
		
		public void addCorrect() {
			correct++;
		}
		
		public void addIncorrect() {
			incorrect++;
		}
		
		public void addCorrect(int n) {
			correct += n;
		}
		
		public void addIncorrect(int n) {
			incorrect += n;
		}
		
		public void addStats(Stats s) {
			addCorrect(s.correct());
			addIncorrect(s.incorrect());
		}
		
		public int total() {
			return correct() + incorrect();
		}
		
		@Override
		public String toString() {
			return String.format("{%d, %d}", correct(), incorrect());
		}
		
	}
	
	private static final class DataMap extends HashMap<String, Stats> {

		private static final long serialVersionUID = 495053631322059137L;
		
		public void addCorrect(String topicName) {
			getStats(topicName).addCorrect();
		}
		
		public void addIncorrect(String topicName) {
			getStats(topicName).addIncorrect();
		}

		/** Creates a new {@link Stats} if one is not present.*/
		private Stats getStats(String topicName) {
			final Stats stats;
			if(!containsKey(topicName))
				put(topicName, stats = new Stats());
			else
				stats = get(topicName);
			return stats;
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
		File statsFile = new File(Main.STATS_FOLDER, "stats.dat");
		if(!statsFile.exists()) {
			MAP_BY_SETS = new SetMap();
			MAP_BY_TOPICS = new DataMap();
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
			buildMapByTopicsFromMapBySets();
		}
	}
	
	public static synchronized void save() {
		
		try {
			if(Main.STATS_FILE.exists())
				Main.STATS_FILE.createNewFile();
			IO.writeObject(Main.STATS_FILE, MAP_BY_SETS);
		} catch (IOException e) {
			System.err.println("Could not save stats.");
			e.printStackTrace();
		}
	}
	
	private static void buildMapByTopicsFromMapBySets() {
		MAP_BY_TOPICS = new DataMap();
		for(DataMap m : MAP_BY_SETS.values())
			for(Map.Entry<String, Stats> e : m.entrySet())
				MAP_BY_TOPICS.getStats(e.getKey()).addStats(e.getValue());
	}
	
	private static Stats topicStats(String topicName) {
		return MAP_BY_TOPICS.get(topicName);
	}
	
	private static DataMap dataMapForSet(ProblemSet set) {
		return MAP_BY_SETS.getDataMap(set);
	}
	
	public static void addCorrect(ProblemSet set, String topicName) {
		dataMapForSet(set).addCorrect(topicName);
		topicStats(topicName).addCorrect();
	}
	
	public static void addCorrect(ProblemSet set, Problem problem) {
		addCorrect(set, problem.topic().name());
	}
	
	public static void addIncorrect(ProblemSet set, String topicName) {
		dataMapForSet(set).addIncorrect(topicName);
		topicStats(topicName).addIncorrect();
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
	
	public static void debugPrint() {
		System.out.printf("MAP_BY_SETS:%n");
		for(Map.Entry<ProblemSet, DataMap> e : MAP_BY_SETS.entrySet())
			System.out.printf("\t%s = %s%n", e.getKey().name(), e.getValue());
		System.out.printf("MAP_BY_TOPICS:%n");
		for(Map.Entry<String, Stats> e : MAP_BY_TOPICS.entrySet())
			System.out.printf("\t%s = %s%n", e.getKey(), e.getValue());
	}
}
