package edu.ResourcesPool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

public class ResourcesPool<Task extends Callable<String>> {

    private final int SIZE_OF_TASKS_FOR_SOLVING = 20;
    private boolean isWorking;
    private int solversCount;
    private Queue<Task> tasks;
    private ArrayList<SolverThread<Task>> solvers;


    private void initSolverThreads() {
        this.solvers = new ArrayList<>();
        for (int i = 0; i < this.solversCount; i++) {
            SolverThread<Task> solver = new SolverThread<>(this);
            this.solvers.add(solver);
        }
    }

    ResourcesPool(int solversCount, ArrayList<Task> tasks) {
        this.solversCount = solversCount;
        this.initSolverThreads();
        this.tasks = new LinkedList<>(tasks);
        this.isWorking = true;
    }


    boolean tasksNotEmpty(){
        return this.tasks.size() > 0;
    }


    boolean isWorking() {
        for (SolverThread<Task> solver : this.solvers) {
            if (solver.isAlive()) {
                this.isWorking = true;
            }
        }
        return this.isWorking;
    }

    synchronized Task getNextTaskForThread() {
        return this.tasks.poll();
    }


    private void startSolvers(){
        for (SolverThread<Task> solver : this.solvers) {
            solver.start();
        }
    }


    private List<String> collectResults() {
        List<String> results = new ArrayList<>();
        for (SolverThread<Task> solver : this.solvers) {
            results.addAll(solver.getSolvedTasks());
        }
        Collections.sort(results);
        return results;
    }

    private static void saveResults(List<String> results) throws IOException {
        FileWriter writer = new FileWriter("src/resources/solved_equations.txt");
        for (String result : results) {
            writer.write(result + System.getProperty("line.separator"));
        }
        writer.close();
    }


    public void start() throws InterruptedException, IOException {
        this.startSolvers();
        while (this.isWorking()) {
            Thread.sleep(100);
        }
        List<String> results = collectResults();
        saveResults(results);
    }


    private static ArrayList<Equation> getEquations() throws IOException {
        ArrayList<Equation> equations = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/resources/equations.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            equations.add(new Equation(line));
        }
        return equations;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ResourcesPool<Equation> pool = new ResourcesPool<>(5, getEquations());
        pool.start();
    }
}
