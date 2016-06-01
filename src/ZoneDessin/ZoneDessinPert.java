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
import java.util.Arrays;
import java.util.HashMap;


public class ZoneDessinPert extends JPanel implements MouseListener,MouseMotionListener
	{
	private Projet p;
	boolean dragging = false;
	private Etape EtapeSelectionne;


	
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
			ArrayList listTacheDessine = new ArrayList<Tache>();
			
			//System.out.println("taille tache a dessine :"+p.getTaches().length);
	
			//tant que la liste des tache dessine ne fait pas la taille du tablmeau de tache :
			int i=0;
			//initilisation 
			//on dessine une etape
			paintEtape(p.getEtape()[0],g);
			for (Tache t : p.getTaches() )
			{
				if (t.nbPredecesseur()==0)
				{
					
					//la tache
	        		paintTache(p.getTaches()[i],g,p.getEtape()[0].pointAvant(),p.getEtape()[1].pointApres()) ;
	        		listTacheDessine.add(p.getEtape()[0]);
				}
			}
			while (listTacheDessine.size()== p.getTaches().length)
			{
				//System.out.println("taille tache dessine :"+listTacheDessine.size());
				// si on a parcouru toute les taches:
				if (i>=p.getTaches().length)
				{
					i=0;
				}
				
				// si les predecesseur de la tache t sont dans la liste des tache dessine et que t n'est pas dans cette liste 
				
				if (p.getTaches()[i].nbPredecesseur()!=0&&listTacheDessine.containsAll(p.getTaches()[i].getPredecesseurs())&&!listTacheDessine.contains(p.getTaches()[i]))
				{
					System.out.println("dessin de :"+p.getEtape()[i].getId());
				//on dessine une etape
				paintEtape(p.getEtape()[i],g);
				//la tache
        		paintTache(p.getTaches()[i],g,p.getEtape()[i].pointAvant(),p.getEtape()[i+1].pointApres()) ;
        		listTacheDessine.add(p.getEtape()[i]);
				}
				
				i++;
			}
			System.out.println("SORTIE DE LA BOUCLE");
			
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
	// Quand on presse le clic de la souris :
	public void mousePressed(MouseEvent event) {

		  Point point = event.getPoint();
		  int x = point.x;
		  int y = point.y;
		  for (Etape e : p.getEtape())
			{
			  if (e.ContientPoint(point)&&!pointIsAlreadyaCenter(new Point(x-50,y-50)))
			  {
				 this.EtapeSelectionne=e;
			  }
			}
		  if (this.EtapeSelectionne!=null)
		  {
			  EtapeSelectionne.setX(x-50);
			  EtapeSelectionne.setY(y-50);
		  }
		  dragging = true;
		  repaint();
	}

		    @Override
		    //quand on declic la souris
		    public void mouseReleased(MouseEvent event) {
		   
		  dragging = false;
		  this.EtapeSelectionne=null;

		    }

		    @Override
		    public void mouseDragged(MouseEvent event) {

		  Point p = event.getPoint();
		  int x = p.x;
		  int y = p.y;
		  
		  if (this.EtapeSelectionne!=null)
		  {
			  EtapeSelectionne.setX(x-50);
			  EtapeSelectionne.setY(y-50);
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
