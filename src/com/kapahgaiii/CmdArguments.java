package com.kapahgaiii;

import org.kohsuke.args4j.Option;

import java.io.Serializable;

public class CmdArguments implements Serializable {

    @Option(name = "--gender", usage = "Choose required gender")
    private String gender = "Both";

    @Option(name = "--length", usage = "Choose required name length")
    private byte length = 5;

    @Option(name = "--count", usage = "How much names do we need")
    private int count = 5;

    public Genders getGender() {
        switch (Character.toLowerCase(gender.charAt(0))) {
            case 'm':
                return Genders.MALE;
            case 'f':
                return Genders.FEMALE;
            default:
                return Genders.BOTH;
        }
    }

    public byte getLength() {
        return length;
    }

    public int getCount() {
        return count;
    }
}