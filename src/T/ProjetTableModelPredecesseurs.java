package T;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ProjetTableModelPredecesseurs extends AbstractTableModel{
    private Projet p;
    private Tache t;
    private String[] noms = {"Id", "Descr", "t", "Pre", "est pre"};

    public ProjetTableModelPredecesseurs(Projet p, Tache t) {
        this.p = p;
        this.t = t;
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
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tache t = this.p.getTaches()[rowIndex];
        Object o = "not";
        if (!this.t.equals(t)) {
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
                case 4:
                    o = "<html><p style=\"color: "+(this.t.precede(t) ? "green" : "red")+"\">"+this.t.precede(t)+"</p></html>";
                    break;
                default:
                    o = "index du getVelueAt invalide";
            }
        }
        return o;
    }
}
