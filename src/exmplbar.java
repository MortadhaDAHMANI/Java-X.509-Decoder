import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class exmplbar extends JPanel {
    private Thread progressThread;
    private JProgressBar progressBar;
    private boolean keepRunning;


    public exmplbar() {
        this.keepRunning = true;
        this.progressThread = new ProgressThread();

        this.progressBar = new JProgressBar();
        this.progressBar.setMaximum(100);

        add(this.progressBar);
        System.out.println(keepRunning);
        this.progressThread.start();
    }

    private class ProgressThread extends Thread {
        public void run() {
            int count = 0;
            while (keepRunning) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                }

                final int cval = count;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(cval);
                    }
                });
                count++;


                if (count == 101) {
                    count = 0;
                } else if (count == 50) {
                    final int[] returnValue = new int[1];
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                returnValue[0] = JOptionPane.showConfirmDialog(progressBar, "Would you like to reset progress?", "Prompt", JOptionPane.YES_NO_OPTION);
                            }
                        });
                        if (returnValue[0] == JOptionPane.YES_OPTION) {
                            count = 0;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    public static void main(String[] a){
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(1);
      f.add(new exmplbar());
      f.pack();
      f.setVisible(true);
    }

}