package ifce.projects.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class OperatingSystem extends Thread {
    private List<Processes> processes; // PV needs to have a list of instantiated processes
    private Semaphore mutex = new Semaphore(1);

    public OperatingSystem(List<Processes> processes) {
        this.processes = processes;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                mutex.acquire();
                List<Integer> deadlockedProcesses = checkDeadlock();
                if (!deadlockedProcesses.isEmpty()) {
                    System.out.println("Deadlock detected! Blocked processes: " + deadlockedProcesses);
                    resolveDeadlock(deadlockedProcesses); // it should kill only one process
                } else {
                    System.out.println("No deadlock detected.");
                }
                mutex.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Integer> checkDeadlock() {
        List<Integer> blockedProcesses = new ArrayList<>();
        for (Processes p : processes) {
            Queue<Integer> requestedResources = p.getRequestQueue().get(p.getProcessId());

            if (requestedResources == null || requestedResources.isEmpty()) {
                continue;
            }

            int[] resourceRequestCount = new int[ResourceManager.EjMax];
            for (int resource : requestedResources) {
                resourceRequestCount[resource]++;
            }

            boolean isBlocked = true;
            for (int i = 0; i < resourceRequestCount.length; i++) {
                if (resourceRequestCount[i] > 0 && ResourceManager.A[i].availablePermits() >= resourceRequestCount[i]) {
                    isBlocked = false;
                    break;
                }
            }
            if (isBlocked) {
                blockedProcesses.add(p.getProcessId());
            }
        }
        return blockedProcesses;
    }

    private void resolveDeadlock(List<Integer> deadlockedProcesses) {
        for (Integer processId : deadlockedProcesses) {
            for (Processes p : processes) {
                if (p.getProcessId() == processId) {
                    terminateProcess(p);
                    break;
                }
            }
        }
    }

    private void terminateProcess(Processes p) {
        System.out.println("Terminating process: " + p.getProcessId());
        p.releaseAllResources();
        p.terminate();
    }
}
