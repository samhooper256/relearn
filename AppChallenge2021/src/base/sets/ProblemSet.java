/**
 * 
 */
package base.sets;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;

import base.Main;
import topics.*;
import utils.IO;

/**
 * <p>The {@code equals} and {@code hashCode} methods are not overridden from {@code Object}.</p>
 * @author Sam Hooper
 *
 */
public final class ProblemSet implements Serializable {
	
	private static final long serialVersionUID = 415601596062566192L;
	private static final Set<ProblemSet> SETS = new HashSet<>();
	private static final Set<String> SET_NAMES = new HashSet<>();
	private static boolean loaded = false;
	private static List<Consumer<ProblemSet>> onRegisterActions;
	
	public static synchronized void loadSets() {
		if(loaded)
			return; //don't load if we've already done it earlier.
		loaded = true;
		for(File f : Main.SETS_FOLDER.listFiles()) {
			try {
				ProblemSet set = IO.readObject(f);
				SETS.add(set);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		for(ProblemSet s : SETS)
			SET_NAMES.add(s.name());
	}
	
	public static Set<ProblemSet> allSets() {
		return Collections.unmodifiableSet(SETS);
	}
	
	public static void addOnRegisterAction(Consumer<ProblemSet> action) {
		if(onRegisterActions == null)
			onRegisterActions = new ArrayList<>();
		onRegisterActions.add(action);
	}
	
	private static void runOnRegisterActions(ProblemSet set) {
		if(onRegisterActions == null)
			return;
		for(Consumer<ProblemSet> action : onRegisterActions)
			action.accept(set);
	}

	private String name;
	private final SetConfiguration config;
	
	/** <p>Creates a new {@link ProblemSet} whose {@link #name() name} is the empty string.</p> */
	public ProblemSet() {
		this("");
	}
	
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
	
	public void setName(String newName) {
		if(SET_NAMES.contains(newName))
			throw new IllegalStateException(String.format("Name already used: %s", newName));
		SET_NAMES.remove(name);
		name = Objects.requireNonNull(newName);
		SET_NAMES.add(name);
		SetCard.of(this).updateName();
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
	public void addTopics(Collection<Topic> topics) {
		config().addTopics(topics);
	}

	public boolean isRegistered() {
		return SETS.contains(this);
	}
	
	/** Registers this {@link ProblemSet} so that it becomes {@link #isRegistered() registered} and will be in the
	 * {@link Set} returned by {@link #allSets()}.
	 * @throws IllegalStateException if this {@link ProblemSet} is already {@link #isRegistered() registered}.*/
	public void register() {
		if(isRegistered())
			throw new IllegalStateException("This ProblemSet is already registered");
		SETS.add(this);
		runOnRegisterActions(this);
	}
	
	public void saveToFile() {
		saveToFile(name());
	}
	
	public void saveToFile(String oldName) {
		System.out.printf("saving %s to file, oldName=%s, name()=%s%n", this, oldName, name());
		File f = new File(Main.SETS_FOLDER, String.format("%s.dat", oldName));
		try {
			if(!name().equals(oldName)) {
				if(f.exists()) {
					//rename the file to its new name:
					Path path = f.toPath();
					Path p = Files.move(path, path.resolveSibling(String.format("%s.dat", name())));
					f = p.toFile();
				}
				else {
					//nothing to do except change the file object to one with the new name:
					f = new File(Main.SETS_FOLDER, String.format("%s.dat", name()));
				}
			}
			if(!f.exists())
				f.createNewFile();
			IO.writeObject(f, this);
		}
		catch(Exception e) {
			throw new RuntimeException(e); //TODO better error handling?
		}
	}
	
	
}
