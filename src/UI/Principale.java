package UI;

import T.Projet;
import T.ProjetTableModel;
import T.Tache;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.*;

public class Principale extends JFrame {
    private Projet p;
    private JTable table;
    private JPopupMenu popupMenu;

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

        popupMenu = new JPopupMenu();
        JMenuItem popupMenuItemAdd = new JMenuItem("Supprimer");
        popupMenuItemAdd.addActionListener(new EcouteurDelete());
        popupMenu.add(popupMenuItemAdd);

        JButton addTache = new JButton("Ajouter une tâche");
        addTache.addActionListener(new Ecouteur());
        this.table = new JTable(this.p.getModel()) {
            //  Determine editor to be used by row
            public TableCellEditor getCellEditor(int row, int column)
            {
                if (column == 3) {
                    JComboBox cb = new JComboBox(((ProjetTableModel)this.getModel()).getTacheAt(row).getModel_predecesseurs());
                    cb.addActionListener(
                            e -> {
                                p.getTaches()[table.getSelectedRow()].addPredecesseur((Tache)cb.getSelectedItem());
                            }
                    );
                    return new DefaultCellEditor(cb);
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };
        this.table.addMouseListener(new EcouteurListe());
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setComponentPopupMenu(this.popupMenu);
        JScrollPane sp = new JScrollPane(this.table);
        JButton save = new JButton("Sauvegarder");
        save.addActionListener(new EcouteurSave());
        JButton load = new JButton("Charger");
        load.addActionListener(new EcouteurLoad());
        JButton print = new JButton("Imprimer");
        print.addActionListener(new EcouteurPrint());
        JButton delete = new JButton("Supprimer une tâche");
        delete.addActionListener(new EcouteurDelete());

        panel.add(addTache);
        panel.add(save);
        panel.add(load);
        panel.add(print);
        panel.add(delete);
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

    public class EcouteurPrint implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                p.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class EcouteurDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                p.supprimeTache(p.getTaches()[table.getSelectedRow()]);
            } else {
                // griser bouton supprimer
            }
        }
    }

    public class EcouteurListe extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isPopupTrigger()) { // pour ouvrir le popup sur la ligne du clic
                System.out.println("test");
                Point p = e.getPoint();
                int rowNumber = table.rowAtPoint( p );
                ListSelectionModel model = table.getSelectionModel();
                model.setSelectionInterval( rowNumber, rowNumber );
            }

            if (e.getClickCount() == 2) {
                new ModifierTacheUI(p,p.getTaches()[table.getSelectedRow()]);
            } else if (e.getClickCount() == 3) { //TODO: a changer
                p.supprimeTache(p.getTaches()[table.getSelectedRow()]);
            }
        }
    }

    public static void main(String[] argv) throws IOException {
        Principale p = new Principale();
        p.setVisible(true);
    }
}
