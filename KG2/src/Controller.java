import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {
    private BorderPane root;
    private ImageView originalView;
    private ImageView resultView;
    private BufferedImage currentImage;
    private BufferedImage resultImage;

    public Controller() {
        buildUI();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void buildUI() {
        root = new BorderPane();
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setPrefWidth(360);

        Button loadBtn = new Button("Load image");
        Button saveBtn = new Button("Save result");

        originalView = new ImageView();
        originalView.setFitWidth(380);
        originalView.setPreserveRatio(true);
        resultView = new ImageView();
        resultView.setFitWidth(380);
        resultView.setPreserveRatio(true);

        Label sharpenLabel = new Label("Sharpening:");
        ChoiceBox<String> sharpenChoice = new ChoiceBox<>();
        sharpenChoice.getItems().addAll("Unsharp Mask", "Laplacian", "High-pass");
        sharpenChoice.setValue("Unsharp Mask");

        Slider amountSlider = new Slider(0, 5, 1);
        amountSlider.setShowTickLabels(true);
        amountSlider.setShowTickMarks(true);

        Slider radiusSlider = new Slider(0, 10, 1);
        radiusSlider.setShowTickLabels(true);
        radiusSlider.setShowTickMarks(true);

        Button applySharpen = new Button("Apply Sharpen");

        Label morphLabel = new Label("Morphology:");
        ChoiceBox<String> morphChoice = new ChoiceBox<>();
        morphChoice.getItems().addAll("Dilation", "Erosion", "Opening", "Closing");
        morphChoice.setValue("Dilation");

        ChoiceBox<String> seType = new ChoiceBox<>();
        seType.getItems().addAll("Square", "Circle", "Cross", "Custom (size)");
        seType.setValue("Square");

        Spinner<Integer> seSize = new Spinner<>(1, 51, 3, 2);
        seSize.setEditable(true);

        Button applyMorph = new Button("Apply Morphology");

        Label compLabel = new Label("Compression testing:");
        Button testCompression = new Button("Test compression (PNG/JPEG)");
        TextArea compOut = new TextArea();
        compOut.setPrefRowCount(6);

        controls.getChildren().addAll(loadBtn, saveBtn, new Separator(),
                new Label("Original:"), originalView, new Separator(),
                sharpenLabel, sharpenChoice, new Label("Amount (strength):"), amountSlider,
                new Label("Radius (for unsharp/Gaussian):"), radiusSlider, applySharpen,
                new Separator(), morphLabel, morphChoice, seType, new Label("SE size (odd):"), seSize, applyMorph,
                new Separator(), compLabel, testCompression, compOut);

        HBox center = new HBox(10);
        center.setPadding(new Insets(10));
        VBox v1 = new VBox(new Label("Original"), originalView);
        VBox v2 = new VBox(new Label("Result"), resultView);
        center.getChildren().addAll(v1, v2);

        loadBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
            File f = fc.showOpenDialog(new Stage());
            if (f != null) loadImage(f);
        });

        saveBtn.setOnAction(e -> {
            if (resultImage == null) return;
            FileChooser fc = new FileChooser();
            fc.setInitialFileName("result.png");
            File out = fc.showSaveDialog(new Stage());
            if (out != null) {
                try { ImageIO.write(resultImage, "PNG", out); }
                catch (IOException ex) { ex.printStackTrace(); }
            }
        });

        applySharpen.setOnAction(e -> {
            if (currentImage == null) return;
            String sel = sharpenChoice.getValue();
            double amount = amountSlider.getValue();
            int radius = (int) Math.round(radiusSlider.getValue());
            switch (sel) {
                case "Unsharp Mask" -> resultImage = ImageUtils.unsharpMask(currentImage, radius, amount);
                case "Laplacian" -> resultImage = ImageUtils.laplacianSharpen(currentImage, amount);
                case "High-pass" -> resultImage = ImageUtils.highPassSharpen(currentImage, radius, amount);
            }
            resultView.setImage(SwingFXUtils.toFXImage(resultImage, null));
        });

        applyMorph.setOnAction(e -> {
            if (currentImage == null) return;
            String op = morphChoice.getValue();
            String type = seType.getValue();
            int size = seSize.getValue();
            StructuringElement se = StructuringElement.create(type, size);
            BufferedImage gray = ImageUtils.toGrayscale(currentImage);
            resultImage = Morphology.apply(gray, se, op);
            resultView.setImage(SwingFXUtils.toFXImage(resultImage, null));
        });

        testCompression.setOnAction(e -> {
            if (currentImage == null) return;
            try {
                File tmpDir = new File(System.getProperty("java.io.tmpdir"));
                File pngFile = new File(tmpDir, "test_tmp.png");
                File jpgFile = new File(tmpDir, "test_tmp.jpg");
                ImageIO.write(currentImage, "PNG", pngFile);
                CompressionUtils.saveJpeg(currentImage, jpgFile, 0.85f);
                double pngSize = pngFile.length();
                double jpgSize = jpgFile.length();
                DecimalFormat df = new DecimalFormat("0.00");
                compOut.setText("PNG size: " + pngSize/1024 + " KB\\n" +
                        "JPEG(85%) size: " + df.format(jpgSize/1024) + " KB\\n" +
                        "JPEG/PNG ratio: " + df.format(jpgSize/pngSize));
            } catch (IOException ex){ ex.printStackTrace(); }
        });

        root.setLeft(controls);
        root.setCenter(center);
    }

    private void loadImage(File f) {
        try {
            BufferedImage bi = ImageIO.read(f);
            if (bi == null) return;
            currentImage = bi;
            resultImage = null;
            Image fx = SwingFXUtils.toFXImage(currentImage, null);
            originalView.setImage(fx);
            resultView.setImage(null);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
