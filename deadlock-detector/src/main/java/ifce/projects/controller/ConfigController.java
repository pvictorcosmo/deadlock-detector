package ifce.projects.controller;

import ifce.projects.model.Resource;
import ifce.projects.model.SharedData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class ConfigController {
    @FXML
    private TextField nameResourceField;
    @FXML
    private TextField idResourceField;
    @FXML
    private TextField quantityResourceField;
    @FXML
    private ListView<Resource> resourceList;

    @FXML
    private void adicionarRecurso() {
        String name = nameResourceField.getText();
        int id = Integer.parseInt(idResourceField.getText());
        int quantity = Integer.parseInt(quantityResourceField.getText());

        Resource resource = new Resource(name, id, quantity);
        SharedData.getInstance().addRecurso(resource);
        resourceList.getItems().add(resource);
    }
}
