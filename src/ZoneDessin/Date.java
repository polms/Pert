package ZoneDessin;
//moi non plus
public class Date {
private int j;

public Date(int j) {
	super();
	this.j = j;
}

@Override
public String toString() {
	return "" + j + "";
}

public int getJ() {
	return j;
}

public void setJ(int j) {
	this.j = j;
}
}
