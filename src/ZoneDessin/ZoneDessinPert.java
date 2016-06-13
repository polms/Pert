package ZoneDessin;
import javax.swing.*;
import T.Projet;
import T.Tache;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.util.LinkedHashSet;


public class ZoneDessinPert extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener
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
			addMouseWheelListener(this);
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
			//on a	 donc une liste de tache trie dans l'ordre a afficher (listTacheDessine)
			
			//maintenant on paint les etapes generé par le projet
			for (int compteur=0;compteur<p.getNbEtape();compteur++)//on parcours les etapes du projet
	  		{
	  			Etape actuel=p.getEtape()[compteur];//on récupère une etape ajouter dans le projet dans construire fenètre
	  			paintEtape(actuel,g);//on la dessine
	  		}

			//on génére un tableau de tache par colonne :
			int[] tabTpC = p.getTPC();
			
	  		int compteurTache=0;
	  		int tempsEcouler=0;
	  		int tempsEcoulertard=0;
	  		// on parcours chaque colonne 
	  		for (int j=0;j<p.getNbEtape();j++)
	  		{
	  			//System.out.println("colonne "+j+" contient : "+tabTpC[j]+" taches.");
	  			//et pour chaque tache 	
	  				for (int ibis=0;ibis<tabTpC[j];ibis++)
		  			{
	  					
	  					Tache tacheactuel=(Tache) listTacheDessine.toArray()[compteurTache];
	  					if (ibis>0)//si il y a plus d'une tache dans la colonne
	  					{
	  						tacheactuel.setEstFictive(true);//les suivante sont fictive
	  					}
	  					//on défini les etapes relié par la taches
	  					tacheactuel.setAvant(p.getEtape()[j]);
	  					tacheactuel.setApres(p.getEtape()[j+1]);
	  					//calcul des date au plus tard/tot:
	  					if (ibis==0)
	  					{
	  						p.getEtape()[j+1].setDatePlusTot(tempsEcouler+tacheactuel.getDuree());
	  					}
	  					
	  					if (ibis>0)//plusieurs tache dans la colonne
	  					{
	  						
	  						tempsEcouler=tempsEcouler+tacheactuel.getDuree();
	  						//on regarde si le temps obtenu est plus petit que la date au plus tot de l'etape:
	  						if (p.getEtape()[j+1].getDatePlusTot()>tempsEcouler)
	  							{
	  								p.getEtape()[j+1].setDatePlusTot(tempsEcouler);
	  							}
	  					}
	  					//la date au plus tard :
	  					tempsEcoulertard=tempsEcoulertard+tacheactuel.getDuree();
	  					p.getEtape()[j+1].setDatePlusTard(tempsEcoulertard);
	  					
	  					//on la paint
	  					paintTache(tacheactuel,g,tacheactuel.getAvant().pointAvant(),tacheactuel.getApres().pointApres());
	  					compteurTache++;
		  			}
	  		
	  	
	  		
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
			g.drawString(String.valueOf(etape.getDatePlusTot())+"            "+String.valueOf(etape.getDatePlusTard()), etape.getX()+25, etape.getY()+80);
			
			
			}
	public LinkedHashSet<Tache> getTacheDansLordre(Projet p)
	{
		LinkedHashSet<Tache> listTacheDessine = new LinkedHashSet<Tache>();
		int i=0;//
		//initilisation 
		//on ajoute les taches initiales:
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
			// si on a parcouru toute les taches mais pas tout trié :
			if (i==p.getTaches().length&&listTacheDessine.size()!=p.getTaches().length)
			{
				i=0;// on parcours à nouveau la liste des tache
			}
			
			// si la tache t n'est pas initial, et que  la liste des taches a trié contient tout les predecesseur de t
			if (p.getTaches()[i].nbPredecesseur()!=0&&listTacheDessine.containsAll(p.getTaches()[i].getPredecesseurs()))
			{
				// on ajoute la tache dans la liste trié
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
	    	if (tache.isEstFictive())
		    	{
		    		c=Color.green;
		    		g.setColor(c);
		    		Point milieuEtape=new Point(((etapePrecedente.x+etapeSuivante.x)/2),((etapePrecedente.y+etapeSuivante.y)/2)+50);
		    	 	g.drawString(tache.getDescription(), ((etapePrecedente.x+etapeSuivante.x)/2), (((etapePrecedente.y+etapeSuivante.y)/2)+100)); 
			    	g.drawString(String.valueOf(tache.getDuree()), ((etapePrecedente.x+etapeSuivante.x)/2), (((etapePrecedente.y+etapeSuivante.y)/2)+115)); 
			    	((Graphics2D) g).draw(new Line2D.Double(etapePrecedente, milieuEtape));
			    	((Graphics2D) g).draw(new Line2D.Double(milieuEtape, etapeSuivante));
			    	 drawArrowHead((Graphics2D) g,etapeSuivante , milieuEtape, c);
		    	}
	    	else
		    	{
			    	g.drawString(tache.getDescription(), ((etapePrecedente.x+etapeSuivante.x)/2), (((etapePrecedente.y+etapeSuivante.y)/2)-5)); 
			    	g.drawString(String.valueOf(tache.getDuree()), ((etapePrecedente.x+etapeSuivante.x)/2), (((etapePrecedente.y+etapeSuivante.y)/2)+15)); 
				       
			    	((Graphics2D) g).draw(new Line2D.Double(etapePrecedente, etapeSuivante));
			       
			        drawArrowHead((Graphics2D) g,etapeSuivante , etapePrecedente, c);
		    	}
			}
	   private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color)
	    {
	        g2.setPaint(color);
	        double dy = tip.y - tail.y;
	        double dx = tip.x - tail.x;
	        double theta = Math.atan2(dy, dx);
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
	public void mouseWheelMoved(MouseWheelEvent evt) {
		if (evt.isControlDown()
				&& evt.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			int scrolled = evt.getWheelRotation();
			if (scrolled < 0) {
				for (int s = 0; s > scrolled; --s) {
					//zommer
					p.incEspacementEtape();
					repaint();

				}

			}
			
			
			
			else 
			{
				for (int s = 0; s < scrolled; ++s) {
					//dezommer
					p.decEspacementEtape();
					repaint();
					
				}
			}

		}
	}





	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
