package com.kapahgaiii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private byte option;
    private byte length = 5;
    private String genderText = "M&F";
    private Engine engine;

    public Menu(Engine engine) {
        this.engine = engine;
    }

    public void run() {
        System.out.println("Hello!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            do {
                try {
                    System.out.print("Type the number of a command (0 for help): ");
                    option = Byte.parseByte(reader.readLine());
                    apply(reader);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input");
                }

            } while (option != -1);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void apply(BufferedReader reader) throws IOException, NumberFormatException {
        switch (option) {
            case -1:
                System.out.println("Goodbye!");
                break;
            case 0:
                System.out.println("0) Help");
                System.out.println("1) Generate name");
                System.out.println("2) Choose name length (now " + length + ")");
                System.out.println("3) Choose gender (now " + genderText + ")");
                System.out.println("-1) Exit");
                break;
            case 1:
                System.out.println(engine.generateName(length));
                break;
            case 2:
                System.out.print("Enter length: ");
                length = Byte.parseByte(reader.readLine());
                break;
            case 3:
                System.out.print("Enter M, F or M&F: ");
                genderText = reader.readLine();
                if (genderText.equals("M")) {
                    engine.clearData();
                    engine.readData("male_names.txt");
                } else if (genderText.equals("F")) {
                    engine.clearData();
                    engine.readData("female_names.txt");
                } else {
                    genderText = "M&F";
                    engine.clearData();
                    engine.readData("male_names.txt");
                    engine.readData("female_names.txt");
                }
                break;
            default:
                System.out.println("Command not found");
                break;
        }
    }
}
