package ifce.projects.model;

public class Resource {
    private String nome;
    private int id;
    private int quantidade;

    public Resource(String nome, int id, int quantidade) {
        this.nome = nome;
        this.id = id;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return nome + " (ID: " + id + ", Qtd: " + quantidade + ")";
    }
}
