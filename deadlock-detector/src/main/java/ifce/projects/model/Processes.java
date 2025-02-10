package ifce.projects.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import ifce.projects.controller.SimulationSetupController;

import java.util.concurrent.Semaphore;

public class Processes extends Thread {
    private boolean alive = true;
    private int t = 0;
    private int processId;
    private int ts;
    private int tu;
    private Queue<Integer> requestQueue = new LinkedList<>();
    private Queue<Integer> acquiredQueue = new LinkedList<>();
    private List<Integer> releaseTimes = new ArrayList<>();
    public static Semaphore mutex = new Semaphore(1);
    private SimulationSetupController controller;

    public Processes(int processId, int ts, int tu, SimulationSetupController controller) {
        this.processId = processId;
        this.ts = ts;
        this.tu = tu;
        this.controller = controller;

        // Initialize resources

    }

    public int getProcessId() {
        return processId;
    }

    @Override
    public void run() {
        while (alive) {
            try {
                Thread.sleep(1000);
                t++;
                if (t % ts == 0 && alive) {
                    mutex.acquire();
                    int resourceIndex = selectRandomResource();
                    requestResource(resourceIndex, processId);
                    releaseTimes.add(t + tu);
                    mutex.release();
                }
                if (!releaseTimes.isEmpty() && releaseTimes.get(0) == t) {
                    mutex.acquire();
                    releaseResource();
                    releaseTimes.remove(0);
                    mutex.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Map<Integer, Queue<Integer>> getRequestQueue() {
        Map<Integer, Queue<Integer>> map = new HashMap<>();
        map.put(processId, new LinkedList<>(requestQueue));
        return map;
    }

    public Map<Integer, Queue<Integer>> getAcquiredQueue() {
        Map<Integer, Queue<Integer>> map = new HashMap<>();
        map.put(processId, new LinkedList<>(acquiredQueue));
        return map;
    }

    private void requestResource(int resource, int id) {
        controller.appendLog("Resource " + resource + " was requested by process: " + processId);
        requestQueue.add(resource);
        try {
            ResourceManager.A[resource].acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        requestQueue.remove(resource);
        acquiredQueue.add(resource);
        controller.appendLog("Process " + processId + " acquired resource " + resource + "("
                + ResourceManager.resourceNames[resource] + ")");
    }

    private void releaseResource() {
        if (!acquiredQueue.isEmpty()) {
            int resource = acquiredQueue.poll();
            ResourceManager.A[resource].release();
            controller.appendLog("Process " + processId + " released resource " + resource + "("
                    + ResourceManager.resourceNames[resource] + ")");
        }
    }

    public void releaseAllResources() {
        while (!acquiredQueue.isEmpty()) {
            int resource = acquiredQueue.poll();
            ResourceManager.A[resource].release();
            controller.appendLog("Process " + processId + " released resource " + resource + "("
                    + ResourceManager.resourceNames[resource + 1] + ")");
        }
    }

    public void terminate() {
        alive = false;
        controller.appendLog("Process " + processId + " has been terminated.");
    }

    private int selectRandomResource() {
        int resource;

        do {
            resource = (int) (Math.random() * ResourceManager.E.length);
        } while (countOccurrences(acquiredQueue, resource) + 1 > ResourceManager.E[resource]);

        return resource;
    }

    private int countOccurrences(Queue<Integer> queue, int resource) {
        int count = 0;
        for (int r : queue) {
            if (r == resource) {
                count++;
            }
        }
        return count;
    }
}