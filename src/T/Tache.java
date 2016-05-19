package T;

import java.io.Serializable;
import java.util.HashSet;

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

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDuree() {
        return duree;
    }

    public String getPredecesseurs() {
        return mesPredecesseurs.toString();
    }

    public static String intToLetters(int value)
    {
        String result = "";
        while (--value >= 0)
        {
            result = (char)('A' + value % 26) + result;
            value /= 26;
        }
        return result;
    }
}
