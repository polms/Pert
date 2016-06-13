package ZoneDessin;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;

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
import java.util.HashSet;
import java.util.LinkedHashSet;


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
			LinkedHashSet<Tache> listTacheDessine = this.getTacheDansLordre(p);
			// ici on a	 donc une liste de tache trie dans l'ordre a afficher (listTacheDessine)
				int nbEtapeAjouter=p.getNBEtape();
	  		// puis la peindre
	  		for (int compteur=0;compteur<nbEtapeAjouter;compteur++)
	  		{
	  			Etape actuel=p.getEtape()[compteur];
	  			paintEtape(actuel,g);//on la dessine
	  		}
	  		//pour chaque atche à dessine :
	  		
	  		for (int i=0;i<listTacheDessine.size();i++)
	  		{
	  			((Tache) listTacheDessine.toArray()[i]).setApres(p.getEtape()[i]);
	  		}
	  		for (int i=0;i<(listTacheDessine.size()-1);i++)
	  		{
	  			
	  			paintTache(((Tache) listTacheDessine.toArray()[i]),g,((Tache) listTacheDessine.toArray()[i]).getApres().pointAvant(),((Tache) listTacheDessine.toArray()[i+1]).getApres().pointApres()) ;
	  		
	  		}
	  		
	  		
	  	
	  		
		}
	public int ajouterEtape(Projet p)
	{
		int tabTpC[]=this.tacheParColonne(p);// tableau de tache par colonne exemple :tabTpC[1]=5 il y a 5 tache dans la colonne 1
  		int nbEtapeAjouter=0;
  		// on parcours chaque colonne pour ajouter les etape dans le projet
  		for (int j=0;j<p.getNBTache();j++)
  		{
  			//System.out.println("colonne "+j+" contient : "+tabTpC[j]+" taches.");
  			//et pour chaque tache dans la colonne
  			if (tabTpC[j]!=0)
  			{
  				for (int ibis=0;ibis<tabTpC[j];ibis++)
	  			{
	  				// j : collonne | tabTpC[j] nb de tache dans la colonne
  					Etape e=new Etape(p,j*250+50,50+200*ibis);
  					e.
	  				p.addEtape(e);// on ajoute une etape au projet 
	  				nbEtapeAjouter++;
	  			}
  			}
  	
  		}
  		// et l'etape final :
  		p.addEtape(new Etape(p,p.getNBTache()*250+50,50));// on ajoute une etape au projet 
  		nbEtapeAjouter++;
  		return nbEtapeAjouter;
	}
	public int[] tacheParColonne(Projet p)
	{
		//on va donc calculer le nombre de tache contenu dans chaque colonne:
		int tabTpC[]=new int[p.getNBTache()];// tableau de tache par colonne exemple :tabTpC[1]=5 il y a 5 tache dans la colonne 1
  		//on initialise toute ses valeurs ï¿½ 0
  		for (int j=0;j<p.getNBTache();j++)
  		{
  			tabTpC[j]=0;
  		}
  		//on parcours toutes les cases du tableau
  		for (int j=0;j<=p.getNBTache();j++)
  		{
  			for (Tache t:p.getTaches())//on parcours toutes les taches
  	  		{
  	  			
  	  			if (t.nbPredecesseur()==j)//si sont nombre de predescecceur correspond ï¿½ la colonne du tableau 
  	  			{
  	  				tabTpC[j]++;//on l'incremente de 1
  	  			}
  	  		}
  			
  			
  		}
  		return tabTpC;
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
	public LinkedHashSet<Tache> getTacheDansLordre(Projet p)
	{
		LinkedHashSet<Tache> listTacheDessine = new LinkedHashSet<Tache>();
		int i=0;//
		//initilisation 
		//on ajoute les première taches
		for (Tache t : p.getTaches() )
		{
			if (t.nbPredecesseur()==0)
			{
				
				//la tache est ajouter à la liste
        		listTacheDessine.add(t);
        		i++;
			}
		}
		
	//	System.out.println(p.getTaches().length+":"+listTacheDessine.size());
		
		//tant que la liste des tache a dessine ne fait pas la taille du tablmeau de tache :
		while (listTacheDessine.size()!=p.getTaches().length)
		{
			// si on a parcouru toute les taches mais pas toute déssiné :
			if (i==p.getTaches().length&&listTacheDessine.size()!=p.getTaches().length)
			{
				i=0;// on parcours à nouveau la liste des tache
			}
			
			// si la tache t n'est pas initial, et que les predecesseurs de la tache t sont dans la liste des taches a dessiné 
			if (p.getTaches()[i].nbPredecesseur()!=0&&listTacheDessine.containsAll(p.getTaches()[i].getPredecesseurs()))
			{
				// on ajoute la tache dans la liste
        		listTacheDessine.add(p.getTaches()[i]);
			}
			i++;//on passe à la tache suivante 	
			
		}
		return listTacheDessine;
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
		  
		  if (dragging&&this.EtapeSelectionne!=null) {
		
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
