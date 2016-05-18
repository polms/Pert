package T;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("SpellCheckingInspection")
public class ProjetTableModel extends AbstractTableModel {
    private Projet p;
    private String[] noms = {"Id", "Descr", "t", "Pre"};

    public ProjetTableModel(Projet p) {
        this.p = p;
    }

    @Override
    public String getColumnName(int colums) {
        return noms[colums]; // trouver pourquoi ça marche pas
    }

    @Override
    public int getRowCount() {
        return this.p.getTaches().length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tache t = this.p.getTaches()[rowIndex];
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
                o = t.getPredecesseurs();
                break;
            default:
                o = "index du getVelueAt invalide";
        }
        return o;
    }
}
