package ifce.projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ifce.projects.model.DeadlockDetector;
import ifce.projects.model.OperatingSystem;
import ifce.projects.model.Processes;
import ifce.projects.model.Processo;
import ifce.projects.model.Recurso;
import ifce.projects.model.ResourceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        // Criando recursos
        Recurso recurso1 = new Recurso("Recurso 1", 1);
        Recurso recurso2 = new Recurso("Recurso 2", 1); // Coloquei 1 para testar melhor o deadlock
        List<Recurso> recursos = new ArrayList<>(Arrays.asList(recurso1, recurso2));

        // Criando processos
        Processo processo1 = new Processo("Processo 1", 1, 2, 3000, recursos);
        Processo processo2 = new Processo("Processo 2", 1, 3, 3000, recursos);
        List<Processo> processos = new ArrayList<>(Arrays.asList(processo1, processo2));

        // Iniciando threads dos processos
        processo1.start();
        processo2.start();

        // Iniciando o detector de deadlock
        DeadlockDetector deadlockDetector = new DeadlockDetector(processos);
        deadlockDetector.start();

        // Aguarda por um tempo para simular a execução
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        primaryStage.setWidth(840); // Largura inicial
        primaryStage.setHeight(680); // Altura inicial
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
