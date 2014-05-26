package ebayPT;

import java.util.Iterator;
import java.util.List;


public class DoubleIterator<E> implements Iterator<E> {

	private Iterator<List<E>> iteratorsList;
	private Iterator<E> actualIterator;
	
	public DoubleIterator(List<List<E>> lists) {
		
		this.iteratorsList = lists.iterator();

		this.actualIterator = iteratorsList.next().iterator();
	}
	
	@Override
	public boolean hasNext() {
		return this.actualIterator.hasNext() || this.iteratorsList.hasNext();
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
