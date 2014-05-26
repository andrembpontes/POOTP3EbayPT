package ebayPT;

import java.util.Map;
import java.util.Set;

//TODO Comment
public interface MapOfSets<K, E> extends Map<K, Set<E>> {
	public void putElemt(K key, E value);
}
