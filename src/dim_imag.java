import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.MediaTracker;

public class dim_imag extends JPanel
{
private String nomFichier;
private Image image;
private Dimension dimImage;
private MediaTracker tracker;

public dim_imag (String nomFichier)
{

this.nomFichier=nomFichier;
image=getToolkit().getImage(nomFichier);
tracker=new MediaTracker(this);
tracker.addImage(image,0);
try{tracker.waitForID(0);} catch (InterruptedException e) {}
dimImage=new Dimension((int)(image.getWidth(this)),(int)(image.getHeight(this)));
System.out.println(dimImage);
setPreferredSize(dimImage);
}

public void paintComponent(Graphics g)
{
super.paintComponent(g);
g.drawImage(image,0,0,this);
}

public Dimension getDimImage()
{
return dimImage;
}

}

class Test extends JFrame
{
dim_imag image;

Test(String s)
{
super("Test de JImage");
this.setSize(555, 555);
this.setContentPane(image=new dim_imag(s));
}

public static void main(String[] argv)
{
Test aff = new Test("print.png");
aff.pack();
aff.setVisible(true);
}
}
