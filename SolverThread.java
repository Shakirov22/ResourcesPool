package edu.ResourcesPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SolverThread<Task extends Callable<String>> extends Thread {
    private final ResourcesPool<Task> pool;
    private ArrayList<String> solvedTasks;

    SolverThread(ResourcesPool<Task> pool) {
        this.pool = pool;
        this.solvedTasks = new ArrayList<>();
    }

    List<String> getSolvedTasks() {
        return this.solvedTasks;
    }

    public void run() {
        while (pool.tasksNotEmpty()) {
            Task taskFromPool = pool.getNextTaskForThread();
            if (taskFromPool == null) {
                break;
            }
            FutureTask<String> task = new FutureTask<String>(taskFromPool);
            task.run();
            try {
                String value = task.get();
                this.solvedTasks.add(value);
                System.out.println(this.getName() + " gets value " + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.getName() + "stop solving");
    }

}
