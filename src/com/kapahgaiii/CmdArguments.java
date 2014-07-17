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

    public String getGender() {
        return gender;
    }

    public byte getLength() {
        return length;
    }

    public int getCount() {
        return count;
    }
}