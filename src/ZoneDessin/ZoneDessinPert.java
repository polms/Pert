package ZoneDessin;
import javax.swing.*;

import T.Projet;
import T.Tache;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;
import java.util.HashMap;


public class ZoneDessinPert extends JPanel implements MouseListener,MouseMotionListener
	{
	private Projet p;
	boolean dragging = false;


	
	private static final long serialVersionUID = -7216378807599404149L;
	
	
		
		public ZoneDessinPert(Projet p)
			{
			super();
			this.p=p;
			addMouseListener(this);
			addMouseMotionListener(this);
			setLayout(new BorderLayout());
	        setBorder(BorderFactory.createLineBorder(Color.black));
			
			}
		
		
		
		  @Override
		    public Dimension getPreferredSize() {
			  return new Dimension(800,500 );
		    }
		protected void paintComponent(final Graphics g) {
			
			super.paintComponent(g);
			int n=0;
			
			for (Tache t : p.getTaches())
			{
					paintEtape(p.getEtape()[n],g);			
	        		paintTache(t,g,p.getEtape()[n].pointAvant(),p.getEtape()[n+1].pointApres()) ;		
				n++;
			}
		}
	public void paintEtape(Etape etape, Graphics g)
	{
			
	    	g.setColor(etape.getC());
			g.fillOval(etape.getX(), etape.getY(), 100, 100);
			g.setColor(Color.BLACK);
			g.drawOval(etape.getX(),etape.getY(),100,100);
			if (((etape.getC().getRed()+etape.getC().getGreen()+etape.getC().getBlue())/3)>128)
			{
				g.setColor(Color.BLACK);
			}
			else 
			{
				g.setColor(Color.WHITE);
			}
			
			g.drawString(etape.getId(), etape.getX()+50, etape.getY()+30);
			g.drawLine(etape.getX(), etape.getY()+50, etape.getX()+100, etape.getY()+50);
			g.drawLine(etape.getX()+50, etape.getY()+50, etape.getX()+50, etape.getY()+100);
			g.drawString(etape.getDateplusTot().toString()+"            "+etape.getDateplusTard().toString(), etape.getX()+25, etape.getY()+80);
			
			
			}
	public void paintTache(Tache tache, Graphics g,Point etapePrecedente,Point etapeSuivante)
	{
			Color c=Color.black;
			if (tache.isEstCheminCritique())
			{
			c=Color.red;
			}
			
	    	g.setColor(c);
	    	g.drawString(tache.getDescription(), etapePrecedente.x+10, +etapeSuivante.y+10); 
	        ((Graphics2D) g).draw(new Line2D.Double(etapePrecedente, etapeSuivante));
	       
	        drawArrowHead((Graphics2D) g,etapeSuivante , etapePrecedente, c);
	  
			
			
			
			}
	   private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color)
	    {
	        g2.setPaint(color);
	        double dy = tip.y - tail.y;
	        double dx = tip.x - tail.x;
	        double theta = Math.atan2(dy, dx);
	        //System.out.println("theta = " + Math.toDegrees(theta));
	        double x, y, rho = theta + Math.toRadians(40);
	        for(int j = 0; j < 2; j++)
	        {
	            x = tip.x - 20 * Math.cos(rho);
	            y = tip.y - 20 * Math.sin(rho);
	            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
	            rho = theta - Math.toRadians(40);
	        }
	    }
	public void mouseClicked(MouseEvent e) {
		
	//info tache/etape
		 
		 }
	
	public void mousePressed(MouseEvent event) {

		  Point point = event.getPoint();
		  int x = point.x;
		  int y = point.y;
		  for (Etape e : p.getEtape())
			{
			  Double oval=new Double(100,100,e.getX(),e.getY());
			  if (oval.contains(x,y)&&!pointIsAlreadyaCenter(new Point(x-50,y-50)))
			  {
				  e.setX(x-50);
				  e.setY(y-50);
			  }
			}
  	

		  dragging = true;
	}

		    @Override
		    public void mouseReleased(MouseEvent event) {

		  dragging = false;

		    }

		    @Override
		    public void mouseDragged(MouseEvent event) {

		  Point p = event.getPoint();
		  

		  int x = p.x;

		  int y = p.y;
		  for (Etape e : this.p.getEtape())
			{
			  Double oval=new Double(100,100,e.getX(),e.getY());
			  if (oval.contains(x,y)&&!pointIsAlreadyaCenter(new Point(x-50,y-50)))
			  {
				  e.setX(x-50);
				  e.setY(y-50);
			  }
			}
		  if (dragging) {

		repaint();

		  }
		    }
	public boolean pointIsAlreadyaCenter(Point p)
	{
		boolean result=false;
		for(Etape e : this.p.getEtape())
		{
			if (p.getX()==e.getX()&&p.getY()==e.getY())
			{
				result=true;
			}
		}
		return result;
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
