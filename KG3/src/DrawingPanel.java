import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawingPanel extends JPanel {

    private List<Point> points;
    private int originX;
    private int originY;
    private final int cellSize = 20;

    public DrawingPanel() {
        setBackground(Color.WHITE);
    }

    public void setPoints(List<Point> points) {
        this.points = points;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        originX = getWidth() / 2;
        originY = getHeight() / 2;

        drawGrid(g);
        drawAxes(g);
        drawPoints(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(230, 230, 230));

        for (int x = originX; x < getWidth(); x += cellSize)
            g.drawLine(x, 0, x, getHeight());
        for (int x = originX; x > 0; x -= cellSize)
            g.drawLine(x, 0, x, getHeight());

        for (int y = originY; y < getHeight(); y += cellSize)
            g.drawLine(0, y, getWidth(), y);
        for (int y = originY; y > 0; y -= cellSize)
            g.drawLine(0, y, getWidth(), y);

        g.setColor(Color.GRAY);
        for (int x = -originX; x < getWidth() - originX; x += cellSize) {
            int px = originX + x;
            g.drawString(Integer.toString((px - originX) / cellSize), px + 3, originY + 12);
        }

        for (int y = -originY; y < getHeight() - originY; y += cellSize) {
            int py = originY + y;
            g.drawString(Integer.toString((originY - py) / cellSize), originX + 5, py - 2);
        }
    }

    private void drawAxes(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawLine(0, originY, getWidth(), originY);

        g.drawLine(originX, 0, originX, getHeight());

        g.fillOval(originX - 3, originY - 3, 6, 6);
        g.drawString("(0,0)", originX + 5, originY - 5);
    }

    private void drawPoints(Graphics g) {
        if (points == null) return;

        g.setColor(Color.RED);

        for (Point p : points) {

            int screenX = originX + p.x * cellSize;
            int screenY = originY - p.y * cellSize;

            g.fillRect(screenX - 2, screenY - 2, 4, 4);
        }
    }
}
