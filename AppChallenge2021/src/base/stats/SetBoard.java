package base.stats;

import java.util.*;

import base.sets.ProblemSet;
import fxutils.PolarizedPane;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

final class SetBoard extends VBox {
	
	private static final class Listing extends PolarizedPane {
		
		private static final String FONT_NAME = "Georgia";
		
		private final ProblemSet set;
		
		Listing(ProblemSet set) {
			super(new Label(set.name()), new Label(String.valueOf(set.practiceCount())));
			this.set = set;
			setFontSize(MIN_FONT);
		}
		
		ProblemSet set() {
			return set;
		}
		
		void setFontSize(double size) {
			setFontSizeInternal(Math.max(Math.min(size, MAX_FONT), MIN_FONT));
		}
		
		void setFontSizeInternal(double size) {
			leftLabel().setFont(Font.font(FONT_NAME, size));
			rightLabel().setFont(Font.font(FONT_NAME, size));
		}
		
		void updateStats() {
			rightLabel().setText(String.valueOf(set.practiceCount()));
		}
		
		private Label leftLabel() {
			return (Label) left();
		}
		
		private Label rightLabel() {
			return (Label) right();
		}
		
	}
	
	private static final String SET_BOARD_CSS = "set-board";
	private static final int MIN_FONT = 18, MAX_FONT = 48;
	
	private final Map<ProblemSet, Listing> listingMap;
	
	SetBoard() {
		listingMap = new HashMap<>();
		initPaneMapAndAddChildren();
		getStyleClass().add(SET_BOARD_CSS);
		sortListings();
		resize();
	}
	
	private void initPaneMapAndAddChildren() {
		for(ProblemSet set : ProblemSet.all()) {
			Listing listing = createPaneFor(set);
			listingMap.put(set, listing);
			getChildren().add(listing);
		}
	}
	
	private PolarizedPane getPaneFor(ProblemSet set) {
		if(!listingMap.containsKey(set))
			listingMap.put(set, createPaneFor(set));
		return listingMap.get(set);
	}
	
	private Listing createPaneFor(ProblemSet set) {
		return new Listing(set);
	}
	
	void updateStats() {
		for(Listing l : listingMap.values())
			l.updateStats();
		resize();
	}
	
	void setAdded(ProblemSet set) {
		getChildren().add(getPaneFor(set));
		sortListings();
		resize();
	}

	private void sortListings() {
		FXCollections.sort(getChildren(), Comparator.comparingInt(p -> ((Listing) p).set().practiceCount()).reversed());
	}
	
	void setRemoved(ProblemSet set) {
		boolean removed = getChildren().remove(getPaneFor(set));
		assert removed;
		listingMap.remove(set);
		resize();
	}
	
	private void resize() {
		if(getChildren().isEmpty())
			return;
		double maxPractices = maxPractices();
		for(Node n : getChildren())
			if(n instanceof Listing) {
				Listing l = (Listing) n;
				double prop = maxPractices == 0 ? 0 : l.set().practiceCount() / maxPractices;
				l.setFontSize((double) MIN_FONT + (MAX_FONT - MIN_FONT) * prop);
			}
	}
	
	private int maxPractices() {
		return listingMap.keySet().stream().mapToInt(ProblemSet::practiceCount).max().getAsInt();
	}
	
}
