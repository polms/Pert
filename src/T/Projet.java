package T;

import ZoneDessin.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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
        if (! t.getPredecesseurs().isEmpty()) {

        }
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
}
