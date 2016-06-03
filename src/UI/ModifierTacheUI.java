    package UI;

    import T.Projet;
    import T.ProjetTableModelPredecesseurs;
    import T.Tache;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;

    public class ModifierTacheUI extends JFrame {
        private Projet p;
        private Tache t;
        private JTextArea description;
        private JSpinner temp;
        private JTable table;
        private ProjetTableModelPredecesseurs model;

        public ModifierTacheUI(Projet p, Tache t) {
            this.p = p;
            this.t = t;
            this.setTitle("Modifier tache: "+t.getId());
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setSize(400, 400);
            this.setVisible(true);
            this.windowBuilder();
        }

        private void windowBuilder() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel l_text = new JLabel("Ici vous pourrez modifier une tache:");
            panel.add(l_text);
            JLabel l_descr = new JLabel("Description: ");
            panel.add(l_descr);
            this.description = new JTextArea(3,10);
            this.setDescription(this.t.getDescription());
            this.description.setSize(150,150);
            panel.add(new JScrollPane(this.description));
            JLabel l_temp = new JLabel("Temp: ");
            panel.add(l_temp);
            this.temp = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE, 1));
            this.setTemp(this.t.getDuree());
            panel.add(this.temp);

            this.model = this.p.getModel(this.t);
            this.table = new JTable(model);
            this.table.addMouseListener(new EcouteurListe());
            panel.add(new JScrollPane(this.table));

            JButton valide = new JButton("Valider");
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


        public class EcouteurListe extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (t.estPrecedecesseur(p.getTaches()[table.getSelectedRow()])) { // si la tache estPrecedecesseur: SUPPRIMER
                        t.removePredecesseur(p.getTaches()[table.getSelectedRow()]);
                        model.fireTableDataChanged();
                    } else { // si la tache ne estPrecedecesseur pas: AJOUTER
                        t.addPredecesseur(p.getTaches()[table.getSelectedRow()]);
                        model.fireTableDataChanged();
                    }
                    p.getModel().fireTableDataChanged(); // temporaire en attende d'un systeme d'aperçu
                }
            }
        }

        public class Ecouteur implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) { // a revoir

            }
        }
    }
