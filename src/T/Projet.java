package T;

import ZoneDessin.*;

import java.awt.print.PrinterException;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Projet implements Serializable {
    private static final long serialVersionUID = -3621411098598590248L;
    private ZoneDessinPert lePert;
    private ArrayList<Tache> mesTaches;
    private ArrayList <Etape> etapesGraph;
    private String nom;
    private int nbTache;
    private int nbEtape;
    private int espacementEtape;
    private transient ProjetTableModel model;

    public Projet(String nom) {
        this.nom = nom;
        this.mesTaches = new ArrayList<>();
        this.etapesGraph=new ArrayList<>();
        this.model = new ProjetTableModel(this);
        this.nbTache = 0;
        this.nbEtape=0;
        this.espacementEtape=250;
    }

    public int getEspacementEtape() {
		return espacementEtape;
	}

	public void setEspacementEtape(int espacementEtape) {
		this.espacementEtape = espacementEtape;
	}
	public void incEspacementEtape() {
		this.espacementEtape++;
	}
	public void decEspacementEtape() {
		this.espacementEtape--;
	}

	@Deprecated // cette fonction vas devenir privée
    public void addTaches(Tache t) {
        if (t != null && ! this.mesTaches.contains(t)) {
            this.mesTaches.add(t);
            this.nbTache++;
        }
    }

    public void supprimeTache(Tache t) {
        for (Tache t1:this.getTaches()) {
            if (t1.estPrecedecesseur(t)) {
                t1.removePredecesseur(t);
                System.out.println("supprimeTache<"+this+">: suprimer "+t+" de "+t1);
            }
        }
        this.mesTaches.remove(t);
        this.nbTache--;
        this.model.fireTableDataChanged();
    }

    public void addEtape(Etape e) {
        if (e != null && ! this.etapesGraph.contains(e)) {
            this.etapesGraph.add(e);
            this.nbEtape++;
        }
    }

    public void supprimeEtape(Etape e) {
        for (Etape et:this.etapesGraph)
        {
            if (e.getId().equals(et.getId()))
            {
                this.etapesGraph.remove(et);
                this.nbEtape--;
            }
        }
    }

    public void saveToFile(File f) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(this);
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e1.getLocalizedMessage(),
                    "Erreur d'écriture",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void print() throws PrinterException {
        JTable jt = new JTable(this.getModel());
        jt.print();
    }

    public  String getNom() {
        return this.nom;
    }

    public Tache[] getTaches() {
        return  this.mesTaches.toArray(new Tache[this.mesTaches.size()]);
    }
    public ArrayList<Tache> getListTaches() {
        return  this.mesTaches;
    }

    public int getNbEtape() {
		return nbEtape;
	}

	public void setNbEtape(int nbEtape) {
		this.nbEtape = nbEtape;
	}

	public int getNbTache() {
		return nbTache;
	}

	public void setNbTache(int nbTache) {
		this.nbTache = nbTache;
	}

	public Etape[] getEtape() {
        return  this.etapesGraph.toArray(new Etape[this.etapesGraph.size()]);
    }

    public void setModel(ProjetTableModel m) {
        this.model = m;
    }

    public ProjetTableModel getModel() {
        return model;
    }

    public ProjetTableModelPredecesseurs getModel(Tache t) {
        return new ProjetTableModelPredecesseurs(this,t);
    }


   

    public static Projet loadFromFile(File f) {
        Projet np = null;
        try {
            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(f)) ;
            np = (Projet) ois.readObject();
            np.setModel(new ProjetTableModel(np));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getLocalizedMessage(),
                    "Erreur de lecture",
                    JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return np;
    }
    
    public int[] getTPC()
	{
    	//on va donc calculer le nombre de tache contenu dans chaque colonne:
    			int tabTpC[]=new int[this.getNbTache()];// tableau de tache par colonne exemple :tabTpC[1]=5 il y a 5 tache dans la colonne 1
    	  		//on initialise toute ses valeurs � 0
    	  		for (int j=0;j<this.getNbTache();j++)
    	  		{
    	  			tabTpC[j]=0;
    	  		}
    	  		//on parcours toutes les cases du tableau
    	  		for (int j=0;j<=this.getNbTache();j++)
    	  		{
    	  			
    	  			for (Tache t:this.getTaches())//on parcours toutes les taches
    	  	  		{
    	  	  			
    	  	  			if (t.nbPredecesseur()==j)//si sont nombre de predescecceur correspond � la colonne du tableau 
    	  	  			{
    	  	  				tabTpC[j]++;//on l'incremente de 1
    	  	  			}
    	  	  		}
    	  			
    	  			
    	  		}
    	  		return tabTpC;
	
	}
    public void genererEtape()
	{
    	int[] tabTpC = this.getTPC();
    
  		for (int j=0;j<this.getNbTache();j++)
  		{
  			
  			if(tabTpC[j]!=0)
  			{
  				Etape e=new Etape(this,j*this.espacementEtape+50,50);
  				this.addEtape(e);
  			}
  		}
  		Etape etapeFinal=new Etape(this,this.getNbTache()*this.espacementEtape+50,50);
  		this.addEtape(etapeFinal);
  		
	}
    
}
