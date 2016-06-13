package ZoneDessin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;
import T.*;



public class  Fenetre extends JFrame {
	
	private static final long serialVersionUID = 8164118974463460991L;


	public Fenetre(){
		super();
		setTitle("FaitDesPert"); //On donne un titre � l'application
		setSize(1200,1024); //On donne une taille � notre fen�tre
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
  		p.genererEtape();
  		//on dessine le pert sur le diagramme voir : paintComponent(final Graphics g) (dans le package ZoneDessin/ZoneDessinPert.java)
  		ZoneDessinPert diagramme= new ZoneDessinPert(p);
  		diagramme.setLayout(new BorderLayout());
  		diagramme.setSize(new Dimension(500,500));
  		//on l'ajoute dans la fenetre
        this.getContentPane().add(diagramme, BorderLayout.CENTER);
		
		
		
		
	}
}
