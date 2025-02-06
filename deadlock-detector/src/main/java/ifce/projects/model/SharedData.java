package ifce.projects.model;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private static final SharedData instance = new SharedData();
    private List<Resource> resources = new ArrayList<>();

    private SharedData() {
    }

    public static SharedData getInstance() {
        return instance;
    }

    public List<Resource> getRecursos() {
        return resources;
    }

    public void addRecurso(Resource resource) {
        resources.add(resource);
    }
}
