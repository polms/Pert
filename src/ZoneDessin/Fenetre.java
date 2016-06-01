package ZoneDessin;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import T.*;



public class  Fenetre extends JFrame {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 8164118974463460991L;

	public Fenetre(){
		super();
		setTitle("FaitDesP�rt"); //On donne un titre � l'application
		setSize(1024,720); //On donne une taille � notre fen�tre
		setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		setResizable(true); //On interdit la redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit � l'application de se fermer lors du clic sur la croix
		construire_fenetre();//On initialise notre fen�tre                
		
		
		
	}
 
	private void construire_fenetre(){
  		Projet p = new Projet("P1");
  		Tache t1=new Tache (p,"manger",20);
  		Tache t2=new Tache (p,"fumer",5);
  		Tache t3=new Tache (p,"dormir",30);
  		Tache t4=new Tache (p,"se reveiller",30);
  		
  		t3.addPredecesseur(t2);
  		t2.addPredecesseur(t4);
  		t1.addPredecesseur(t4);
  		t3.addPredecesseur(t4);
  		t3.addPredecesseur(t1);
  		
  		int tabTpC[]=new int[p.getNBTache()];// tableau de tache par colonne exemple :tabTpC[1]=5 il y a 5 tache dans la colonne 1
  		//on initialise toute ses valeurs � 0
  		for (int j=0;j<p.getNBTache();j++)
  		{
  			tabTpC[j]=0;
  		}
  		//on parcours toutes les cases du tableau
  		for (int j=0;j<p.getNBTache();j++)
  		{
  			for (Tache t:p.getTaches())//on parcours toutes les taches
  	  		{
  	  			
  	  			if (t.nbPredecesseur()==j)//si sont nombre de predescecceur correspond � la colonne du tableau 
  	  			{
  	  				tabTpC[j]++;//on l'incremente de 1
  	  			}
  	  		}
  			
  			
  		}
  		/*
  		for (int j=0;j<p.getNBTache();j++)
  		{
  			System.out.println("colonne "+j+" contient : "+tabTpC[j]+" taches.");
  		}
  		*/
  		int i=0;
  		for (Tache t:p.getTaches())//pour chaque tache du projet
  		{
  			
  				// on creer 2 etapes
  				p.addEtape(new Etape(p,i*250+50,50));
  	  			p.addEtape(new Etape(p,i*250+300,50));
  			
  			i++;
  		}
  		
  		ZoneDessinPert diagramme= new ZoneDessinPert(p);
  		diagramme.setLayout(new BorderLayout());
  		diagramme.setSize(new Dimension(500,500));
  		
  		
         this.getContentPane().add(diagramme, BorderLayout.CENTER);
		
		
		
		
	}
}
