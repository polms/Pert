package ZoneDessin;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

import T.Projet;

public class Etape implements Serializable {
	Projet p;
	private int datePlusTot;
	private int datePlusTard;
	public int getDatePlusTot() {
		return datePlusTot;
	}
	public void setDatePlusTot(int datePlusTot) {
	
		this.datePlusTot = datePlusTot;
		
	}
	public int getDatePlusTard() {
		return datePlusTard;
	}
	public void setDatePlusTard(int datePlusTard) {
		if (datePlusTard>this.datePlusTard)
		{
		this.datePlusTard = datePlusTard;
		}
	}
	private String id;
	private Color c;
	private int x;
	private int y;
	
	public Point pointAvant()
	{
		return new Point(x+100,y+50);
	}
	public Point pointApres()
	{
		return new Point(x,y+50);
	}
	public Etape(Projet p,int x,int y) {
		super();
		this.p=p;
		p.addEtape(this);
		this.id = intToLetters(p.getNbEtape());
		this.c=Color.WHITE;
		this.datePlusTard=0;
		this.datePlusTot=0;
		this.x=x;
		this.y=y;
	}

	public boolean ContientPoint(Point p)
	{
		boolean result=false;
		
		if((p.getX()>=this.x&&p.getX()<=this.x+100)&&(p.getY()>=this.y&&p.getY()<=this.y+100))
			{
				
				result=true;
			}
		
		return result;
	}

public int getX() {
	return x;
}

public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}

public void setY(int y) {
	this.y = y;
}

	public Color getC() {
		return c;
	}
	public void setC(Color c) {
		this.c = c;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
    public static String intToLetters(int value) {
        String result = "";
        while (--value >= 0) {
            result = (char) ('A' + value % 26) + result;
            value /= 26;
        }
        return result;
    }
	
}