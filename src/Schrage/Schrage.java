package Schrage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Schrage {
    public static void main(String[] args) {
        PriorityQueue<Task> readyTasks =
                new PriorityQueue<>(Comparator.comparingInt((Task s) -> s.getDeliveryTime()).reversed());
        PriorityQueue<Task> unorderedTasks =
                new PriorityQueue<>(Comparator.comparingInt((Task s) -> s.getReleaseTime()));

        List<String> pathnames = List.of("SCHRAGE1.dat", "SCHRAGE2.dat", "SCHRAGE3.dat", "SCHRAGE4.dat",
                "SCHRAGE5.dat", "SCHRAGE6.dat", "SCHRAGE7.dat", "SCHRAGE8.dat", "SCHRAGE9.dat");
        for(String pathname : pathnames) {
            Task[] tasks = getTasksFromFile(pathname);
            for(Task task : tasks){
                unorderedTasks.add(task);
            }
            System.out.println(schrage(readyTasks, unorderedTasks));
        }


/*
        Task task1 = new Task(1, 1, 2, 1);
        Task task2 = new Task(2, 3, 3, 3);
        Task task3 = new Task(3, 2, 7, 1);
        Task task4 = new Task(4, 9, 2, 9);
        Task task5 = new Task(5, 5, 2, 3);

        List<Task> tasks = List.of(task1, task2, task3, task4, task5);

        for(Task task : tasks){
            readyTasks.add(task);
            unorderedTasks.add(task);
        }

        while(!readyTasks.isEmpty()){
            System.out.println(readyTasks.poll());
        }
        System.out.println("xxx");
        while(!unorderedTasks.isEmpty()){
            System.out.println(unorderedTasks.poll());
        }*/

    }

    public static int schrage(PriorityQueue<Task> readyTasks, PriorityQueue<Task> unorderedTasks){
        Task task;
        int time = 0;
        int Cmax = 0;
        while(!readyTasks.isEmpty() || !unorderedTasks.isEmpty()){
            while(!unorderedTasks.isEmpty() && unorderedTasks.peek().getReleaseTime() <= time){ // check if there are ready tasks
                task =unorderedTasks.poll();
                readyTasks.add(task);
            }
            if(readyTasks.isEmpty()){ // przesuniecie do czasu, gdy juz jakies zadanie bedzie gotowe
                time = unorderedTasks.peek().getReleaseTime();
            }
            else{
                task = readyTasks.poll();
                time = time + task.getExcutionTime(); // moment zakonczenia wykonywania zadania
                Cmax = Math.max(Cmax, time+task.getDeliveryTime());
            }
        }
        return Cmax;
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
}
