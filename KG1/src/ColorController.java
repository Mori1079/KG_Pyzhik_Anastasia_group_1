import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;

public class ColorController {
    private RGBPane rgb;
    private CMYKPane cmyk;
    private HSVPane hsv;
    private boolean updating = false;

    public ColorController(RGBPane rgb, CMYKPane cmyk, HSVPane hsv) {
        this.rgb = rgb;
        this.cmyk = cmyk;
        this.hsv = hsv;
        addListeners();
    }

    private void addListeners() {
        rgb.r.valueProperty().addListener((o, oldVal, newVal) -> updateFromRGB());
        rgb.g.valueProperty().addListener((o, oldVal, newVal) -> updateFromRGB());
        rgb.b.valueProperty().addListener((o, oldVal, newVal) -> updateFromRGB());

        cmyk.c.valueProperty().addListener((o, oldVal, newVal) -> updateFromCMYK());
        cmyk.m.valueProperty().addListener((o, oldVal, newVal) -> updateFromCMYK());
        cmyk.y.valueProperty().addListener((o, oldVal, newVal) -> updateFromCMYK());
        cmyk.k.valueProperty().addListener((o, oldVal, newVal) -> updateFromCMYK());

        hsv.h.valueProperty().addListener((o, oldVal, newVal) -> updateFromHSV());
        hsv.s.valueProperty().addListener((o, oldVal, newVal) -> updateFromHSV());
        hsv.v.valueProperty().addListener((o, oldVal, newVal) -> updateFromHSV());
    }

    private void updateFromRGB() {
        if (updating) return;
        updating = true;

        int r = (int) rgb.r.getValue();
        int g = (int) rgb.g.getValue();
        int b = (int) rgb.b.getValue();

        rgb.rField.setText(String.valueOf(r));
        rgb.gField.setText(String.valueOf(g));
        rgb.bField.setText(String.valueOf(b));

        double[] cmykVals = ColorConverter.rgbToCmyk(r, g, b);
        double[] hsvVals = ColorConverter.rgbToHsv(r, g, b);

        cmyk.c.setValue(cmykVals[0] * 100);
        cmyk.m.setValue(cmykVals[1] * 100);
        cmyk.y.setValue(cmykVals[2] * 100);
        cmyk.k.setValue(cmykVals[3] * 100);

        hsv.h.setValue(hsvVals[0]);
        hsv.s.setValue(hsvVals[1] * 100);
        hsv.v.setValue(hsvVals[2] * 100);

        updating = false;
    }

    private void updateFromCMYK() {
        if (updating) return;
        updating = true;

        double c = cmyk.c.getValue() / 100.0;
        double m = cmyk.m.getValue() / 100.0;
        double y = cmyk.y.getValue() / 100.0;
        double k = cmyk.k.getValue() / 100.0;

        cmyk.cField.setText(String.format("%.1f", c * 100));
        cmyk.mField.setText(String.format("%.1f", m * 100));
        cmyk.yField.setText(String.format("%.1f", y * 100));
        cmyk.kField.setText(String.format("%.1f", k * 100));

        int[] rgbVals = ColorConverter.cmykToRgb(c, m, y, k);
        rgb.r.setValue(rgbVals[0]);
        rgb.g.setValue(rgbVals[1]);
        rgb.b.setValue(rgbVals[2]);

        updating = false;
    }

    private void updateFromHSV() {
        if (updating) return;
        updating = true;

        double h = hsv.h.getValue();
        double s = hsv.s.getValue() / 100.0;
        double v = hsv.v.getValue() / 100.0;

        hsv.hField.setText(String.format("%.0f", h));
        hsv.sField.setText(String.format("%.1f", s * 100));
        hsv.vField.setText(String.format("%.1f", v * 100));

        int[] rgbVals = ColorConverter.hsvToRgb(h, s, v);
        rgb.r.setValue(rgbVals[0]);
        rgb.g.setValue(rgbVals[1]);
        rgb.b.setValue(rgbVals[2]);

        updating = false;
    }

    public void bindColorPicker(ColorPicker picker, TextField hexField, Region preview) {
        picker.setOnAction(e -> {
            Color c = picker.getValue();
            int r = (int) (c.getRed() * 255);
            int g = (int) (c.getGreen() * 255);
            int b = (int) (c.getBlue() * 255);
            rgb.r.setValue(r);
            rgb.g.setValue(g);
            rgb.b.setValue(b);
            hexField.setText(String.format("#%02X%02X%02X", r, g, b));
            preview.setStyle("-fx-background-color: " + hexField.getText() + "; -fx-border-color: black;");
        });
    }
}
