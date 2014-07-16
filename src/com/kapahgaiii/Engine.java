package com.kapahgaiii;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Engine {

    private HashMap<String, Segment> data = new HashMap<String, Segment>();

    public class Segment {
        String subs;
        HashMap<String, Integer> nextChars = new HashMap<String, Integer>();
        String canBeEndWith = null;

        public Segment(String subs) {
            this.subs = subs;
        }
    }

    public Engine(String filename) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));

            String s;
            while ((s = in.readLine()) != null) {
                s = s.toLowerCase();
                for (int i = 0; i < s.length() - 2; i++) {
                    String subs = s.substring(i, i + 2);
                    String nextChar = s.substring(i + 2, i + 3);
                    if (data.containsKey(subs)) {
                        if (data.get(subs).nextChars.containsKey(nextChar)) {
                            data.get(subs).nextChars.put(nextChar, data.get(subs).nextChars.get(nextChar) + 1);
                        } else {
                            data.get(subs).nextChars.put(nextChar, 1);
                        }
                    } else {
                        data.put(subs, new Segment(subs));
                    }
                    if (i == s.length() - 3) {
                        data.get(subs).canBeEndWith = nextChar;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void printData() {
        for (HashMap.Entry<String, Segment> outPair : data.entrySet()) {
            for (HashMap.Entry<String, Integer> inPair : outPair.getValue().nextChars.entrySet()) {
                System.out.println(outPair.getKey() + " " + inPair.getKey() + " " + inPair.getValue());
            }
        }
    }

    public String generateName(int length) {
        String name; //our result

        Random generator = new Random();
        Object[] keys = data.keySet().toArray();
        name = (String) keys[generator.nextInt(keys.length)]; //start width a random 2 symbols

        while (name.length() < length) {

            String c = null;

            Segment next = data.get(name.substring(name.length() - 2));

            int i = 0;

            Object[] chars = next.nextChars.keySet().toArray();

            while (i < chars.length) { //yes, that's awful
                String o = (String) (chars[generator.nextInt(chars.length)]);
                if (data.containsKey(name.substring(name.length() - 1) + o)) {
                    c = o;
                }
                i++;
            }

            if (c == null) {
                break;
            }

            name += c;
        }

        return name;
    }

}
