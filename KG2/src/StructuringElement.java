public class StructuringElement {
    private int[][] mask;

    public StructuringElement(int[][] mask) {
        this.mask = mask;
    }

    public int[][] getMask() {
        return mask;
    }

    public static StructuringElement create(String type, int size) {
        if (size % 2 == 0) size++;
        int[][] m = new int[size][size];
        int r = size / 2;
        switch (type) {
            case "Circle" -> {
                for (int y = -r; y <= r; y++)
                    for (int x = -r; x <= r; x++)
                        if (x*x + y*y <= r*r) m[y+r][x+r] = 1;
            }
            case "Cross" -> {
                for (int i = 0; i < size; i++) {
                    m[r][i] = 1;
                    m[i][r] = 1;
                }
            }
            default -> { // Square
                for (int y = 0; y < size; y++)
                    for (int x = 0; x < size; x++)
                        m[y][x] = 1;
            }
        }
        return new StructuringElement(m);
    }
}
