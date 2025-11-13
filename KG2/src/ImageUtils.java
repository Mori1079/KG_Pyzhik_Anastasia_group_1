import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageUtils {
    public static BufferedImage unsharpMask(BufferedImage img, int radius, double amount) {
        BufferedImage blurred = gaussianBlur(img, radius);
        BufferedImage sharp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                int brgb = blurred.getRGB(x, y);
                Color c = new Color(rgb);
                Color b = new Color(brgb);
                int r = (int)Math.min(255, Math.max(0, c.getRed() + amount * (c.getRed() - b.getRed())));
                int g = (int)Math.min(255, Math.max(0, c.getGreen() + amount * (c.getGreen() - b.getGreen())));
                int bC = (int)Math.min(255, Math.max(0, c.getBlue() + amount * (c.getBlue() - b.getBlue())));
                sharp.setRGB(x, y, new Color(r, g, bC).getRGB());
            }
        }
        return sharp;
    }

    public static BufferedImage laplacianSharpen(BufferedImage img, double amount) {
        float[] kernel = {
                0, -1, 0,
                -1, 4, -1,
                0, -1, 0
        };
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
        BufferedImage lap = op.filter(img, null);
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color o = new Color(img.getRGB(x, y));
                Color l = new Color(lap.getRGB(x, y));
                int r = clamp(o.getRed() + (int)(amount * l.getRed()));
                int g = clamp(o.getGreen() + (int)(amount * l.getGreen()));
                int b = clamp(o.getBlue() + (int)(amount * l.getBlue()));
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }

    public static BufferedImage highPassSharpen(BufferedImage img, int radius, double amount) {
        BufferedImage blur = gaussianBlur(img, radius);
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color o = new Color(img.getRGB(x, y));
                Color b = new Color(blur.getRGB(x, y));
                int r = clamp((int)(o.getRed() + amount * (o.getRed() - b.getRed())));
                int g = clamp((int)(o.getGreen() + amount * (o.getGreen() - b.getGreen())));
                int bl = clamp((int)(o.getBlue() + amount * (o.getBlue() - b.getBlue())));
                res.setRGB(x, y, new Color(r, g, bl).getRGB());
            }
        }
        return res;
    }

    public static BufferedImage gaussianBlur(BufferedImage img, int radius) {
        if (radius <= 0) return img;
        float[] kernel = createGaussianKernel(radius);
        ConvolveOp op = new ConvolveOp(new Kernel(kernel.length, 1, kernel), ConvolveOp.EDGE_NO_OP, null);
        BufferedImage temp = op.filter(img, null);
        op = new ConvolveOp(new Kernel(1, kernel.length, kernel), ConvolveOp.EDGE_NO_OP, null);
        return op.filter(temp, null);
    }

    private static float[] createGaussianKernel(int radius) {
        int size = radius * 2 + 1;
        float[] kernel = new float[size];
        float sigma = radius / 2f;
        float sum = 0;
        for (int i = 0; i < size; i++) {
            float x = i - radius;
            kernel[i] = (float)Math.exp(-(x * x) / (2 * sigma * sigma));
            sum += kernel[i];
        }
        for (int i = 0; i < size; i++) kernel[i] /= sum;
        return kernel;
    }

    public static BufferedImage toGrayscale(BufferedImage img) {
        BufferedImage g = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = g.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return g;
    }

    private static int clamp(int v) {
        return Math.min(255, Math.max(0, v));
    }
}
