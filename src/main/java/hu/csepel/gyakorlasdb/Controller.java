package hu.csepel.gyakorlasdb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public abstract class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    protected void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    public static Controller ujAblak(String fxml, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(SzineszApplication.class.getResource(fxml));
        Scene scene = new Scene(loader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = loader.getController();
        controller.stage = stage;
        return controller;
    }

    protected boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos?");
        alert.setContentText(uzenet);
        Optional<ButtonType> result =alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
