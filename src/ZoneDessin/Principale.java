package ZoneDessin;
import javax.swing.SwingUtilities;

public class Principale {

	public static void main(String [] args) {
        Runnable gui =  new Runnable() {

            @Override
            public void run() {
                Fenetre f = new Fenetre();
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);

            }
        };
        
        SwingUtilities.invokeLater(gui);
    }

}
