package ifce.projects.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifce.projects.model.ResourceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ResourceViewController {

    @FXML
    private TextField resourceNameField;

    @FXML
    private TextField resourceQuantityField;

    @FXML
    private Label resourceLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button startButton;

    private int currentResourceIndex = 0;
    List<String> resourceNames = new ArrayList<>();
    List<Integer> resourceQuantity = new ArrayList<>();

    @FXML
    public void initialize() {
        // Iniciar o botão "Start Simulation" como invisível
        // startButton.setVisible(false);
    }

    @FXML
    private void handleNext() {
        String resourceName = resourceNameField.getText().trim();
        String quantityName = resourceQuantityField.getText().trim();
        int quantity = Integer.parseInt(resourceQuantityField.getText());

        // Verificar se o nome do recurso está vazio
        if (resourceName.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Resource name cannot be empty.");
            alert.showAndWait();
            return;
        }

        // Armazenar o nome do recurso
        if (resourceName != "" && quantityName != "") {
            resourceNames.add(resourceName);
            resourceQuantity.add(quantity);
        }
        currentResourceIndex++;

        // Limpar o campo de entrada para o próximo recurso
        resourceNameField.clear();
        resourceQuantityField.clear();

        // Atualizar o texto da label para o próximo recurso
        if (currentResourceIndex < ResourceManager.EjMax) {
            resourceLabel.setText("Enter name for resource " + (currentResourceIndex + 1));
        } else {
            // Todos os recursos foram preenchidos, mostrar o botão de simulação
            resourceLabel.setText("All resources entered! You can now start the simulation.");
            nextButton.setVisible(false);
            startButton.setVisible(true); // Tornar o botão "Start Simulation" visível
        }
    }

    @FXML
    private void handleStartSimulation(ActionEvent event) {
        try {
            ResourceManager.resourceQuantityArr = new Integer[resourceQuantity.size() + 1];
            for (int i = 0; i < resourceQuantity.size(); i++) {
                ResourceManager.resourceQuantityArr[i] = resourceQuantity.get(i);
            }
            ResourceManager.initializeResources(currentResourceIndex + 1);
            for (int i = 0; i < resourceNames.size(); i++) {
                ResourceManager.resourceNames[i] = resourceNames.get(i);
            }

            // Aqui você pode adicionar qualquer lógica para iniciar a simulação
            String fxmlPath = "/ifce/projects/view/SimulationView.fxml";

            // Cria um FXMLLoader
            // Carregar a tela de recursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setWidth(840); // Largura inicial
            stage.setHeight(680); // Altura inicial
            stage.setTitle("Simulation Process");
            stage.show();
            Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAtual.close();
            // Exemplo de exibição de mensagem
            System.out.println("Simulation started with the following resources:");
            for (int i = 0; i < currentResourceIndex; i++) {
                System.out.println("Resource " + (i + 1) + ": " + ResourceManager.resourceNames[i]);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
