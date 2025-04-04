import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlopeVisualizerSet extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int DOT_SIZE = 10;
    private static final int LINE_CNT = 10;
    static ArrayList<int[]> lines;
    static Dot dot1;
    static Dot dot2;
    static boolean placeDot1;
    static int clicks;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SlopeVisualizerSet visualizer = new SlopeVisualizerSet();
            visualizer.setVisible(true);
        });
    }

    public SlopeVisualizerSet() {
        lines = new ArrayList<>();
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
        if (clicks == LINE_CNT) {
            for (int[] line: lines) {
                g2d.drawLine(line[0], line[1], line[2], line[3]);
            }
            removeMouseListener(getMouseListeners()[0]);
        } else {
            g2d.fillOval(dot1.x - (DOT_SIZE / 2), dot1.y - (DOT_SIZE / 2), DOT_SIZE, DOT_SIZE);
            g2d.fillOval(dot2.x - (DOT_SIZE / 2), dot2.y - (DOT_SIZE / 2), DOT_SIZE, DOT_SIZE);
            if (clicks >= 2) {
                double slope = ((double) dot2.y - (double) dot1.y) / ((double) dot2.x - (double) dot1.x);
                double yIntercept = -1 * (dot1.x * slope) + dot1.y;
                int[] line = {0, (int) yIntercept, 1000, (int) (1000 * slope + yIntercept)};
                g2d.drawLine(line[0], line[1], line[2], line[3]);
                lines.add(line);
                System.out.println("Dot 1: (" + dot1.x + ", " + dot1.y + "), Dot 2: (" + dot2.x + ", " + dot2.y + ")");
                System.out.println("Y = " + slope + "x + " + yIntercept + " - Clicks: " + clicks);
            }
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
