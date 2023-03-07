import java.io.File;
import java.io.IOException;
import java.util.*;

public class JacksonAlgorithm {
    public static int totalExecutionTime(Task[] tasks) {
        sortByReleaseTime(tasks);
        int[] timeOfTerminationTask = new int[tasks.length];
        for (int i = 0; i < timeOfTerminationTask.length; i++) {
            timeOfTerminationTask[i] = Math.max((i > 0) ? timeOfTerminationTask[i - 1] : 0,
                    tasks[i].getReleaseTime()) + tasks[i].getExcutionTime();
        }
        final int totalExecutionTime = timeOfTerminationTask[timeOfTerminationTask.length - 1];
        return totalExecutionTime;
    }

    public static void sortByReleaseTime(Task[] tasks) {
        int numOfTasks = tasks.length;
        for (int i = 0; i < numOfTasks; i++) {
            for (int j = 1; j < numOfTasks; j++) {
                if (tasks[j - 1].getReleaseTime() > tasks[j].getReleaseTime()) {
                    swap(tasks, j - 1, j);
                } else if (tasks[j - 1].getReleaseTime() == tasks[j].getReleaseTime()) {
                    if (tasks[j - 1].getExcutionTime() < tasks[j].getExcutionTime()) {
                        swap(tasks, j - 1, j);
                    }
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
        for (String pathname : allPathnames) {
            Task[] tasks = getTasksFromFile(pathname);

            long start = System.currentTimeMillis();
            System.out.println("file: " + pathname);
            final int cMax = totalExecutionTime(tasks);
            long stop = System.currentTimeMillis();


            System.out.println("C_{max}: " + cMax);
            System.out.println("total time of execution: " + (stop - start) + "ms");

        }
    }














/*
        List<Task> tasksList = List.of( new Task(1,3,5),
                                        new Task(2, 2, 8),
                                        new Task(3, 8, 3),
                                        new Task(4, 3, 6),
                                        new Task(5, 4, 6));
        Task[] tasks = new Task[5];
        for(int i = 0; i<tasks.length; i++){
            tasks[i] = tasksList.get(i);
        }
        int cMax = totalExecutionTime(tasks);
       */
       /* List<Integer> capacities = List.of(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000, 20000);
        for (int capacity : capacities){
            Task[] tasks = getRandomTasks(capacity, 0, 10);
            int cMax = 0;

            long start = System.currentTimeMillis();
            for(int i = 0; i<100; i++){
                cMax = totalExecutionTime(tasks);
            }
            long stop = System.currentTimeMillis();

            System.out.println("capacity: " + capacity);
            System.out.println("Cmax: " + cMax);
            System.out.println("total time of execution: " + (stop - start) + "ms \n");
        }*/



    public static Task[] getTasksFromFile(String pathname){
        try{
            Scanner reader = new Scanner(new File(pathname));
            String firstLine = reader.nextLine();
            Task[] tasks = new Task[Integer.parseInt(firstLine)];
            int i = 0;
            while(reader.hasNextLine()){
                String[] task = reader.nextLine().split(" ");
                tasks[i] = new Task(i, Integer.parseInt(task[0]), Integer.parseInt(task[1]));
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
            //tasks[i] = new Task(i, (int) (Math.random() * maxTime) +minTime,
                    //(int) (Math.random() * maxTime) +minTime);
            tasks[i] = new Task(i, 2, 3);
        }
        return tasks;
    }

}