import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class HSVPane {
    private GridPane pane = new GridPane();

    public Slider h = new Slider(0, 360, 0);
    public Slider s = new Slider(0, 100, 100);
    public Slider v = new Slider(0, 100, 100);

    public TextField hField = new TextField("0");
    public TextField sField = new TextField("100");
    public TextField vField = new TextField("100");

    public HSVPane() {
        pane.setHgap(6);
        pane.setVgap(6);
        pane.setPadding(new Insets(6));
        pane.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");
        pane.add(new Label("HSV"), 0, 0);

        pane.addRow(1, new Label("H"), h, hField);
        pane.addRow(2, new Label("S"), s, sField);
        pane.addRow(3, new Label("V"), v, vField);

        bindSliderAndField(h, hField);
        bindSliderAndField(s, sField);
        bindSliderAndField(v, vField);
    }

    private void bindSliderAndField(Slider slider, TextField field) {
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            field.setText(String.format("%.1f", newVal.doubleValue()));
        });

        field.textProperty().addListener((obs, oldText, newText) -> {
            try {
                double val = Double.parseDouble(newText);
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
