import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


public class explorer extends JFrame {
        private JButton parcour,b2,b3,b4;
        JPanel p1,p2,p3;
        private Container c=null;
	private JTree arbre;
	private DefaultMutableTreeNode racine;
	private  DefaultTreeCellRenderer tCellRenderer = new  DefaultTreeCellRenderer();

	public explorer(){
		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(".:: Explorer ::.");
                this.setResizable(false);
                ImageIcon img=new ImageIcon("pictures\\acceuil.jpg");
                JLabel imgl=new JLabel(img);
                JLabel chemin=new JLabel("Chemin/Path");
                JTextField chemintxt=new JTextField(50);
                p1=new JPanel();
                p2=new JPanel();
                p3=new JPanel();
                parcour=new JButton("...");
                b2=new JButton("          .:: Chat ::.          ");
                b3=new JButton("  .:: Gestion des Services ::.  ");
                b4=new JButton(".:: Quitter ::.");
                p1.setBackground(Color.WHITE);
                p2.setBackground(Color.WHITE);
                p3.setBackground(Color.WHITE);
                c=this.getContentPane();
                c.setLayout(new GridLayout(1,1));
                //c.setLayout(new BorderLayout());
                c.add(p1);
                //c.add(p2);
                //c.add(p3,BorderLayout.SOUTH);
                //p1.add(imgl);
                p1.add(chemin);
                p1.add(chemintxt);
                p1.add(parcour);
                
                //b1.addActionListener(this);
                //b2.addActionListener(this);
                //b3.addActionListener(this);
                //b4.addActionListener(this);
		initRenderer();
		listRoot();
		this.setVisible(true);
	}

	private void initRenderer(){
		this.tCellRenderer = new  DefaultTreeCellRenderer();
		this.tCellRenderer.setClosedIcon(new ImageIcon("img/folder-apps.png"));
		this.tCellRenderer.setOpenIcon(new ImageIcon("img/folder-apps.png"));
		this.tCellRenderer.setLeafIcon(new ImageIcon("img/listreader.png"));
	}

	private void listRoot(){
		this.racine = new DefaultMutableTreeNode();
		for(File file : File.listRoots())
		{
			DefaultMutableTreeNode lecteur = new DefaultMutableTreeNode(file.getAbsolutePath());
			try {
				for(File nom : file.listFiles()){
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nom.getName()+"\\");
					lecteur.add(this.listFile(nom, node));
				}
			} catch (NullPointerException e){}
			this.racine.add(lecteur);
		}
		arbre = new JTree(this.racine);
		arbre.setRootVisible(false);
                arbre.setCellRenderer(this.tCellRenderer);
                JScrollPane j= new JScrollPane(arbre);
                
                p1.add(j);
		
                //this.getContentPane().add(j);
	}

	private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node){
		int count = 0;
		if(file.isFile()){
			return new DefaultMutableTreeNode(file.getName());
                }
		else{
			for(File nom : file.listFiles()){
				count++;
				if(count < 5){
					DefaultMutableTreeNode subNode;
					if(nom.isDirectory()){
						subNode = new DefaultMutableTreeNode(nom.getName()+"\\");
						node.add(this.listFile(nom, subNode));
					}else{
						subNode = new DefaultMutableTreeNode(nom.getName());
					}
					node.add(subNode);
				}
			}
			return node;
		}
	}

	public static void main(String[] args){
		  new explorer();
	}
}