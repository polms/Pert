package UI;

import T.Projet;
import T.Tache;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouvelleTacheUI extends JFrame {
    private Projet p;
    private JTextField description;
    private JTextField temp;

    public NouvelleTacheUI(Projet p) {
        this.p = p;
        this.setTitle("Nouvelle tache");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 400);
        this.setVisible(true);
        this.windowBuilder();
    }

    private void windowBuilder() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        this.description = new JTextField();
        this.temp = new JTextField();
        JButton valide = new JButton("Valide");
        valide.addActionListener(new Ecouteur());

        panel.add(this.description);
        panel.add(this.temp);
        panel.add(valide);
        this.setContentPane(panel);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setTemp(String temp) {
        this.temp.setText(temp);
    }

    private class Ecouteur implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) { // a revoir
            Tache t = new Tache(p, description.getText(), Integer.parseInt(temp.getText()));
            p.getModel().fireTableDataChanged();
        }
    }
}
