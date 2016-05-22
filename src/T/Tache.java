package T;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

@SuppressWarnings("SpellCheckingInspection")
public class Tache implements Serializable {
    private static final long serialVersionUID = 5350274506539072796L;
    private String id;
    private String description;
    private int duree;
    private boolean estCheminCritique;
    private HashSet<Tache> mesPredecesseurs;
    private Projet p;

    public Tache(Projet p, String description, int duree) {
        this.p = p;
        p.addTaches(this);
        this.id = intToLetters(p.getNBTache());
        this.description = description;
        this.duree = duree;
        this.mesPredecesseurs = new HashSet<>();
    }

    public void addPredecesseur(Tache t) {
        if (! this.equals(t)) {
            for (int i = 0; i < this.p.getNBTache(); i++) {
                if (this.p.getTaches()[i].getPredecesseurs().contains(this)) {
                    this.p.getTaches()[i].addPredecesseurUnsafe(t); // Unsafe pour eviter la récursivite
                }
            }
            this.mesPredecesseurs.addAll(t.getPredecesseurs());
            this.mesPredecesseurs.add(t);
        }
    }

    private void addPredecesseurUnsafe(Tache t) {
        this.mesPredecesseurs.add(t);
    }

    public String getId() {
        return id;
    }

    public Projet getProjet(){
        return this.p;
    }

    public String getDescription() {
        return description;
    }

    public int getDuree() {
        return duree;
    }

    public HashSet<Tache> getPredecesseurs() {
        return mesPredecesseurs;
    }

    public boolean equals(Tache o) {
        return (this.p == o.getProjet()) && (this.id.equals(o.getId()));
    }

    public boolean precede(Tache o) {
        return (this.p == o.getProjet()) && (this.mesPredecesseurs.contains(o));
    }

    public String toString() {
        return this.id;
    }

    public static String intToLetters(int value) {
        String result = "";
        while (--value >= 0) {
            result = (char) ('A' + value % 26) + result;
            value /= 26;
        }
        return result;
    }
}
