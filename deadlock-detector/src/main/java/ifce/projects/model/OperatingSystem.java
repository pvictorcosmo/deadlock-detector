package ifce.projects.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import ifce.projects.controller.SimulationSetupController;

public class OperatingSystem extends Thread {

    public static Semaphore mutex = new Semaphore(1);
    private SimulationSetupController controller;

    public OperatingSystem(List<Processes> processes, SimulationSetupController controller) {
        this.controller = controller;

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                OperatingSystem.mutex.acquire();
                List<Integer> deadlockedProcesses = checkDeadlock();
                if (!deadlockedProcesses.isEmpty()) {
                    controller.appendLog("Deadlock detected! Blocked processes: " + deadlockedProcesses);
                    // resolveDeadlock(deadlockedProcesses); // it should kill only one process
                } else {
                    controller.appendLog("No deadlock detected.");
                }
                OperatingSystem.mutex.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Integer> checkDeadlock() {
        List<Integer> blockedProcesses = new ArrayList<>();
        for (Processes p : ResourceManager.processes) {
            Queue<Integer> requestedResources = p.getRequestQueue().get(p.getProcessId());
            System.out.println("process id in check deadlock" + p.getProcessId());
            if (requestedResources == null || requestedResources.isEmpty()) {
                continue;
            }

            int[] resourceRequestCount = new int[ResourceManager.resourceNames.length];
            for (int resource : requestedResources) {
                resourceRequestCount[resource]++;
            }

            boolean isBlocked = true;
            for (int i = 0; i < resourceRequestCount.length; i++) {
                System.out.println(
                        "request counted:" + resourceRequestCount[i] + resourceRequestCount.length);
                if (resourceRequestCount[i] > 0
                        && ResourceManager.A[i].availablePermits() <= resourceRequestCount[i]) {
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
            for (Processes p : ResourceManager.processes) {
                if (p.getProcessId() == processId) {
                    terminateProcess(p);
                    break;
                }
            }
        }
    }

    public void terminateProcess(Processes p) {
        controller.appendLog("Terminating process: " + p.getProcessId());

        p.releaseAllResources();
        p.terminate();
    }
}
