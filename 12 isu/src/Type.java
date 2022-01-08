import java.util.ArrayList;

//type class
public class Type {

	String type1;
	String type2;
	ArrayList<Type> weakAgainst = new ArrayList<Type>();
	ArrayList<Type> strongAgainst = new ArrayList<Type>();

	public Type(String type1, String type2) {
		this.type1 = type1;
		this.type2 = type2;
	}

	public Type (String type) {
		this.type1 = type;
	}

	public String toString () {
		if (this.type2 == null || this.type2.isBlank() )
			return this.type1;

		return String.format("%s & %s", this.type1, this.type2);
	}


}
