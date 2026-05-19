import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe1 extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private boolean playerXTurn = true;
    private JLabel statusLabel;
    private JButton resetButton;

    public TicTacToe1() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 480);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Title / status label
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        // Game grid
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 50);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(btnFont);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            gridPanel.add(buttons[i]);
        }
        add(gridPanel, BorderLayout.CENTER);

        // Reset button
        resetButton = new JButton("Restart Game");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 18));
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        // Ignore if already marked
        if (!clicked.getText().equals("")) return;

        // Mark X or O
        clicked.setText(playerXTurn ? "X" : "O");
        clicked.setForeground(playerXTurn ? Color.RED : Color.BLUE);

        // Check win/draw conditions
        if (checkWin()) {
            String winner = playerXTurn ? "Player X" : "Player O";
            statusLabel.setText(winner + " Wins!");
            disableButtons();
            JOptionPane.showMessageDialog(this, winner + " Wins the Game!");
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
            JOptionPane.showMessageDialog(this, "It's a Draw!");
        } else {
            playerXTurn = !playerXTurn;
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWin() {
        int[][] combos = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, // cols
            {0,4,8},{2,4,6}          // diagonals
        };

        for (int[] combo : combos) {
            String b1 = buttons[combo[0]].getText();
            String b2 = buttons[combo[1]].getText();
            String b3 = buttons[combo[2]].getText();
            if (!b1.equals("") && b1.equals(b2) && b2.equals(b3)) {
                highlight(combo);
                return true;
            }
        }
        return false;
    }

    private void highlight(int[] combo) {
        for (int i : combo) {
            buttons[i].setBackground(Color.YELLOW);
        }
    }

    private boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setEnabled(true);
            b.setBackground(null);
        }
        playerXTurn = true;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe1::new);
    }
}