import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RasterFrame extends JFrame {

    private final DrawingPanel drawingPanel = new DrawingPanel();

    public RasterFrame() {
        setTitle("Алгоритмы растеризации");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        JButton stepBtn = new JButton("Step-by-Step");
        JButton ddaBtn = new JButton("DDA");
        JButton brLineBtn = new JButton("Bresenham Line");
        JButton brCircleBtn = new JButton("Bresenham Circle");

        stepBtn.addActionListener(e -> runStepByStep());
        ddaBtn.addActionListener(e -> runDDA());
        brLineBtn.addActionListener(e -> runBresenhamLine());
        brCircleBtn.addActionListener(e -> runCircle());

        buttonPanel.add(stepBtn);
        buttonPanel.add(ddaBtn);
        buttonPanel.add(brLineBtn);
        buttonPanel.add(brCircleBtn);

        add(buttonPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void runStepByStep() {
        int x1 = input("Введите x1:");
        int y1 = input("Введите y1:");
        int x2 = input("Введите x2:");
        int y2 = input("Введите y2:");

        List<Point> points = StepByStep.build(x1, y1, x2, y2);
        drawingPanel.setPoints(points);
    }

    private void runDDA() {
        int x1 = input("Введите x1:");
        int y1 = input("Введите y1:");
        int x2 = input("Введите x2:");
        int y2 = input("Введите y2:");

        List<Point> points = DDA.build(x1, y1, x2, y2);
        drawingPanel.setPoints(points);
    }

    private void runBresenhamLine() {
        int x1 = input("Введите x1:");
        int y1 = input("Введите y1:");
        int x2 = input("Введите x2:");
        int y2 = input("Введите y2:");

        List<Point> points = BresenhamLine.build(x1, y1, x2, y2);
        drawingPanel.setPoints(points);
    }

    private void runCircle() {
        int xc = input("Введите центр X:");
        int yc = input("Введите центр Y:");
        int r = input("Введите радиус:");

        List<Point> points = BresenhamCircle.build(xc, yc, r);
        drawingPanel.setPoints(points);
    }

    private int input(String msg) {
        return Integer.parseInt(JOptionPane.showInputDialog(this, msg));
    }
}
