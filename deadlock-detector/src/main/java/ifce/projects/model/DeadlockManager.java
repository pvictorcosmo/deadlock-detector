// package ifce.projects.model;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Queue;
// import java.util.concurrent.Semaphore;

// public class DeadlockManager extends Thread {
//     private List<ProcessThread> processes; // Lista de processos gerenciados
//     public static List<Semaphore> A = new ArrayList<>(); // Recursos disponíveis
//     public static List<Integer> E = new ArrayList<>(); // Recursos disponíveis

//     public DeadlockManager(List<ProcessThread> processes) {
//         this.processes = processes;
//     }

//     @Override
//     public void run() {
//         while (true) {
//             try {
//                 sleep(5000);
//                 ProcessThread.mutex.acquire();
//                 checkDeadlock();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             } finally {
//                 ProcessThread.mutex.release();
//             }
//         }
//     }

//     private synchronized void checkDeadlock() {
//         List<ProcessThread> blockedProcesses = new ArrayList<>();
//         boolean[] finished = new boolean[processes.size()];
//         int[] availableResources = new int[A.size()];

//         for (int i = 0; i < A.size(); i++) {
//             availableResources[i] = A.get(i).availablePermits();
//         }

//         boolean progress;
//         do {
//             progress = false;
//             for (int i = 0; i < processes.size(); i++) {
//                 ProcessThread p = processes.get(i);
//                 if (!finished[i] && canExecute(p, availableResources)) {
//                     executeSimulation(p, availableResources);
//                     finished[i] = true;
//                     progress = true;
//                 }
//             }
//         } while (progress);

//         for (int i = 0; i < finished.length; i++) {
//             if (!finished[i]) {
//                 blockedProcesses.add(processes.get(i));
//             }
//         }

//         if (!blockedProcesses.isEmpty()) {
//             System.out.println("Deadlock detectado! Processos bloqueados: ");
//             for (ProcessThread p : blockedProcesses) {
//                 System.out.println("Processo ID: " + p.getProcessId());
//             }
//         } else {
//             System.out.println("Nenhum deadlock detectado.");
//         }
//     }

//     private boolean canExecute(ProcessThread p, int[] availableResources) {
//         Queue<Integer> requestedResources = p.getRequestQueue().get(p.getProcessId());

//         if (requestedResources == null || requestedResources.isEmpty()) {
//             return true;
//         }

//         for (int resource : requestedResources) {
//             if (resource < 0 || resource >= availableResources.length || availableResources[resource] < 1) {
//                 return false;
//             }
//         }
//         return true;
//     }

//     private void executeSimulation(ProcessThread p, int[] availableResources) {
//         Queue<Integer> requestedResources = p.getRequestQueue().get(p.getProcessId());

//         if (requestedResources != null) {
//             for (int resource : requestedResources) {
//                 availableResources[resource]--;
//             }
//             for (int resource : requestedResources) {
//                 availableResources[resource]++;
//             }
//         }
//     }

//     private void resolveDeadlock(int processId) {
//         for (ProcessThread p : processes) {
//             if (p.getProcessId() == processId) {
//                 terminateProcess(p);
//                 break;
//             }
//         }
//     }

//     private void terminateProcess(ProcessThread p) {
//         System.out.println("Encerrando processo: " + p.getProcessId());
//         p.releaseAllResources();
//         p.terminate();
//     }
// }
