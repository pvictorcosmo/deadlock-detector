package ifce.projects.model;

import java.util.concurrent.Semaphore;

public class ResourceManager {
    public static Semaphore[] A; // Semáforos para controle de acesso aos recursos
    public static int[] E; // Quantidade disponível de cada recurso
    public static int EjMax; // Quantidade máxima de recursos
    public static String[] resourceNames; // Array para armazenar os nomes dos recursos

    // Inicializa os recursos
    public static void initializeResources(int numResources) {
        EjMax = numResources;
        resourceNames = new String[EjMax];
        if (A == null) {
            A = new Semaphore[EjMax];
            for (int i = 0; i < EjMax; i++) {
                A[i] = new Semaphore(1); // Cada recurso tem uma permissão inicialmente
            }
        }

        if (E == null) {
            E = new int[EjMax];
            for (int i = 0; i < EjMax; i++) {
                E[i] = 1; // Supondo que cada recurso inicialmente tenha 1 unidade disponível
            }
        }
    }
}
