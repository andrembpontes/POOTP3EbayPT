package ebayPT;

import java.util.Comparator;


public class ComparatorUserBySales implements Comparator<IUser> {

	@Override
	public int compare(IUser o1, IUser o2) {
		int salesDiff = o2.getSales() - o1.getSales();
		
		if(salesDiff == 0)
			return o1.getUsername().compareTo(o2.getUsername());
		
		return salesDiff;
	}

}