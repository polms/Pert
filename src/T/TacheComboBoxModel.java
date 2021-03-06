package T;

import javax.swing.*;

class TacheComboBoxModel extends AbstractListModel implements ComboBoxModel {
    private Tache selection = null;
    private Tache t;

    public TacheComboBoxModel(Tache t) {
        this.t = t;
    }

    public Object getElementAt(int index) {
        return this.t.predecesseursModifiable().get(index);
    }

    public int getSize() {
        return this.t.predecesseursModifiable().size();
    }

    public void setSelectedItem(Object anItem) {
        selection = (Tache) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}