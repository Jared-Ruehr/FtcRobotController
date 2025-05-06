import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class GridPanel extends JPanel {
    private final int cellSize = 4;
    private final int height = 250;
    private final int width = 500;
    private final Set<Point> clickedCells = new HashSet<>();
    private Timer mouseTimer;
    private boolean mouseHeld = true;
    public boolean timerRunning = false;


    public GridPanel() {
        mouseTimer = new Timer(100, e -> {
            if (mouseHeld) {
                Point mousePosition = getMousePosition();
                //System.out.println("Held");
                dropSand(getMousePosition());
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                //System.out.println("dragged");
                Point clickedPoint = e.getPoint();
                dropSand(clickedPoint);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                //System.out.println("pressed");
                mouseHeld = true;
                mouseTimer.start();
                Point clickedPoint = e.getPoint();
                dropSand(clickedPoint);
            }
            public void mouseReleased(MouseEvent e){
                //System.out.println("released");
                mouseHeld = false;
                mouseTimer.stop();
            }
        });
    }
    private void dropSand(Point e) {
        int gridX = e.x / cellSize;
        int gridY = e.y / cellSize;

        // Store clicked cell positions
        clickedCells.add(new Point(gridX, gridY));

        if (!timerRunning) {
            timerRunning = true;
            new Timer(50, t -> {
                Set<Point> updatedCells = new HashSet<>();
                for (Point p : clickedCells) {
                    Point cellBelow = new Point(p.x, p.y + 1);
                    if (p.y < height / cellSize - 1 && !clickedCells.contains(cellBelow)) { //need to check if there is a point below it. if so, stack
                        updatedCells.add(new Point(p.x , p.y + 1));
                    } else {
                        updatedCells.add(new Point(p.x, p.y));
                    }
                }
                clickedCells.clear();
                clickedCells.addAll(updatedCells);
                repaint();
            }).start();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        int cols = width / cellSize;
        int rows = height / cellSize;
        g.setColor(Color.BLACK);

        for (int i = 0; i <= cols; i++) {
            g.drawLine(i * cellSize, 0,  i * cellSize, height);
        }

        for (int i = 0; i <= rows; i++) {
            g.drawLine(0, i * cellSize, width, i * cellSize);
        }

        // Draw clicked cells
        g.setColor(Color.BLUE);
        for (Point p : clickedCells) {
            if (p.x<width/cellSize && p.y<height/cellSize) {
                g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Click");
        GridPanel grid = new GridPanel();
        frame.add(grid);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

