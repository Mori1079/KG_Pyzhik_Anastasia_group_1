import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BresenhamCircle {

    public static List<Point> build(int xc, int yc, int r) {

        List<Point> points = new ArrayList<>();

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        while (x <= y) {
            points.add(new Point(xc + x, yc + y));
            points.add(new Point(xc - x, yc + y));
            points.add(new Point(xc + x, yc - y));
            points.add(new Point(xc - x, yc - y));
            points.add(new Point(xc + y, yc + x));
            points.add(new Point(xc - y, yc + x));
            points.add(new Point(xc + y, yc - x));
            points.add(new Point(xc - y, yc - x));

            if (d <= 0)
                d += 4 * x + 6;
            else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
        return points;
    }
}
