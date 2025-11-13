import java.awt.image.BufferedImage;

public class Morphology {
    public static BufferedImage apply(BufferedImage img, StructuringElement se, String op) {
        return switch (op) {
            case "Dilation" -> dilate(img, se);
            case "Erosion" -> erode(img, se);
            case "Opening" -> dilate(erode(img, se), se);
            case "Closing" -> erode(dilate(img, se), se);
            default -> img;
        };
    }

    public static BufferedImage dilate(BufferedImage img, StructuringElement se) {
        int w = img.getWidth(), h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, img.getType());
        int[][] mask = se.getMask();
        int r = mask.length / 2;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int max = 0;
                for (int j = -r; j <= r; j++) {
                    for (int i = -r; i <= r; i++) {
                        int xx = x + i, yy = y + j;
                        if (xx >= 0 && yy >= 0 && xx < w && yy < h && mask[j + r][i + r] == 1) {
                            int val = img.getRaster().getSample(xx, yy, 0);
                            max = Math.max(max, val);
                        }
                    }
                }
                out.getRaster().setSample(x, y, 0, max);
            }
        }
        return out;
    }

    public static BufferedImage erode(BufferedImage img, StructuringElement se) {
        int w = img.getWidth(), h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, img.getType());
        int[][] mask = se.getMask();
        int r = mask.length / 2;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int min = 255;
                for (int j = -r; j <= r; j++) {
                    for (int i = -r; i <= r; i++) {
                        int xx = x + i, yy = y + j;
                        if (xx >= 0 && yy >= 0 && xx < w && yy < h && mask[j + r][i + r] == 1) {
                            int val = img.getRaster().getSample(xx, yy, 0);
                            min = Math.min(min, val);
                        }
                    }
                }
                out.getRaster().setSample(x, y, 0, min);
            }
        }
        return out;
    }
}
