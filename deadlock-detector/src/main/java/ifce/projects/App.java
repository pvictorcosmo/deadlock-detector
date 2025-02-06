package ifce.projects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Caminho para o arquivo FXML
        String fxmlPath = "/ifce/projects/view/MainView.fxml";

        // Cria um FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        // Carrega a view e o controlador
        Parent root = loader.load();

        // Obtém o controlador e, se necessário, injeta o ViewModel (caso tenha)
        // Exemplo: ConfigView configView = loader.getController();
        // Injeção do ViewModel pode ser feita aqui, se necessário

        // Configura a cena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Configuração da Aplicação");
        primaryStage.setWidth(640); // Largura inicial
        primaryStage.setHeight(480); // Altura inicial
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
