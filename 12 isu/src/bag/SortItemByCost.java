package bag;

import java.util.Comparator;

public class SortItemByCost implements Comparator<Item> 
{

	public int compare(Item o1, Item o2) 
	{
		return o2.getCost()-o1.getCost();
	}

}
