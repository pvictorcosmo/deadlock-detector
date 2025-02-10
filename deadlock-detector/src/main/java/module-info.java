module ifce.projects {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ifce.projects to javafx.fxml;

    exports ifce.projects;
    exports ifce.projects.controller;
    exports ifce.projects.model;

    opens ifce.projects.controller to javafx.fxml;
}
