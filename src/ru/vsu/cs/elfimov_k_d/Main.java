package ru.vsu.cs.elfimov_k_d;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = new Console();
        do {
            try {
                System.out.print("> ");
                String line = scanner.nextLine();
                console.checkCases(new StreamingArgs(line));
            } catch (RuntimeException e) {
                e.getStackTrace();
            }
        } while (true);
    }
}
