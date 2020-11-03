//Mortadha 2011
import javax.swing.UIManager;

public class Main {
    public static void main(String args[]) {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	}
	catch (Exception e)
	{}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExplorerTree().setVisible(true);
                //new Start().setVisible(true);
            }
        });
  }
}
