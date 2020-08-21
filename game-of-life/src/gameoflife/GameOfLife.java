package gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private ContentPanel content;
    private GridPanel body;
    private JLabel gen, alive;
    private JButton toggle, reset;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
        setSize(625, 715);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);

        gen = new JLabel();
        gen.setName("GenerationLabel");
        gen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        alive = new JLabel();
        alive.setName("AliveLabel");
        alive.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        add(gen);
        add(alive);
        add(Box.createRigidArea(new Dimension(10, 10)));
        body = new GridPanel();
        add(body);
        setVisible(true);
        pack();
    }

    public void setText(String label, String s) {
        if (label.equals("GenerationLabel")) {
            gen.setText(s);
        }
        else if (label.equals("AliveLabel")) {
            alive.setText(s);
        }
    }

    public void updateBody(int[][] state) {
        body.updateState(state);
    }
}

class GridPanel extends JPanel {
    private int[][] state;

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(j*10, i*10, 10, 10);
                if (state[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j*10, i*10, 10, 10);
                }
            }
        }
    }

    public void updateState(int[][] st) {
        state = st;
        repaint();
    }
}

class ContentPanel extends JPanel {
}