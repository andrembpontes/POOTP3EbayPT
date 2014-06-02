package ebayPT;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of MapOfSets using a HashMap of TreeSets
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public class HashMapOfTreeSet<K, E> extends HashMap<K, Set<E>>
	implements MapOfSets<K, E> {

	private static final long	serialVersionUID	= 1L;
	
	@Override
	public void putElemt(K key, E value){
		
		if(this.containsKey(key)){
			this.get(key).add(value);
		
		}
		else{
			Set<E> newSet = new TreeSet<E>();
			newSet.add(value);
			
			this.put(key, newSet);
		}
	}
	
	@Override
	public E removeElemt(K key, E value){
		this.get(key).remove(value);
		
		if(this.get(key).size() == 0)
			this.remove(key);
		
		return value;
	}
}
