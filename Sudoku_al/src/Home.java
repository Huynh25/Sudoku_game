
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tran Cong Chinh - CE171816
 */
public class Home extends javax.swing.JFrame {

    JLabel lb;
    public SudokuGUI S;

    /**
     * Creates new form Home
     */
    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 980);
        setLayout(new GridLayout(5, 1, 1, 1));
        lb = new JLabel("Home");
        lb.setHorizontalAlignment(JLabel.CENTER);
        add(lb);
        lb.setFont(new Font("Courier", Font.BOLD, 60));
        lb.setForeground(Color.red);
        //New game
        JButton btNewGame = new JButton("New game");
        btNewGame.setPreferredSize(new Dimension(50, 10));
        btNewGame.addActionListener((ActionEvent e) -> {
            S = new SudokuGUI();
            S.setSuperHome(this);
            this.setVisible(false);
        });
        add(btNewGame);
        btNewGame.setFont(new Font("Courier", Font.BOLD, 60));
        //howtoplay
        JButton btHowToPlay = new JButton("How To Play");
        btHowToPlay.setPreferredSize(new Dimension(50, 10));
        btHowToPlay.addActionListener((ActionEvent e) -> {
            HowToPlay h = new HowToPlay();
            h.setVisible(true);
            this.setVisible(false);
        });
        add(btHowToPlay);
        btHowToPlay.setFont(new Font("Courier", Font.BOLD, 60));
        //about us
        JButton btAboutUS = new JButton("About US");
        btAboutUS.setPreferredSize(new Dimension(50, 10));
        btAboutUS.addActionListener((ActionEvent e) -> {
            AboutUs au = new AboutUs();
            au.setVisible(true);
            this.setVisible(false);
            //about Us
//            this.setVisible(false);
        });
        add(btAboutUS);
        btAboutUS.setFont(new Font("Courier", Font.BOLD, 60));
        //exit
        JButton btExit = new JButton("Exit");
        btExit.setPreferredSize(new Dimension(50, 10));

        btExit.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(rootPane, "Thank you for using our program !!");
            System.exit(0);
        });
        add(btExit);
        btExit.setFont(new Font("Courier", Font.BOLD, 60));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    public void run() {
        this.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
