//Mortadha 2011
import com.sun.awt.AWTUtilities;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ExplorerTree extends javax.swing.JFrame {

    private JFileChooser chooser;
    private File file;
    private boolean cert = false;

    public ExplorerTree() {
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle(".:: Certificat (Certificate) X.509 ::.");
        ImageIcon monIcone = new ImageIcon(getClass().getResource("/ico.png"));
        setIconImage(monIcone.getImage());
        javax.swing.Timer time = new javax.swing.Timer(1000, new ClockListener());
        time.start();
        initComponents();
        infotxt.setText("SE (OS) : " + System.getProperty("os.name")
                + " | Version : " + System.getProperty("os.version")
                + " | Architecture : " + System.getProperty("os.arch")
                + " | Utilisateur (User) : " + System.getProperty("user.name"));

    }

    public void valueChanged(String fl) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        file = new File(fl);
        String inf = "Droit (Right): " + DroitFichier(file) + "\n";
        inf += DroitFichier1(file);
        inf += "\nNom (Name): \n   " + file.getName() + "\n";
        inf += "\nTaille (Size): \n   " + TailleFichier(file);
        inf += "\nModifié le (Last modified): \n   " + dateFormat.format(file.lastModified()) + "\n   ";
        inf += dateFormat1.format(file.lastModified()) + "\n   ";
        infoarea.setText(inf);
    }

    public String TailleFichier(File f) {
        DecimalFormat df = new DecimalFormat("#,###.##");
        float a = (float) file.length();
        String taille = "";
        if (a >= 1073741824) {
            taille += df.format(((a / 1024) / 1024) / 1024) + " Go\n   " + df.format((a / 1024) / 1024) + " Mo\n   " + df.format(a / 1024) + " Ko\n   " + df.format(a) + " octets\n   ";
        } else {
            taille += "";
        }
        if ((a >= 1048576) && (a < 1073741824)) {
            taille += df.format((a / 1024) / 1024) + " Mo\n   " + df.format(a / 1024) + " Ko\n   " + df.format(a) + " octets\n   ";
        } else {
            taille += "";
        }
        if ((a >= 1024) && (a < 1048576)) {
            taille += df.format(a / 1024) + " Ko\n   " + df.format(a) + " octets\n   ";
        } else {
            taille += "";
        }
        if ((a >= 8) && (a < 1024)) {
            taille += df.format(a) + " octets\n   ";
        } else {
            taille += "";
        }
        if ((a == 0) && (a < 8)) {
            taille += "0 octets\n   ";
        } else {
            taille += "";
        }
        return taille;
    }

    public String DroitFichier(File f) {
        String droit = "[";
        if (f.isDirectory()) {
            droit += "d";
        } else {
            droit += "-";
        }
        if (f.canRead()) {
            droit += "r";
        } else {
            droit += "-";
        }
        if (f.canWrite()) {
            droit += "w";
        } else {
            droit += "-";
        }
        if (f.canExecute()) {
            droit += "x";
        } else {
            droit += "-";
        }
        droit += "]";
        return droit;
    }

    public String DroitFichier1(File f) {
        String droit = "";
        if (f.isDirectory()) {
            droit += "   Dossier";
        } else {
            droit += "   Fichier";
        }
        if (f.isHidden()) {
            droit += " (Caché)\n";
        } else {
            droit += "\n";
        }
        if (f.canRead()) {
            droit += "   Lecture\n";
        } else {
            droit += "";
        }
        if (f.canWrite()) {
            droit += "   Écriture\n";
        } else {
            droit += "";
        }
        if (f.canExecute()) {
            droit += "   Exécution\n";
        } else {
            droit += "";
        }
        return droit;
    }

    public void Ecriretxt(String args) throws IOException, CertificateException {
        try {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setApproveButtonText("Enregistrer (Save) '*.Txt'");
            chooser.setDialogTitle("Exporter (Export)");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                launchtxt.setText(chooser.getSelectedFile().getAbsolutePath() + ".Txt");
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                FileInputStream in = new FileInputStream(args);
                java.security.cert.Certificate c = cf.generateCertificate(in);
                in.close();
                String s = c.toString();
                FileOutputStream fout = new FileOutputStream(launchtxt.getText());
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fout));
                out.write(s, 0, s.length());
                out.close();
                launch.setEnabled(true);
                launch.setText("Lancer (Txt)");
                JOptionPane.showMessageDialog(this, "Fichier Txt créé avec succes", "Attention", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Fichier Txt erroné", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cert(String args) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(args);
        java.security.cert.Certificate c = cf.generateCertificate(in);
        in.close();
        X509Certificate t = (X509Certificate) c;
        jTextField1.setText(t.getSerialNumber().toString(16));
        jTextField2.setText("" + t.getVersion());
        jTextField7.setText("" + t.getIssuerDN());
        jTextField5.setText(dateFormat.format(t.getNotBefore()) + " (" + t.getNotBefore() + ")");
        jTextField4.setText(dateFormat.format(t.getNotAfter()) + " (" + t.getNotAfter() + ")");
        jTextField3.setText("" + t.getSigAlgName());
        byte[] sig = t.getSignature();
        jTextArea1.setText("" + new BigInteger(sig).toString(16));
        PublicKey pk = t.getPublicKey();
        byte[] pkenc = pk.getEncoded();
        for (int i = 0; i < pkenc.length; i++) {
            jTextArea2.append("" + pkenc[i] + ",");
        }
    }

    public void Parcourir() throws Exception {
        try {
            if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
                AWTUtilities.setWindowOpacity(this, (60) / 100.0f);
            }
        } catch (Exception e) {
        }
        infoarea.setText(" ");
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Ouvrir (Open)");
        chooser.setApproveButtonText("Ouvrir (Open)");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String ext = chooser.getSelectedFile().getAbsolutePath();
            if (ext.substring(ext.indexOf("."), ext.length()).equals(".cer")) {
                chemintxt.setText(chooser.getSelectedFile().getAbsolutePath());
                file = new File(chooser.getSelectedFile().getAbsolutePath());
                valueChanged(chemintxt.getText());
                cert(chemintxt.getText());
                cerlanch.setEnabled(true);
                cerlanch.setText("Lancer le Certificat (Launch the Certificate)  \" "+file.getName()+"\"");
                cert = true;
            } else {
                JOptionPane.showMessageDialog(this, "Format invalide", "Attention", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class ClockListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");
            heure.setText(df.format(Calendar.getInstance().getTime()));
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        arbonom = new javax.swing.JLabel();
        chemin = new javax.swing.JLabel();
        chemintxt = new javax.swing.JTextField();
        parcour = new javax.swing.JButton();
        heure = new javax.swing.JLabel();
        launch = new javax.swing.JButton();
        cerlanch = new javax.swing.JButton();
        launchtxt = new javax.swing.JTextField();
        export = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        infoarea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        infotxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        chemin1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(900, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel.setMinimumSize(new java.awt.Dimension(900, 500));
        jPanel.setName(""); // NOI18N
        jPanel.setPreferredSize(new java.awt.Dimension(836, 380));

        arbonom.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        arbonom.setForeground(new java.awt.Color(153, 0, 0));
        arbonom.setHorizontalAlignment(JLabel.CENTER);
        arbonom.setText(".:: Certificat (Certificate) X.509 ::.");
        arbonom.setEnabled(false);

        chemin.setText("Chemin (Path)");

        chemintxt.setEditable(false);
        chemintxt.setBackground(new java.awt.Color(255, 255, 255));
        chemintxt.setForeground(new java.awt.Color(70, 144, 60));
        chemintxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        chemintxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                chemintxtMousePressed(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chemintxtMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chemintxtMouseEntered(evt);
            }
        });

        parcour.setText("...");
        parcour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourActionPerformed(evt);
            }
        });

        heure.setHorizontalAlignment(JLabel.RIGHT);
        heure.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        launch.setText("Lancer (Launch)");
        launch.setEnabled(false);
        launch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchActionPerformed(evt);
            }
        });

        cerlanch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/certif.png"))); // NOI18N
        cerlanch.setText("Lancer le Certificat (Launch the Certificate)");
        cerlanch.setEnabled(false);
        cerlanch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerlanchActionPerformed(evt);
            }
        });

        launchtxt.setEditable(false);
        launchtxt.setBackground(new java.awt.Color(255, 255, 255));
        launchtxt.setForeground(new java.awt.Color(70, 144, 60));

        export.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Exporter (Export) TXT" }));
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        infoarea.setEditable(false);
        infoarea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        infoarea.setForeground(new java.awt.Color(70, 144, 60));
        infoarea.setLineWrap(true);
        infoarea.setTabSize(0);
        infoarea.setText(" ");
        jScrollPane2.setViewportView(infoarea);

        jLabel1.setText("Propriété (Property)");

        infotxt.setEditable(false);
        infotxt.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        infotxt.setForeground(new java.awt.Color(255, 0, 0));
        infotxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        infotxt.setBorder(null);
        infotxt.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Numéro de série (Serial Number)");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Version (Version)");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Algorithme de signature du certificat (Algorithm ID)");

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setHorizontalAlignment(JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Validité (Validity) :");

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setHorizontalAlignment(JTextField.CENTER);

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.setHorizontalAlignment(JTextField.CENTER);

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel6.setText("          Pas après (Not After)");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel7.setText("          Pas avant (Not Before)");

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(255, 255, 255));
        jTextField7.setHorizontalAlignment(JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Autorité de certification (Issuer)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Signature de Certificat (Certificate Signature)");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Clé publique (Public Key)                                        ");

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        chemin1.setText("Parcourir (Browse)");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cerlanch, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                            .addComponent(jLabel5)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)))
                            .addComponent(launchtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                            .addComponent(infotxt, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(chemin, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(arbonom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                                    .addComponent(chemintxt, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE))))
                        .addGap(19, 19, 19)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(chemin1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(parcour)
                                    .addComponent(heure, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(launch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                    .addComponent(export, javax.swing.GroupLayout.Alignment.TRAILING, 0, 176, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(chemin1)
                        .addComponent(parcour))
                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(chemin)
                        .addComponent(chemintxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(heure, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arbonom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(launchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(launch))
                .addGap(11, 11, 11)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(export, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cerlanch)
                .addGap(21, 21, 21))
        );

        jPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jTextField1});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parcourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourActionPerformed
        try {
            Parcourir();
        } catch (Exception ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_parcourActionPerformed

    private void chemintxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chemintxtMousePressed
        try {
            Parcourir();
        } catch (Exception ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_chemintxtMousePressed

    private void chemintxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chemintxtMouseEntered
        chemintxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 0), null));
    }//GEN-LAST:event_chemintxtMouseEntered

    private void chemintxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chemintxtMouseExited
        chemintxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));
    }//GEN-LAST:event_chemintxtMouseExited

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try {
            if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
                AWTUtilities.setWindowOpacity(this, (100) / 100.0f);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowActivated

    private void cerlanchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerlanchActionPerformed
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    file = new File(chemintxt.getText());
                    desktop.open(file);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.IllegalArgumentException ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cerlanchActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        if (export.getSelectedIndex() == 0 && cert) {
            try {
                if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
                    AWTUtilities.setWindowOpacity(this, (60) / 100.0f);
                }
            } catch (Exception e) {
            }
            try {
                this.setOpacity(60 / 100.0f);
            } catch (Exception e) {
            }
            try {
                try {

                    Ecriretxt(chemintxt.getText());
                } catch (CertificateException ex) {
                    Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Fichier Txt erroné", "Erreur", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Certificat X.509 introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
        }


}//GEN-LAST:event_exportActionPerformed

    private void launchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchActionPerformed
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    file = new File(launchtxt.getText());
                    desktop.open(file);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.IllegalArgumentException ex) {
            Logger.getLogger(ExplorerTree.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_launchActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arbonom;
    private javax.swing.JButton cerlanch;
    private javax.swing.JLabel chemin;
    private javax.swing.JLabel chemin1;
    private javax.swing.JTextField chemintxt;
    private javax.swing.JComboBox export;
    private javax.swing.JLabel heure;
    private javax.swing.JTextArea infoarea;
    private javax.swing.JTextField infotxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JButton launch;
    private javax.swing.JTextField launchtxt;
    private javax.swing.JButton parcour;
    // End of variables declaration//GEN-END:variables
}
