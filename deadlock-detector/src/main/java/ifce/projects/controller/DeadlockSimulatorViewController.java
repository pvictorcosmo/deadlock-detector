package ifce.projects.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DeadlockSimulatorViewController {

    @FXML
    private TextArea logArea;
    
    private OperatingSystemController operatingSystemController;

    public DeadlockSimulatorViewController() {
        operatingSystemController = new OperatingSystemController();
    }

    @FXML
    public void initialize() {
        updateLog();
    }

    public void updateLog() {
        logArea.clear();
        for (String log : operatingSystemController.getLog()) {
            logArea.appendText(log + "\n");
        }
    }

    @FXML
    private void addProcess() {
        Process process = new Process(1, 5, 10);  // Simple example
        operatingSystemController.addProcess(process);
        operatingSystemController.log("Process " + process.getId() + " has been added.");
        updateLog();
    }

    @FXML
    private void addResource() {
        Resource resource = new Resource("Printer", 1, 5);  // Simple example
        operatingSystemController.addResource(resource);
        operatingSystemController.log("Resource " + resource.getName() + " has been added.");
        updateLog();
    }

    @FXML
    private void checkDeadlock() {
        operatingSystemController.detectDeadlock();
        updateLog();
    }
}
