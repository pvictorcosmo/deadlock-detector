module ifce.projects {
    requires javafx.controls;
    requires javafx.fxml;

    opens ifce.projects to javafx.fxml;

    exports ifce.projects;
    exports ifce.projects.controller;

    opens ifce.projects.controller to javafx.fxml;
}
