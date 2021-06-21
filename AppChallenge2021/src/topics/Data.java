/**
 * 
 */
package topics;

import java.util.*;

/**
 * @author Sam Hooper
 *
 */
public final class Data {
	
	public static final class Stats {
		
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
		
	}
	
	private static Map<String, Stats> MAP = null;
	
	static synchronized void load() {
		if(MAP != null)
			throw new IllegalStateException("MAP should be null if nothing is loaded");
		for(String name : TopicUtils.allNames())
			MAP.put(name, new Stats());
		//TODO actually load from file
	}
	
	private static Stats statsFor(String topicName) {
		return MAP.get(topicName);
	}
	
	public static void addCorrect(String topicName) {
		statsFor(topicName).addCorrect();
	}
	
	public static void addIncorrect(String topicName) {
		statsFor(topicName).addIncorrect();
	}
	
}
