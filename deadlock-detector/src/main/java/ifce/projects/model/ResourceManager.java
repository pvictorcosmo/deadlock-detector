package ifce.projects.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ResourceManager {
    public static Semaphore[] A; // Semáforos para controle de acesso aos recursos
    public static int[] E; // Quantidade disponível de cada recurso
    public static int EjMax; // Quantidade máxima de recursos
    public static String[] resourceNames; // Array para armazenar os nomes dos recursos
    public static Integer[] resourceQuantityArr; // Inicializa os recursos
    public static List<Processes> processes = new ArrayList<>();

    public static void initializeResources(int numResources) {
        EjMax = numResources;
        resourceNames = new String[EjMax];
        if (A == null) {
            A = new Semaphore[resourceQuantityArr.length];
            for (int i = 0; i < EjMax; i++) {
                if (resourceQuantityArr[i] != null) {
                    System.out.println("RESOURCES: " + resourceNames[i]);
                    A[i] = new Semaphore(resourceQuantityArr[i]); // Cada recurso tem uma permissão inicialmente
                }
            }
        }

        if (E == null) {
            E = new int[EjMax];
            for (int i = 0; i < EjMax; i++) {
                if (resourceQuantityArr[i] != null) {
                    E[i] = resourceQuantityArr[i]; // Supondo que cada recurso inicialmente tenha 1 unidade disponível
                }
            }
        }
    }
}
