package com.kapahgaiii;

public class NameGenerator {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Menu menu = new Menu(engine);
        menu.run();
    }
}
