package pokesetup;

public class Pair <Integer, Move> 
implements Comparable
{
	
	Integer i1;
	Move m1;
	
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
