package com.kapahgaiii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private byte option;

    public void run(Engine engine) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while ((option = Byte.parseByte(reader.readLine())) != -1) {
                System.out.println("hello");
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
