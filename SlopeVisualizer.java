import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlopeVisualizer extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    static Dot dot1;
    static Dot dot2;
    static boolean placeDot1;
    static int clicks;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SlopeVisualizer visualizer = new SlopeVisualizer();
            visualizer.setVisible(true);
        });
    }

    public SlopeVisualizer() {
        placeDot1 = true;
        clicks = 0;
        dot1 = new Dot(-1, -1);
        dot2 = new Dot(-1, -1);
        setTitle("Slope Visualizer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clicks++;
                System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
                if (placeDot1) {
                    dot1.x = e.getX();
                    dot1.y = e.getY();
                    repaint();
                    placeDot1 = false;
                } else {
                    dot2.x = e.getX();
                    dot2.y = e.getY();
                    repaint();
                    placeDot1 = true;
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillOval(dot1.x, dot1.y, 10, 10);
        g2d.fillOval(dot2.x, dot2.y, 10, 10);
        if (clicks >= 2) {
            g2d.drawLine(dot1.x, dot1.y, dot2.x, dot2.y);
        }
    }

    private static class Dot {
        int x, y;

        Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
