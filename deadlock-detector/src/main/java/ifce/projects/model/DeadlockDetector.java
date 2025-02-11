package ifce.projects.model;

import java.util.List;

public class DeadlockDetector extends Thread {
    private List<Processo> processos;

    public DeadlockDetector(List<Processo> processos) {
        this.processos = processos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000); // Verifica deadlock a cada 5 segundos
                detectarDeadlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectarDeadlock() {
        // Aqui podemos detectar deadlock verificando se algum processo
        // está preso esperando por recursos indefinidamente
        for (Processo processo : processos) {
            // Lógica simplificada de deadlock: se o processo estiver esperando mais que o
            // tempo máximo
            if (processo.getState() == State.WAITING) {
                System.out.println("Deadlock detectado! O processo " + processo + " está esperando.");
                // Tenta reiniciar o processo
                processo.interrupt();
            }
        }
    }
}
