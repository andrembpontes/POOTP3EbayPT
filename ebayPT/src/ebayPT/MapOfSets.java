package ebayPT;

import java.util.Map;
import java.util.Set;

/**
 * Helper type used to facilitate the use of maps of sets.
 * 
 * @author author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface MapOfSets<K, E> extends Map<K, Set<E>> {
	
	/**
	 * @param key: key to use in map
	 * @param value: Value to associate to map key
	 */
	public void putElemt(K key, E value);
	
	/**
	 * @param key:  key what value are associated
	 * @param value: value do be removed
	 * @return: Removed value
	 */
	public E removeElemt(K key, E value);
}
