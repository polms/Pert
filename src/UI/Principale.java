package UI;

import T.Projet;
import T.ProjetTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Principale extends JFrame {
    private Projet p;
    private JTable table;

    public Principale() {
        this.p = new Projet("Projet sans nom");
        this.setTitle(p.getNom()+" - Pert");
        this.setSize(500,500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.windowBuidler();
    }

    public void windowBuidler() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton addTache = new JButton("Ajouter une tache");
        addTache.addActionListener(new Ecouteur());
        this.table = new JTable(this.p.getModel());
        this.table.addMouseListener(new EcouteurListe());
        JScrollPane sp = new JScrollPane(this.table);
        JButton save = new JButton("Sauvegarder");
        save.addActionListener(new EcouteurSave());
        JButton load = new JButton("Charger");
        load.addActionListener(new EcouteurLoad());

        panel.add(addTache);
        panel.add(save);
        panel.add(load);
        panel.add(sp);
        this.setContentPane(panel);
    }

    public class Ecouteur implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new NouvelleTacheUI(p);
        }
    }

    public class EcouteurSave implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("G:\\");
            if (fileChooser.showSaveDialog(Principale.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                p.saveToFile(file);
            }
        }
    }

    public class EcouteurLoad implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("G:\\");
            if (fileChooser.showOpenDialog(Principale.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                p = Projet.loadFromFile(file);
                if (p != null) {
                    table.setModel(p.getModel());
                    p.getModel().fireTableDataChanged();
                }
            }
        }
    }

    public class EcouteurListe extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                new ModifierTacheUI(p,p.getTaches()[table.getSelectedRow()]);
            }
        }
    }

    public static void main(String[] argv) throws IOException {
        Principale p = new Principale();
        p.setVisible(true);
    }
}
