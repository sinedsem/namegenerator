package com.kapahgaiii;

import java.io.IOException;

public class NameGenerator {
    public static void main(String[] args) throws IOException {

        Engine engine = new Engine("input.txt");

        //engine.printData();

        for (int i = 0; i < 10; i++) {
            System.out.println(engine.generateName(10));
        }



        /*String s = "Виктор";



        int i = 2;
        String subs = s.substring(i, i + 2);
        String nextChar = s.substring(i + 2, i + 3);

        System.out.println(subs + " " + nextChar);*/
        //Menu menu = new Menu();
        //menu.run(engine);
    }

}
