
public class Pair <K, V>{
	
	K o1;
	V o2;
	
	public Pair (K o1, V o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	
	public String toString() {
		return String.format("%s,%s",o1.toString(), o2.toString());
	}

}
