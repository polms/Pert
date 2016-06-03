package T;

import ZoneDessin.Etape;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashSet;

@SuppressWarnings("SpellCheckingInspection")
public class Tache implements Serializable {
    private static final long serialVersionUID = 5350274506539072796L;
    private String id;
    private String description;
    private int duree;
    private boolean estCheminCritique;
    private boolean estInverse;
    private HashSet<Tache> mesPredecesseurs;
    private Etape avant;
    private Etape apres;
    private Projet p;

    public Tache(Projet p, String description, int duree) {
        this.p = p;
        p.addTaches(this);
        this.id = intToLetters(p.getNBTache());
        this.description = description;
        this.duree = duree;
        this.mesPredecesseurs = new HashSet<>();
        this.estCheminCritique = false;
        this.estInverse = false;
    }

    public boolean addPredecesseur(Tache t) {
        boolean ajouter = false;
        if (! this.equals(t) && ! t.estPrecedecesseur(this)) { //eviter les boucle
            for (Tache tc: this.p.getTaches()) { // ajout des contrainte aux successeurs de this
                if ( ! (this.equals(tc) || tc.getPredecesseurs().isEmpty()) && tc.estPrecedecesseur(this)) { //eviter les boucle + opti
                    if (tc.getPredecesseurs().containsAll(this.getPredecesseurs()) && tc.getPredecesseurs().contains(this)) {
                        tc.getPredecesseurs().add(t);
                    }
                }
            }
            ajouter = this.mesPredecesseurs.addAll(t.getPredecesseurs());
            ajouter = (ajouter || t.getPredecesseurs().isEmpty()) && this.mesPredecesseurs.add(t);
        }
        return ajouter;
    }

    public void removePredecesseur(Tache t) {
        boolean annuler = false;

        if (! this.equals(t)) {
            for (int i = 0; i < this.p.getNBTache(); i++) {
                Tache cur = this.p.getTaches()[i];
                HashSet<Tache> cur_pre = this.p.getTaches()[i].getPredecesseurs();

                if (!cur_pre.isEmpty()) { // important pour l'intersection a suivre
                    if (cur_pre.contains(this)) { // cas ou
                        int reponse = JOptionPane.showConfirmDialog(null, "<html>Voulez vous que la supression de " + t + " de la liste des prédecesseurs de " + this + " antraine la supresion de " + t + " comme prédecesseur dans <b style=\"color: red\">" + cur + "</b> ?</html>", "Confirmation de suppression", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (reponse == JOptionPane.OK_OPTION) {
                            cur.removePredecesseurUnsafe(t);
                        } else if (reponse == JOptionPane.CANCEL_OPTION) {
                            annuler = true;
                            break; // TODO: 31/05/16 trouver une meilleur option
                        }
                    }
                    // verifier  que le predecesseur n'est pas imposer par un autre prédecesseur
                    if (this.getPredecesseurs().contains(cur) // un autre prédecesseur inclu ?
                            && ! this.equals(cur) && ! t.equals(cur) // esque c'est moi celui que l'on veut retirer ?
                            && this.getPredecesseurs().containsAll(cur_pre)) {
                        int reponse = JOptionPane.showConfirmDialog(null, "Voulez vous que la supression de " + t + " dans la liste des prédecesseurs de "+this+" entraine la perte de " + cur + " comme predecesseur de "+this+" ?", "Confirmation de supression", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (reponse == JOptionPane.YES_OPTION) {
                            this.removePredecesseurUnsafe(cur);
                        } else if (reponse == JOptionPane.CANCEL_OPTION) {
                            annuler = true;
                            break; // TODO: 31/05/16 trouver une façon plus propre 
                        }
                    }
                }
            }
            if (!annuler) {
                this.mesPredecesseurs.remove(t);
            }
        }
    }

    private void removePredecesseurUnsafe(Tache t) {
        this.mesPredecesseurs.remove(t);
    }

    public int nbPredecesseur()
    {
        return this.mesPredecesseurs.size();
    }

    public String getId() {
        return id;
    }

    public Projet getProjet() {
        return this.p;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        //this.p.getModel().fireTableDataChanged();
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
        //this.p.getModel().fireTableDataChanged();
    }

    public HashSet<Tache> getPredecesseurs() {
        return mesPredecesseurs;
    }

    public void setAvant(Etape avant) {
        this.avant = avant;
    }

    public Etape getApres() {
        return apres;
    }

    public void setApres(Etape apres) {
        this.apres = apres;
    }

    public void setEstCheminCritique(boolean estCheminCritique) {
        this.estCheminCritique = estCheminCritique;
    }

    public boolean isEstCheminCritique() {
        return this.estCheminCritique;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Tache o) {
        return (this.p == o.getProjet()) && (this.id.equals(o.getId()));
    }

    public boolean estPrecedecesseur(Tache o) {
        return (this.p == o.getProjet()) && (this.mesPredecesseurs.contains(o));
    }

    public boolean precede(Tache t) {
        return t.getPredecesseurs().contains(this);
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
