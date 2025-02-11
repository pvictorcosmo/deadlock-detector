package ifce.projects.model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Processo extends Thread {
    private String nome;
    private List<Recurso> recursos;
    private Random random = new Random();
    private Set<Recurso> recursosRequisitados = new HashSet<>();
    private int deltaS;
    private int deltaU;
    private long tempoEsperaMaximo; // Tempo máximo que o processo pode esperar por recursos

    public Processo(String nome, int deltaS, int deltaU, long tempoEsperaMaximo, List<Recurso> recursos) {
        this.nome = nome;
        this.deltaS = deltaS;
        this.deltaU = deltaU;
        this.tempoEsperaMaximo = tempoEsperaMaximo;
        this.recursos = recursos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long inicio = System.currentTimeMillis();
                Recurso recurso = recursos.get(random.nextInt(recursos.size()));

                // Tenta alocar o recurso e aguarda pelo tempo máximo configurado
                if (recurso.alocar()) {
                    recursosRequisitados.add(recurso);
                    System.out.println(nome + " alocou: " + recurso);
                } else {
                    System.out.println(nome + " não conseguiu alocar: " + recurso);
                }

                // Verifica se o processo está esperando por mais tempo que o limite configurado
                long tempoEspera = System.currentTimeMillis() - inicio;
                if (tempoEspera > tempoEsperaMaximo) {
                    System.out.println(nome + " excedeu o tempo de espera, reiniciando processo.");
                    return; // Reinicia o processo
                }

                Thread.sleep(deltaU * 1000); // Usa o recurso por um tempo aleatório
                if (!recursosRequisitados.isEmpty()) {
                    Recurso recursoLiberado = recursosRequisitados.iterator().next();
                    recursoLiberado.liberar();
                    System.out.println(nome + " liberou: " + recursoLiberado);
                    recursosRequisitados.remove(recursoLiberado);
                }

                Thread.sleep(deltaS * 1000); // Espera antes de solicitar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}