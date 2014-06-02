package ebayPT;

import java.util.Comparator;

/**
 * Class used to compare users by total amount earned in sales, and by
 * alphabetical order in the case of ties.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public class ComparatorUserBySales implements Comparator<IUser> {

	@Override
	public int compare(IUser o1, IUser o2) {
		int salesDiff = o2.getSales() - o1.getSales();
		
		if(salesDiff == 0)
			return o1.getUsername().compareTo(o2.getUsername());
		
		return salesDiff;
	}

}
