package dangelov;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HPApp extends Application {
    private HPVinTester currentFinder;
    private GridPane gridPane;
    private final String pattern = "yyyy-MM-dd";
    private StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public String toString(LocalDate date) {
            return date != null?this.dateFormatter.format(date):"";
        }

        public LocalDate fromString(String string) {
            return string != null && !string.isEmpty()?LocalDate.parse(string, this.dateFormatter):null;
        }
    };

    public HPApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        this.gridPane = this.initGrid();
        this.addWelcomeText();
        TextField vinNumberField = this.addVinNumberField();
        TextField carNumberField = this.addCarNumberField();
        DatePicker startDatePicker = this.initStartDatePicker();
        DatePicker endDatePicker = this.initEndDatePicker();
        HPApp.StatusField statusField = new HPApp.StatusField(this.gridPane);
        this.addFindBtn((event) -> {
            this.destroyFinder();
            this.currentFinder = new HPVinTester(vinNumberField.getText(), carNumberField.getText());
            Output output = this.currentFinder.find((LocalDate)startDatePicker.getValue(), (LocalDate)endDatePicker.getValue());
            statusField.update(output);
            (new Alert(AlertType.INFORMATION, "DONE: " + output.getMessage(), new ButtonType[]{ButtonType.OK})).showAndWait();
        });
        Scene scene = new Scene(this.gridPane, 500.0D, 350.0D);
        stage.setTitle("Historia Pojazdow: Date Checker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void stop() throws Exception {
        super.stop();
        this.destroyFinder();
    }

    private void destroyFinder() {
        if(this.currentFinder != null) {
            this.currentFinder.close();
        }

    }

    private void addWelcomeText() {
        Text title = new Text("Welcome user-shmuser");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20.0D));
        this.gridPane.add(title, 0, 0, 2, 1);
    }

    private DatePicker initEndDatePicker() {
        DatePicker endDate = new DatePicker();
        endDate.setConverter(this.converter);
        endDate.setPromptText("yyyy-MM-dd".toLowerCase());
        Label endDateLabel = new Label("To Date");
        this.gridPane.add(endDateLabel, 0, 4);
        this.gridPane.add(endDate, 1, 4);
        return endDate;
    }

    private DatePicker initStartDatePicker() {
        DatePicker startDate = new DatePicker();
        startDate.setConverter(this.converter);
        startDate.setPromptText("yyyy-MM-dd".toLowerCase());
        Label stDateLabel = new Label("From Date");
        this.gridPane.add(stDateLabel, 0, 3);
        this.gridPane.add(startDate, 1, 3);
        return startDate;
    }

    private GridPane initGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10.0D);
        grid.setVgap(10.0D);
        grid.setPadding(new Insets(25.0D, 25.0D, 25.0D, 25.0D));
        return grid;
    }

    private TextField addVinNumberField() {
        Label vinNumberLabel = new Label("VIN Number");
        this.gridPane.add(vinNumberLabel, 0, 1);
        TextField vinNumberInput = new TextField();
        this.gridPane.add(vinNumberInput, 1, 1);
        return vinNumberInput;
    }

    private TextField addCarNumberField() {
        Label carNumberLabel = new Label("Car Number");
        this.gridPane.add(carNumberLabel, 0, 2);
        TextField carNumberInput = new TextField();
        this.gridPane.add(carNumberInput, 1, 2);
        return carNumberInput;
    }

    private void addFindBtn(EventHandler<ActionEvent> handler) {
        Button btn = new Button("Find");
        HBox hbBtn = new HBox(10.0D);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        this.gridPane.add(btn, 1, 5);
        btn.setOnAction(handler);
    }

    private static class StatusField {
        Text status = new Text("");
        StringProperty statusProp = new SimpleStringProperty();

        StatusField(GridPane gridPane) {
            this.status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20.0D));
            this.status.textProperty().bind(this.statusProp);
            gridPane.add(this.status, 0, 6, 2, 1);
        }

        public void update(Output output) {
            this.statusProp.setValue(output.getMessage());
            this.status.setFill(output.getColor());
        }
    }
}
