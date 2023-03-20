package RPQ;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RPQ {
    public static int totalExecutionTime(Task[] tasks) {
        sortByR(tasks);
        int[] timeOfTerminationTask = new int[tasks.length];
        int[] timeOfDeliveredTask = new int[tasks.length];
        for (int i = 0; i < timeOfTerminationTask.length; i++) {
            timeOfTerminationTask[i] = Math.max((i > 0) ? timeOfTerminationTask[i - 1] : 0,
                    tasks[i].getReleaseTime()) + tasks[i].getExcutionTime();
            timeOfDeliveredTask[i] = timeOfTerminationTask[i] + tasks[i].getDeliveryTime();
        }
        final int Cmax = Arrays.stream(timeOfDeliveredTask).max().getAsInt();
        return Cmax;
    }

    public static void sortByR(Task[] tasks) {
        int numOfTasks = tasks.length;
        for (int i = 0; i < numOfTasks; i++) {
            for (int j = 1; j < numOfTasks; j++) {
                if (tasks[j - 1].getReleaseTime() > tasks[j].getReleaseTime()) {
                    swap(tasks, j - 1, j);
                }
            }
        }
    }

    private static void swap(Task[] tasks, int firstIndex, int secondIndex) {
        Task tmp = tasks[firstIndex];
        tasks[firstIndex] = tasks[secondIndex];
        tasks[secondIndex] = tmp;
    }

    public static void main(String[] args) {
        List<String> allPathnames = List.of("JACK1.DAT", "JACK2.DAT", "JACK3.DAT", "JACK4.DAT", "JACK5.DAT",
                "JACK6.DAT", "JACK7.DAT", "JACK8.DAT");
        /*for (String pathname : allPathnames) {
            Task[] tasks = getTasksFromFile(pathname);

            long start = System.currentTimeMillis();
            System.out.println("file: " + pathname);
            final int cMax = totalExecutionTime(tasks);
            long stop = System.currentTimeMillis();


            System.out.println("C_{max}: " + cMax);
            System.out.println("total time of execution: " + (stop - start) + "ms");

        }*/
        List<Integer> capacity = List.of(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000);
        for(int cap : capacity){
            Task[] tasks = getRandomTasks(cap, 1, 10);

            int cMax = 0;
            long start = System.currentTimeMillis();
            for(int i = 0; i<1000; i++){
                cMax = totalExecutionTime(tasks);
            }
            long stop = System.currentTimeMillis();

            System.out.println("C_{max}: " + cMax);
            System.out.println("total time of execution: " + (stop - start) + "ms");

        }
    }

    public static Task[] getTasksFromFile(String pathname){
        try{
            Scanner reader = new Scanner(new File(pathname));
            String firstLine = reader.nextLine();
            Task[] tasks = new Task[Integer.parseInt(firstLine)];
            int i = 0;
            while(reader.hasNextLine()){
                String[] task = reader.nextLine().split(" ");
                tasks[i] = new Task(i, Integer.parseInt(task[0]), Integer.parseInt(task[1]), Integer.parseInt(task[2]));
                i++;
            }
            return tasks;
        } catch (IOException ex){
            throw new NoSuchElementException("Pathname: " + pathname + "didn't found.");
        }
    }

    public static Task[] getRandomTasks(int capacity, int minTime, int maxTime){
        Task[] tasks = new Task[capacity];
        for(int i = 0; i<capacity; i++){
            tasks[i] = new Task(i, 2, 3, 3);
        }
        return tasks;
    }

}