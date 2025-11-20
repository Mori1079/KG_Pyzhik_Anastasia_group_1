import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StepByStep {

    public static List<Point> build(int x1, int y1, int x2, int y2) {
        List<Point> points = new ArrayList<>();

        int dx = x2 - x1;
        int dy = y2 - y1;

        float m = (float) dy / dx;
        float y = y1;

        for (int x = x1; x <= x2; x++) {
            points.add(new Point(x, Math.round(y)));
            y += m;
        }

        return points;
    }
}
