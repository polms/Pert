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
    private transient ProjetTableModel model;

    public Projet(String nom) {
        this.nom = nom;
        this.mesTaches = new ArrayList<>();
        this.etapesGraph=new ArrayList<>();
        this.model = new ProjetTableModel(this);
        this.nbTache = 0;
        this.nbEtape=0;
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
            if (t1.precede(t)) {
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

    public int getNBTache()  {
        return this.nbTache;
    }

    public int getNBEtape()  {
        return this.nbEtape;
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
}
