package base.stats;

import java.io.Serializable;

public interface ReadOnlyStats extends Serializable {
	
	int total();
	
	default boolean isEmpty() {
		return total() == 0;
	}
	
}
