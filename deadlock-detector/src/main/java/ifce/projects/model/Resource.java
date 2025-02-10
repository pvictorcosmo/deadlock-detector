package ifce.projects.model;

public class Resource {
    public String name;
    public int id;
    public int quantityResources;

    public Resource(String name, int id, int quantityResources) {
        this.name = name;
        this.id = id;
        this.quantityResources = quantityResources;
    }
}
