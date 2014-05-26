package ebayPT;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


public class HashMapOfTreeSet<K, E> extends HashMap<K, Set<E>>
	implements MapOfSets<K, E> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	//TODO comment
	
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
}
