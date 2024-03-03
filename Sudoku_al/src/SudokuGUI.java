/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokuGUI extends javax.swing.JFrame {

    private JTextField[][] cells;
    private SudokuSolver solver;
    private JComboBox<String> difficultyComboBox;
    JLabel time_lb;
    JLabel sls_lb;
    JLabel score;
    JLabel difficult;
    boolean returnToHome = false;
    int textFieldWidth = 80;
    int textFieldHeight = 80;
    int inputX = -1, inputY = -1;
    int errors;
    private Home superHome;
    private static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    JPanel gridPanel;
    // Các button khi chơi game
    private JButton undoButton;
    private JButton hintButton;
    private JButton pauseButton;
    private JButton Home;
    JPanel numberPanel;
    int difficulty = 45;
    int hintLimit = 5;
    int countTimer = 0;
    int hintLimitOrg = 5;
    int difficultyOrg = 45;

    public SudokuGUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo panel chứa các điều khiển cho kích cỡ và mức độ khó
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        difficultyComboBox = new JComboBox<>(DIFFICULTIES);
        difficultyComboBox.setPreferredSize(new Dimension(120, 30));
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 15));

        startNewGame();
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent difficultySelection) {
                if (difficultyComboBox.getSelectedItem().toString().equals("Easy")) {
                    difficultyOrg = 45;
                    hintLimitOrg = 5;
                } else if (difficultyComboBox.getSelectedItem().toString().equals("Medium")) {
                    difficultyOrg = 48;
                    hintLimitOrg = 4;
                } else {
                    difficultyOrg = 52;
                    hintLimitOrg = 3;
                }
                startNewGame();

            }
        });
        time_lb = new JLabel("0h :0m :0s");
        sls_lb = new JLabel("Can be wrong " + errors + " times");
        score = new JLabel("---SCORE:    ---");
        score.setFont(new Font("Courier", Font.BOLD, 20));
        time_lb.setFont(new Font("Courier", Font.BOLD, 20));
        sls_lb.setFont(new Font("Courier", Font.BOLD, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 1;
        controlPanel.add(score, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        difficult = new JLabel("Difficulty:");
        difficult.setFont(new Font("Courier", Font.BOLD, 20));

        controlPanel.add(difficult, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(difficultyComboBox, gbc);

        gbc.gridx = 4;
        controlPanel.add(time_lb, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(sls_lb, gbc);

        add(controlPanel, BorderLayout.NORTH);

        // Tạo nút Undo
        undoButton = new JButton("Undo");
        undoButton.setFont(new Font("Courier", Font.BOLD, 20));
        undoButton.setPreferredSize(new Dimension(80, 50));
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Coordinates thePopCell = solver.undo();
                if (thePopCell == null) {
                    return;
                }
                int x = thePopCell.x;
                int y = thePopCell.y;
                cells[x][y].setText("");
                solver.grid[x][y] = 0;
            }

        });
        // Tạo nút Hint
        hintButton = new JButton("Hint");
        hintButton.setFont(new Font("Courier", Font.BOLD, 20));
        hintButton.setPreferredSize(new Dimension(80, 50));
        hintButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hintLimit == 0) {
                    JOptionPane.showMessageDialog(rootPane, "You have run out of hints");
                    return;
                }

                Coordinates hintCell = solver.hint();

                if (hintCell == null) {
                    return;
                }

                int x = hintCell.x;
                int y = hintCell.y;
                cells[x][y].setBackground(Color.white);
                cells[x][y].setFont(new Font("Courier", Font.BOLD, 75));
                cells[x][y].setText(" " + hintCell.value);
                cells[x][y].setForeground(Color.BLUE);
                solver.grid[x][y] = hintCell.value;
                solver.checkInput(hintCell.value, x, y);
                --hintLimit;
                JOptionPane.showMessageDialog(rootPane, "You have " + hintLimit + " hints left");
                if (solver.steps.size() == difficulty) {
                    returnToHome = JOptionPane.showConfirmDialog(rootPane, "Congratulations, you win the game\n"
                            + "You got " + solver.highPoint + " point, would you like to play a new game ?") != 0;
                    if (returnToHome) {
                        superHome.S.setVisible(false);
                        superHome.run();
                    } else {
                        startNewGame();
                    }
                }

            }

        });

//        // Tạo nút Pause
//        pauseButton = new JButton("Pause");
//        pauseButton.setPreferredSize(new Dimension(80, 50));
        Home = new JButton("Home");
        Home.setFont(new Font("Courier", Font.BOLD, 20));

        Home.setPreferredSize(new Dimension(100, 50));
        Home.addActionListener((ActionEvent e) -> {
            Home h = new Home();
            h.setVisible(true);
            this.setVisible(false);
        });
        // Tạo panel chứa Undo, Hint, Pause
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sls_lb);
        buttonPanel.add(undoButton);
        buttonPanel.add(hintButton);
//        buttonPanel.add(pauseButton);
        buttonPanel.add(Home);

        controlPanel.add(buttonPanel, gbc);

        setVisible(true);

        pack();

    }
    
    private void resetHint() {
        hintLimit = hintLimitOrg;
    }
    
    private void resetDifficulty() {
        difficulty = difficultyOrg;
    }

    private void startNewGame() {
        resetHint();
        resetDifficulty();
        errors = 3;

        if (cells != null) {
            gridPanel.removeAll();
        }

        cells = new JTextField[9][9];
        gridPanel = new JPanel(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setEditable(false);
                cells[row][col].setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
                gridPanel.add(cells[row][col]);
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                cells[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
                cells[i][j + 2].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
                cells[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                cells[i + 2][j].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
                cells[i][j + 1].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
                cells[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
                cells[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
                cells[i + 1][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
                cells[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        numberPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 1; i < 10; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(new Font("Courier", Font.BOLD, 50));
            numberButton.setPreferredSize(new Dimension(180, 100));
//            numberButton.addActionListener(new NumberButtonListener());
            final int valueButton = i;
            numberButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    int input = valueButton;
                    if (inputX < 0 || inputY < 0) {
                        return;
                    }
                    if (solver.checkInput(input, inputX, inputY)) {
                        cells[inputX][inputY].setBackground(Color.white);
                        cells[inputX][inputY].setFont(new Font("Courier", Font.BOLD, 75));
                        cells[inputX][inputY].setText(" " + input);
                        cells[inputX][inputY].setForeground(Color.BLUE);
                        solver.grid[inputX][inputY] = input;
                        solver.highPoint += 10;

                        if (solver.steps.size() == difficulty) {
                            returnToHome = JOptionPane.showConfirmDialog(rootPane, "Congratulations, you win the game\n"
                                    + "You got " + solver.highPoint + " point, would you like to play a new game ?") != 0;
                        }
                    } else {
                        if (errors > 0) {
                            errors--;
                            sls_lb.setText("Can be wrong " + errors + " times");
                            cells[inputX][inputY].setBackground(Color.red);
                            JOptionPane.showMessageDialog(rootPane, "Oooops, you have typed wrong number, you have " + errors + " wrong times left");
                            cells[inputX][inputY].setBackground(Color.white);
                        } else {
                            returnToHome = JOptionPane.showConfirmDialog(rootPane, "Game over, you lose\nWould you like to play a new game ?") != 0;
                            if (returnToHome) {
                                superHome.S.setVisible(false);
                                superHome.run();
                            } else {
                                startNewGame();
                                
                            }
                        }
                    }
                }

            });
            numberPanel.add(numberButton);
        }
        add(numberPanel, BorderLayout.EAST);

//        String difficultySelection = (String) difficultyComboBox.getSelectedItem();
//        int difficulty = getDifficultyFromSelection(difficultySelection);
        solver = new SudokuSolver(difficulty);
        solver.playGame();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (solver.grid[row][col] == 0) {
                    final int x = row, y = col;
                    cells[row][col].addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (inputX >= 0 && inputY >= 0) {
                                cells[inputX][inputY].setBackground(Color.white);
                            }

                            inputX = x;
                            inputY = y;
                            cells[x][y].setBackground(Color.CYAN);

                        }

                    });
                    continue;
                }

                cells[row][col].setFont(new Font("Courier", Font.BOLD, 75));
                cells[row][col].setText(" " + solver.grid[row][col]);

            }
        }

        countTimer = 0;

        Timer timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                time_lb.setText(countTimer / 3600 + "h :" + countTimer / 60 + "m :" + countTimer % 60 + "s");
                countTimer++;
                score.setText("---SCORE: " + solver.highPoint + " ---");
            }
        });
        timer.start();

        pack();

    }

    public void setSuperHome(Home home) {
        superHome = home;
    }

    private class NumberButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String number = clickedButton.getText();

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (cells[row][col].isFocusOwner()) {
                        cells[row][col].setText(number);
                        return;
                    }
                }
            }
        }
    }

    private class UndoButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Xử lý sự kiện khi nhấn nút Undo
            // ...
        }
    }

    private class HintButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Xử lý sự kiện khi nhấn nút Hint
            // ...
        }
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
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SudokuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SudokuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SudokuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SudokuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SudokuGUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
