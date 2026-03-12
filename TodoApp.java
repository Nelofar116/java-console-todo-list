import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();
        File file = new File("tasks.txt");

        // Load tasks from file
        try {
            if (file.exists()) {
                Scanner fileReader = new Scanner(file);
                while (fileReader.hasNextLine()) {
                    tasks.add(fileReader.nextLine());
                }
                fileReader.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks.");
        }

        while (true) {
            System.out.println("\n--- TO DO LIST ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter task: ");
                String task = sc.nextLine();
                tasks.add(task + " [PENDING]");
                saveTasks(tasks, file);
                System.out.println("Task added.");

            } else if (choice == 2) {
                if (tasks.isEmpty()) {
                    System.out.println("No tasks available.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }

            } else if (choice == 3) {
                System.out.print("Enter task number to mark as completed: ");
                int num = sc.nextInt();
                sc.nextLine();

                if (num > 0 && num <= tasks.size()) {
                    String task = tasks.get(num - 1);
                    if (!task.contains("[DONE]")) {
                        task = task.replace("[PENDING]", "[DONE]");
                        tasks.set(num - 1, task);
                        saveTasks(tasks, file);
                        System.out.println("Task marked as completed.");
                    } else {
                        System.out.println("Task already completed.");
                    }
                } else {
                    System.out.println("Invalid task number.");
                }

            } else if (choice == 4) {
                System.out.print("Enter task number to delete: ");
                int num = sc.nextInt();
                sc.nextLine();

                if (num > 0 && num <= tasks.size()) {
                    tasks.remove(num - 1);
                    saveTasks(tasks, file);
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Invalid task number.");
                }

            } else if (choice == 5) {
                System.out.println("Exiting application...");
                break;

            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    // Method to save tasks to file
    static void saveTasks(ArrayList<String> tasks, File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String t : tasks) {
                writer.write(t);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving tasks.");
        }
    }
}
