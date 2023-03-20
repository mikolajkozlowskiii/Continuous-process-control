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


        for (String pathname : pathnames) {
            Task[] tasks = getTasksFromFile(pathname);
            for (Task task : tasks) {
                unorderedTasks.add(task);
            }
            long start = System.currentTimeMillis();
            int cmax = schrage(readyTasks, unorderedTasks);
            long stop = System.currentTimeMillis();
            System.out.println("Schrage: " + cmax + ", time:" + (stop - start));

            Task[] tasks2 = getTasksFromFile(pathname);
            for (Task task : tasks2) {
                unorderedTasks.add(task);
            }
            start = System.currentTimeMillis();
            cmax = prtmS(readyTasks, unorderedTasks);
            stop = System.currentTimeMillis();
            System.out.println("prtmS: " + cmax + ", time:" + (stop - start));
        }
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



    public static int schrage(PriorityQueue<Task> readyTasks, PriorityQueue<Task> unorderedTasks){
        Task task;
        int time = 0;
        int Cmax = 0;
        while(!readyTasks.isEmpty() || !unorderedTasks.isEmpty()){
            //SPRAWDZENIE DOSTEPNYCH ZADAN
            while(!unorderedTasks.isEmpty() && unorderedTasks.peek().getReleaseTime() <= time){
                task =unorderedTasks.poll();
                readyTasks.add(task);
            }
            // PRZESUNIECIE DO MOMENTU, GDY JAKIES ZADANIE BEDZIE JUZ GOTOWE
            if(readyTasks.isEmpty()){
                time = unorderedTasks.peek().getReleaseTime();
            }
            // WYKONANIE ZADANIA I PRZESUNIECIE CZASU
            else{
                task = readyTasks.poll();
                time = time + task.getExcutionTime();
                Cmax = Math.max(Cmax, time+task.getDeliveryTime());
            }
        }
        return Cmax;
    }

    public static int prtmS(PriorityQueue<Task> readyTasks, PriorityQueue<Task> unorderedTasks){
        Task currentTask = new Task(0, 0, 0, Integer.MAX_VALUE);
        Task nextReadyTask;
        int time = 0;
        int Cmax = 0;
        while(!readyTasks.isEmpty() || !unorderedTasks.isEmpty()){
            // SPRAWDZENIE DOSTEPNYCH ZADAN
            while(!unorderedTasks.isEmpty() && unorderedTasks.peek().getReleaseTime() <= time){
                nextReadyTask = unorderedTasks.poll();
                readyTasks.add(nextReadyTask);
                if(nextReadyTask.getDeliveryTime() > currentTask.getDeliveryTime()){
                    currentTask.setExecutionTime(time - nextReadyTask.getReleaseTime());
                    time = nextReadyTask.getReleaseTime(); // MOZLIWE "COFNIECIE" CZASU
                    if(currentTask.getReleaseTime() > 0){
                        readyTasks.add(currentTask);
                    }
                }
            }
            // PRZESUNIECIE DO MOMENTU, GDY JAKIES ZADANIE BEDZIE JUZ GOTOWE
            if(readyTasks.isEmpty()){ // przesuniecie do czasu, gdy juz jakies zadanie bedzie gotowe
                time = unorderedTasks.peek().getReleaseTime();
            }
            // POTENCJALNE WYKONANIE ZADANIA I PRZESUNIECIE CZASU
            else{
                nextReadyTask = readyTasks.poll(); // POTENCJALNE WYKONYWANIE ZADANIA
                currentTask = nextReadyTask;

                time = time + currentTask.getExcutionTime(); // PRZESUNIECIE CZASU
                Cmax = Math.max(Cmax, time+currentTask.getDeliveryTime()); // POTENCJALNE CMAX DLA DANEGO ZADANIA
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


    public static Task[] getRandomTasks(int capacity, int minTime, int maxTime){
        Task[] tasks = new Task[capacity];
        for(int i = 0; i<capacity; i++){
            tasks[i] = new Task(i, 2, 3, 3);
        }
        return tasks;
    }
}

/* List<Integer> capacity = List.of(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000);
        for(int cap : capacity) {
            Task[] tasks = getRandomTasks(cap, 1, 100);

            int cMax = 0;
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                unorderedTasks.addAll(Arrays.asList(tasks));
                cMax = schrage(readyTasks,unorderedTasks);
            }
            long stop = System.currentTimeMillis();

            System.out.println("Prtms C_{max}: " + cMax);
            System.out.println("Prtms: " + (stop - start) + "ms");






            Task[] tasks2 = getRandomTasks(cap, 1, 100);

            int cMax2 = 0;
            long start2 = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                unorderedTasks.addAll(Arrays.asList(tasks2));
                cMax2 = prtmS(readyTasks,unorderedTasks);
            }
            long stop2 = System.currentTimeMillis();

            System.out.println("Schrage C_{max}: " + cMax2);
            System.out.println("Schrage: " + (stop2 - start2) + "ms");*/