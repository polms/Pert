package UI;

import T.Projet;
import T.Tache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouvelleTacheUI extends JFrame {
    private Projet p;
    private JTextArea description;
    private JSpinner temp;

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

        JLabel l_text = new JLabel("Ici vous pourrez crée une nouvelle tache:");
        panel.add(l_text);
        JLabel l_descr = new JLabel("Description: ");
        panel.add(l_descr);
        this.description = new JTextArea(3,10);
        this.description.setSize(150,150);
        panel.add(new JScrollPane(this.description));
        JLabel l_temp = new JLabel("Temp: ");
        panel.add(l_temp);
        this.temp = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE, 1));
        panel.add(this.temp);
        JButton valide = new JButton("Valide");
        valide.addActionListener(new Ecouteur());
        panel.add(valide);
        this.setContentPane(panel);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setTemp(int temp) {
        this.temp.setValue(temp);
    }

    private class Ecouteur implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) { // a revoir
            Tache t = new Tache(p, description.getText(), (int)temp.getValue());
            p.getModel().fireTableDataChanged();
        }
    }
}
