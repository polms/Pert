package T;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TacheTest {
    @Test
    public void equals() throws Exception {
        Projet p = new Projet("test");
        Projet p2 = new Projet("test 2");
        Tache t1 = new Tache(p, "tache 1", 1);
        Tache t2 = new Tache(p, "tache 2", 1);
        Tache t3 = new Tache(p2, "tache 1", 1);
        Tache t4 = new Tache(p2, "tache 2", 1);
        assertTrue(t1.equals(t1));
        assertFalse(t1.equals(t2));
        assertTrue(t2.equals(t2));
        assertFalse(t2.equals(t1));

        assertFalse(t1.equals(t3));
        assertFalse(t4.equals(t2));

        assertTrue(t3.equals(t3));
        assertFalse(t4.equals(t3));
    }

    @Test
    public void precede() throws Exception {
        boolean marche = true;
        Projet p = new Projet("test");
        Tache t1 = new Tache(p, "tache 1", 1);
        Tache t2 = new Tache(p, "tache 2", 1);
        Tache t3 = new Tache(p, "tache 3", 1);
        Tache t4 = new Tache(p, "tache 4", 1);
        marche = marche && t1.addPredecesseur(t2);
        marche = marche && t1.addPredecesseur(t3);
        marche = marche && t4.addPredecesseur(t1);
        assertTrue("L'ajout de predecesseur ne marche pas", marche);
        assertTrue(t3.precede(t1));
        assertTrue(t2.precede(t1));
        assertTrue(t1.precede(t4));
        assertTrue(t2.precede(t4));
        assertTrue(t3.precede(t4));
        assertFalse(t2.precede(t3));
        assertFalse(t4.precede(t2));
    }

    @Test
    public void addPredecesseur() throws Exception {
        Projet p = new Projet("test");
        Tache t1 = new Tache(p, "tache 1", 1);
        Tache t2 = new Tache(p, "tache 2", 1);
        Tache t3 = new Tache(p, "tache 3", 1);
        Tache t4 = new Tache(p, "tache 4", 1);
        Tache t5 = new Tache(p, "tache 5", 1);
        Tache t6 = new Tache(p, "tache 6", 1);
        assertTrue("on ne peut pas ajouter un predecesseur a une tache",t1.addPredecesseur(t2));
        assertFalse("on peut ajoute un predecesseur a lui meme", t1.addPredecesseur(t1));
        assertFalse("on peut faire une boucle: 1",t2.addPredecesseur(t1));

        t1.addPredecesseur(t3); // t1 = t2, t3
        HashSet<Tache> hs = new HashSet<>();
        hs.add(t2);
        hs.add(t3);
        assertTrue("les predecesseurs ne sont pas bien ajouter",t1.getPredecesseurs().equals(hs));
        assertFalse("on peut faire une boucle: 2", t2.addPredecesseur(t1));
        assertTrue("le boolean n'est pas correct", t1.getPredecesseurs().equals(hs)); // juste au cas ou
        // t1 = t2,t3
        assertTrue("on ne peut pas ajouter un predeceseurs avec des predecesseurs", t4.addPredecesseur(t1));
        hs.clear(); hs.add(t1); hs.add(t2); hs.add(t3);
        //t4 = t1,t2,t3
        assertTrue("les predecesseurs contrainte ne sont pas bien ajouter", t4.getPredecesseurs().equals(hs));

        // t1 = t2,t3
        //t4 = t1,t2,t3

        t2.addPredecesseur(t5);
        // t1 = t2,t3,t5
        // t2 = t5
        // t4 = t1,t2,t3,t5
        hs.clear(); hs.add(t2); hs.add(t3); hs.add(t5);
        assertTrue("les predecesseurs contrainte n+ ne sont pas bien ajouter", t1.getPredecesseurs().equals(hs));
        hs.clear(); hs.add(t1); hs.add(t2); hs.add(t3);hs.add(t5);
        assertTrue("les predecesseurs contrainte n+ ne sont pas bien ajouter", t4.getPredecesseurs().equals(hs));
        t6.addPredecesseur(t5);
        // t1 = t2,t3,t5
        // t2 = t5
        // t4 = t1,t2,t3,t5
        // t6 = t5
        hs.clear(); hs.add(t5);
        assertTrue("les predecesseurs contrainte sont ajouter trop souvent", t6.getPredecesseurs().equals(hs));
//        for (Tache t: p.getTaches()) {
//            System.out.print(t.getId());
//            System.out.println(t.getPredecesseurs());
//        }
    }

    @Test
    public void removePredecesseur() throws Exception {
        assertTrue("test a crée",false);
    }

}