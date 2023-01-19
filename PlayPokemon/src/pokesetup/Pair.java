package pokesetup;

public class Pair <Integer, Move> 
implements Comparable
{

	//pair class that is speciifclaly used to hold moves keyed by the level that you can get them
	//the reason this couldn't have been a map is that for some pokemon, there are multiples moves you can get at a single level
	//additionally, many pokemon store extra moves at level 1
	
	private Integer i1;
	private Move m1;
	
	public Pair (Integer curLevel, Move curMove) {
		this.i1 = curLevel;
		this.m1 = curMove;
	}
	
	public String toString() {
		if (this.m1 == null)
			return "";
		return String.format("%d,%s",i1, m1.toString());
	}

	@Override
	public int compareTo(Object o) {
		Pair<Integer,Move> p = (Pair<Integer,Move>) o;
		return (int)p.getInt() - (int)this.i1;
	}
	
	 public Integer getInt() {
		 return this.i1;
	 }
	 public Move getMove() {
		 return this.m1;
	 }

}
