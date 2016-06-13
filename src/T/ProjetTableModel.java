package T;

import javax.swing.table.AbstractTableModel;
import javax.swing.JOptionPane;

@SuppressWarnings("SpellCheckingInspection")
public class ProjetTableModel extends AbstractTableModel {
    private Projet p;
    private Tache t;
    private String[] noms = {"Identifiant", "Description", "temp", "Prédécesseurs"};

    public ProjetTableModel(Projet p) {
        this.p = p;
    }

    @Override
    public String getColumnName(int colums) {
        return noms[colums];
    }

    @Override
    public int getRowCount() {
        return this.p.getTaches().length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public Tache getTacheAt(int row) {
        return this.p.getTaches()[row];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tache t = getTacheAt(rowIndex);
        Object o;
        switch (columnIndex) {
            case 0:
                o = t.getId();
                break;
            case 1:
                o = t.getDescription();
                break;
            case 2:
                o = t.getDuree();
                break;
            case 3:
                o = t.getPredecesseurs().toString();
                break;
            default:
                o = "index du getVelueAt invalide";
        }
        return o;
    }

    public boolean isCellEditable(int rowIndex,int columnIndex) {
        return true;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws IllegalArgumentException {
        Tache t = this.p.getTaches()[rowIndex];
        switch (columnIndex) {
            case 1:
                t.setDescription((String)aValue);
                break;
            case 2:
                try {
                    t.setDuree(Integer.parseInt((String) aValue));
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                            "La durée ne peut pas contenir autrechose que des nombres",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            default:
                //throw new IllegalArgumentException("La colone n'est pas modifiable: "+columnIndex);
        }
    }
}
