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
    private ProjetTableModel model;
    private JTable table;

    public Principale() {
        this.p = new Projet();
        this.setTitle("Fenetre principale");
        this.setSize(400,400);
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
        JButton save = new JButton("Sauvegarder");
        save.addActionListener(new EcouteurSave());
        JButton load = new JButton("Charger");
        load.addActionListener(new EcouteurLoad());

        panel.add(addTache);
        panel.add(this.table);
        panel.add(save);
        panel.add(load);
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
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(p);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public class EcouteurLoad implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("G:\\");
            if (fileChooser.showOpenDialog(Principale.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file)) ;
                    p = (Projet) ois.readObject();
                    p.setModel(new ProjetTableModel(p));
                    table.setModel(p.getModel());
                    p.getModel().fireTableDataChanged();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public class EcouteurListe extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                System.out.println(p.getTaches()[table.getSelectedRow()].getId());
                NouvelleTacheUI t = new NouvelleTacheUI(p);
                t.setDescription(p.getTaches()[table.getSelectedRow()].getDescription()); // crée un constructeur
                t.setTemp(""+p.getTaches()[table.getSelectedRow()].getDuree());
            }
        }
    }

    public static void main(String[] argv) {
        Principale p = new Principale();
        p.setVisible(true);
    }
}
