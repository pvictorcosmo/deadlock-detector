package ifce.projects.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class EventLogController {
    @FXML
    private TextArea logArea;

    public void initialize() {
        logArea.setText("Simulation started...\n"); // Exemplo inicial
    }

    public void appendLog(String message) {
        logArea.appendText(message + "\n");
    }
}
