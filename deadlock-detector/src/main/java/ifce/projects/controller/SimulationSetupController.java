package ifce.projects.controller;

import ifce.projects.model.OperatingSystem;
import ifce.projects.model.Processes;
import ifce.projects.model.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimulationSetupController {

    @FXML
    private TextField tsField;

    @FXML
    private TextField tuField;

    @FXML
    private ListView<String> processListView;

    @FXML
    private Button addProcessButton;

    @FXML
    private Button removeProcessButton;

    @FXML
    private Button startSimulationButton;

    @FXML
    private TextArea eventLogTextArea; // Adicionando TextArea para o log de eventos

    private final List<Processes> processes = new ArrayList<>();

    @FXML
    private void handleAddProcess() {
        // Criar o dialog de entrada para 'ts' e 'tu'
        TextInputDialog tsDialog = new TextInputDialog();
        tsDialog.setTitle("Process Details");
        tsDialog.setHeaderText("Enter the ts value:");
        tsDialog.setContentText("ts:");

        // Estilizando diretamente o TextInputDialog
        tsDialog.getDialogPane().setStyle("-fx-background-color: #fafafa; -fx-border-radius: 10px;");
        tsDialog.getDialogPane().lookup(".label").setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tsDialog.getDialogPane().lookup(".content").setStyle("-fx-font-size: 14px; -fx-color: #555;");
        tsDialog.setResizable(true);

        // Obter a entrada para ts
        Optional<String> tsResult = tsDialog.showAndWait();
        if (tsResult.isPresent()) {
            try {
                int ts = Integer.parseInt(tsResult.get().trim());

                // Agora o dialog para 'tu'
                TextInputDialog tuDialog = new TextInputDialog();
                tuDialog.setTitle("Process Details");
                tuDialog.setHeaderText("Enter the tu value:");
                tuDialog.setContentText("tu:");

                // Estilizando o segundo TextInputDialog
                tuDialog.getDialogPane().setStyle("-fx-background-color: #fafafa; -fx-border-radius: 10px;");
                tuDialog.getDialogPane().lookup(".label").setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                tuDialog.getDialogPane().lookup(".content").setStyle("-fx-font-size: 14px; -fx-color: #555;");
                tuDialog.setResizable(true);

                Optional<String> tuResult = tuDialog.showAndWait();
                if (tuResult.isPresent()) {
                    try {
                        int tu = Integer.parseInt(tuResult.get().trim());

                        if (ts <= 0 || tu <= 0) {
                            throw new NumberFormatException();
                        }

                        // Criando o processo
                        int processId = processes.size() + 1;
                        Processes newProcess = new Processes(processId, ts, tu, this);
                        newProcess.start();
                        processes.add(newProcess);

                        // Atualizando a interface
                        processListView.getItems().add("Process " + processId + " - ts: " + ts + ", tu: " + tu);

                        // Adicionando o log de evento
                        eventLogTextArea
                                .appendText("Created Process " + processId + " - ts: " + ts + ", tu: " + tu + "\n");

                    } catch (NumberFormatException e) {
                        showAlert("Error", "Invalid input for tu. Please enter a valid number.");
                    }
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input for ts. Please enter a valid number.");
            }
        }
    }

    @FXML
    private void handleRemoveProcess() {
        // Criar o dialog de entrada para 'ts' e 'tu'
        TextInputDialog tsDialog = new TextInputDialog();
        tsDialog.setTitle("Process Details");
        tsDialog.setHeaderText("Enter the ts value:");
        tsDialog.setContentText("ts:");

        // Estilizando diretamente o TextInputDialog
        tsDialog.getDialogPane().setStyle("-fx-background-color: #fafafa; -fx-border-radius: 10px;");
        tsDialog.getDialogPane().lookup(".label").setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tsDialog.getDialogPane().lookup(".content").setStyle("-fx-font-size: 14px; -fx-color: #555;");
        tsDialog.setResizable(true);

        // Obter a entrada para ts
        Optional<String> tsResult = tsDialog.showAndWait();
        if (tsResult.isPresent()) {
            try {
                int ts = Integer.parseInt(tsResult.get().trim());

                // Agora o dialog para 'tu'
                TextInputDialog tuDialog = new TextInputDialog();
                tuDialog.setTitle("Process Details");
                tuDialog.setHeaderText("Enter the tu value:");
                tuDialog.setContentText("tu:");

                // Estilizando o segundo TextInputDialog
                tuDialog.getDialogPane().setStyle("-fx-background-color: #fafafa; -fx-border-radius: 10px;");
                tuDialog.getDialogPane().lookup(".label").setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                tuDialog.getDialogPane().lookup(".content").setStyle("-fx-font-size: 14px; -fx-color: #555;");
                tuDialog.setResizable(true);

                Optional<String> tuResult = tuDialog.showAndWait();
                if (tuResult.isPresent()) {
                    try {
                        int tu = Integer.parseInt(tuResult.get().trim());

                        if (ts <= 0 || tu <= 0) {
                            throw new NumberFormatException();
                        }

                        // Criando o processo
                        int processId = processes.size() + 1;
                        Processes newProcess = new Processes(processId, ts, tu, this);
                        newProcess.start();
                        processes.add(newProcess);

                        // Atualizando a interface
                        processListView.getItems().add("Process " + processId + " - ts: " + ts + ", tu: " + tu);

                        // Adicionando o log de evento
                        eventLogTextArea
                                .appendText("Created Process " + processId + " - ts: " + ts + ", tu: " + tu + "\n");

                    } catch (NumberFormatException e) {
                        showAlert("Error", "Invalid input for tu. Please enter a valid number.");
                    }
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input for ts. Please enter a valid number.");
            }
        }
    }

    @FXML
    public void initialize() {

        // Verifica se os recursos foram inicializados
        if (ResourceManager.EjMax <= 0) {
            showAlert("Error", "Resources have not been initialized.");
            return;
        }

        // Inicia o sistema operacional
        OperatingSystem os = new OperatingSystem(processes);
        os.start();

        // Exemplo de log de evento quando a simulação começa
        eventLogTextArea.appendText("Simulation started with " + processes.size() + " processes.\n");
    }

    private void showAlert(String title, String message) {
        // Melhorando o Alert
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Estilizando diretamente o Alert
        alert.getDialogPane().setStyle("-fx-background-color: #f2dede; -fx-border-radius: 15px;");

        // Estilizando os botões diretamente
        for (ButtonType buttonType : alert.getButtonTypes()) {
            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
            button.setStyle(
                    "-fx-background-color: #f5c6cb; -fx-text-fill: #721c24; -fx-font-size: 14px; -fx-padding: 10px;");
        }

        alert.showAndWait();
    }

    public void appendLog(String message) {
        eventLogTextArea.appendText(message + "\n");
    }
}
