package ebayPT;

import java.util.Map;
import java.util.Set;

/**
 * Helper type used to facilitate the use of maps of sets.
 * 
 * @author author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface MapOfSets<K, E> extends Map<K, Set<E>> {
	public void putElemt(K key, E value);
	public E removeElemt(K key, E value);
}
