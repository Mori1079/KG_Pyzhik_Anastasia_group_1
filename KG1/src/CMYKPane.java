import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class CMYKPane {
    private GridPane pane = new GridPane();

    public Slider c = new Slider(0, 100, 0);
    public Slider m = new Slider(0, 100, 100);
    public Slider y = new Slider(0, 100, 100);
    public Slider k = new Slider(0, 100, 0);

    public TextField cField = new TextField("0");
    public TextField mField = new TextField("100");
    public TextField yField = new TextField("100");
    public TextField kField = new TextField("0");

    public CMYKPane() {
        pane.setHgap(6);
        pane.setVgap(6);
        pane.setPadding(new Insets(6));
        pane.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");
        pane.add(new Label("CMYK"), 0, 0);

        pane.addRow(1, new Label("C"), c, cField);
        pane.addRow(2, new Label("M"), m, mField);
        pane.addRow(3, new Label("Y"), y, yField);
        pane.addRow(4, new Label("K"), k, kField);

        bindSliderAndField(c, cField);
        bindSliderAndField(m, mField);
        bindSliderAndField(y, yField);
        bindSliderAndField(k, kField);
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
