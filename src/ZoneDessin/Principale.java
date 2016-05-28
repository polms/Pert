package ZoneDessin;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import T.Projet;

public class Principale {

	public static void main(String [] args) {
        Runnable gui =  new Runnable() {

            @Override
            public void run() {
                Fenetre f = new Fenetre();
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);

            }
        };
        
        SwingUtilities.invokeLater(gui);
    }

}
