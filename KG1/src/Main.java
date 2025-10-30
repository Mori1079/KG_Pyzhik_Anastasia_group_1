import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Color Converter — RGB / CMYK / HSV");

        RGBPane rgbPane = new RGBPane();
        CMYKPane cmykPane = new CMYKPane();
        HSVPane hsvPane = new HSVPane();

        ColorController controller = new ColorController(rgbPane, cmykPane, hsvPane);

        VBox right = new VBox(10);
        right.setPadding(new Insets(12));
        right.setAlignment(Pos.TOP_CENTER);

        ColorPicker colorPicker = new ColorPicker(Color.RED);
        TextField hexField = new TextField("#FF0000");
        Region preview = new Region();
        preview.setPrefSize(160, 160);
        preview.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        Label infoLabel = new Label("Присутствует погрешность при приведении цвета к разным моделям");
        infoLabel.setWrapText(true);
        infoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: darkred;");
        right.getChildren().add(infoLabel);

        right.getChildren().addAll(
                new Label("Color Picker"), colorPicker,
                new Label("HEX (#RRGGBB)"), hexField,
                new Label("Preview"), preview
        );

        controller.bindColorPicker(colorPicker, hexField, preview);

        HBox top = new HBox(12, rgbPane.getPane(), cmykPane.getPane(), hsvPane.getPane());
        top.setPadding(new Insets(12));

        BorderPane root = new BorderPane();
        root.setCenter(top);
        root.setRight(right);

        Scene scene = new Scene(root, 1150, 420);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
