package com.kapahgaiii;


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

        if (cmdArguments.getGender() != Genders.BOTH) {//default loaded both. If not,
            engine.clearData();//clear data and
            engine.readData(cmdArguments.getGender().getSrc());//load only male or female
        }

        for (int i = 0; i < cmdArguments.getCount(); i++) {
            View.print(engine.generateName(cmdArguments.getLength()));
        }

    }
}
