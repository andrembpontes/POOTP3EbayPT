package ebayPT;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO comment this
public class DoubleIterator<E> implements Iterator<E> {

	private Iterator<Collection<E>> iteratorsList;
	private Iterator<E> actualIterator;
	
	public DoubleIterator(Collection<Collection<E>> lists) {
		
		this.iteratorsList = lists.iterator();
		
		try{
			this.actualIterator = iteratorsList.next().iterator();
		}
		catch(NoSuchElementException e){
			this.actualIterator = null;
		}
	}
	
	@Override
	public boolean hasNext() {
		try{
			return this.actualIterator.hasNext() ||
					this.iteratorsList.hasNext();
		}
		catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public E next() {
		if(actualIterator.hasNext()){
			return actualIterator.next();
		}
		else {
			this.actualIterator = iteratorsList.next().iterator();
			return actualIterator.next();
		}
	}

	@Override
	public void remove() {
		actualIterator.remove();
	}
	
}
