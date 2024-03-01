package gc.algorithm.exercises;

import java.util.Scanner;

public class LineReport {
    // 500 lines in terminal
    private static LineUsage[] bigArray = new LineUsage[501];

    public static void main(String[] args) {
        fillBigArray();
        readFile();
        printAll();
    }

    // adds empty LineUsage objects in the bigArray
    public static void fillBigArray() {
        for (int i = 0; i < bigArray.length; i++) {
            bigArray[i] = new LineUsage();
        }
    }

    // reads input file and puts data into the bigArray
    public static void readFile() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineSplit = line.split(" ");
            String username = lineSplit[1];
            int terminal = Integer.parseInt(lineSplit[0]);
            bigArray[terminal].addObservation(username);
        }
    }

    // prints out every element in the bigArray
    public static void printAll() {
        System.out.println("Line Most Common User Count\n");
        for (int i = 1; i < bigArray.length; i++) {
            String username = bigArray[i].findMaxUsage().getUsername();
            int count = bigArray[i].findMaxUsage().getCount();
            System.out.println(i + " " + username + " " + count);
        }
    }

    // prints only terminals that have a user
    public static void printExisting() {
        System.out.println("Line Most Common User Count\n");
        for (int i = 1; i < bigArray.length; i++) {
            String username = bigArray[i].findMaxUsage().getUsername();
            int count = bigArray[i].findMaxUsage().getCount();
            if (count == 0) {
                continue;
            }
            System.out.println(i + " " + username + " " + count);
        }
    }
}
