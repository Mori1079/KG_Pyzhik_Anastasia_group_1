import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class RGBPane {
    private GridPane pane = new GridPane();

    public Slider r = new Slider(0, 255, 255);
    public Slider g = new Slider(0, 255, 0);
    public Slider b = new Slider(0, 255, 0);

    public TextField rField = new TextField("255");
    public TextField gField = new TextField("0");
    public TextField bField = new TextField("0");

    public RGBPane() {
        pane.setHgap(6);
        pane.setVgap(6);
        pane.setPadding(new Insets(6));
        pane.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");
        pane.add(new Label("RGB"), 0, 0);

        pane.addRow(1, new Label("R"), r, rField);
        pane.addRow(2, new Label("G"), g, gField);
        pane.addRow(3, new Label("B"), b, bField);

        bindSliderAndField(r, rField);
        bindSliderAndField(g, gField);
        bindSliderAndField(b, bField);
    }

    private void bindSliderAndField(Slider slider, TextField field) {
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            field.setText(String.valueOf(newVal.intValue()));
        });

        field.textProperty().addListener((obs, oldText, newText) -> {
            try {
                int val = Integer.parseInt(newText);
                if (val >= slider.getMin() && val <= slider.getMax()) {
                    slider.setValue(val);
                }
            } catch (NumberFormatException e) {
            }
        });
    }

    public GridPane getPane() {
        return pane;
    }
}
