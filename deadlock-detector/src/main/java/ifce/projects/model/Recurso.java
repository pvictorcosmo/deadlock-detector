package ifce.projects.model;

import java.util.concurrent.Semaphore;

public class Recurso {
    private String nome;
    private Semaphore semaphore;

    public Recurso(String nome, int qtdInstancias) {
        this.nome = nome;
        this.semaphore = new Semaphore(qtdInstancias, true);
    }

    public boolean alocar() {
        try {
            semaphore.acquire();
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void liberar() {
        semaphore.release();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " (Disponível: " + semaphore.availablePermits() + ")";
    }
}