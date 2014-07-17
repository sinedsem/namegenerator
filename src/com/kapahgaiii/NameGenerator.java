package com.kapahgaiii;

//import com.sun.scenario.CmdArguments;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class NameGenerator {


    public static void main(String[] args) {

        CmdArguments cmdArguments = new CmdArguments();
        CmdLineParser parser = new CmdLineParser(cmdArguments);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("An error occurred while parsing command line args");
        }

        Engine engine = new Engine();

        if (cmdArguments.getGender().substring(0, 1).toLowerCase().equals("m")) {
            engine.clearData();
            engine.readData("male_names.txt");
        } else if (cmdArguments.getGender().substring(0, 1).toLowerCase().equals("f")) {
            engine.clearData();
            engine.readData("female_names.txt");
        }

        for (int i = 0; i < cmdArguments.getCount(); i++) {
            System.out.println(engine.generateName(cmdArguments.getLength()));
        }

    }
}
