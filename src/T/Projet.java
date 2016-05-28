package T;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Projet implements Serializable {
    private static final long serialVersionUID = -3621411098598590248L;
    private ArrayList<Tache> mesTaches;
    private String nom;
    private int nbTache;
    private transient ProjetTableModel model;

    public Projet(String nom) {
        this.nom = "Projet sans nom";
        this.mesTaches = new ArrayList<>();
        this.model = new ProjetTableModel(this);
        this.nbTache = 0;
    }

    public void addTaches(Tache t) {
        if (t != null && ! this.mesTaches.contains(t)) {
            this.mesTaches.add(t);
            this.nbTache++;
        }
    }

    public void supprimeTache(Tache t) {
        if (! t.getPredecesseurs().isEmpty()) {

        }
    }

    public  String getNom() {
        return this.nom;
    }

    public Tache[] getTaches() {
        return  this.mesTaches.toArray(new Tache[this.mesTaches.size()]);
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
}
