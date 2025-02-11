package ifce.projects.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ifce.projects.model.DeadlockDetector;
import ifce.projects.model.Processo;
import ifce.projects.model.Recurso;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField numResourcesField;

    @FXML
    private void initalize() {
      
    }

    @FXML
    private void handleNext() {
        try {
            int numResources = Integer.parseInt(numResourcesField.getText());

            if (numResources <= 0) {
                throw new NumberFormatException();
            }

            // Inicializar recursos com o número informado
            // ResourceManager.EjMax = numResources;
            String fxmlPath = "/ifce/projects/view/ResourceView.fxml";

            // Cria um FXMLLoader
            // Carregar a tela de recursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setWidth(840); // Largura inicial
            stage.setHeight(680); // Altura inicial
            stage.setTitle("Resource Manager");
            stage.show();

            // Fechar a tela atual
            Stage currentStage = (Stage) numResourcesField.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please enter a valid number for resources.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load the next screen.");
            alert.showAndWait();
        }
    }
}
