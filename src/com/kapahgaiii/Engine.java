package com.kapahgaiii;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Engine {
    private char[] russianAlphabet = {'а', 'е', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т'}; //most-used characters
    private HashMap<String, Segment> data = new HashMap<String, Segment>();
    Random randomizer = new Random();

    public class Segment {
        String subs;
        LinkedHashMap<String, Integer> nextChars = new LinkedHashMap<String, Integer>();
        String canBeEndWith = null;
        int randomSum = 0;

        public Segment(String subs) {
            this.subs = subs;
        }

        public String getRandom() {
            int index = randomizer.nextInt(randomSum);
            int sum = 0;
            Map.Entry<String, Integer> pair;
            Iterator<Map.Entry<String, Integer>> it = nextChars.entrySet().iterator();
            do {
                pair = it.next();
                sum += pair.getValue();
            } while (it.hasNext() && sum < index);

            return pair.getKey();
        }

    }

    public Engine(String filename) throws IOException {
        //reading our data
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
                    if (i == s.length() - 4) {
                        data.get(subs).canBeEndWith = s.substring(i + 2);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        //counting random sums
        for (HashMap.Entry<String, Segment> outPair : data.entrySet()) {
            for (Map.Entry<String, Integer> inPair : outPair.getValue().nextChars.entrySet()) {
                outPair.getValue().randomSum += inPair.getValue();
            }
        }

    }

    public void printData() {
        for (HashMap.Entry<String, Segment> outPair : data.entrySet()) {
            for (Map.Entry<String, Integer> inPair : outPair.getValue().nextChars.entrySet()) {
                System.out.println(outPair.getKey() + " " + inPair.getKey() + " " + inPair.getValue());
            }
        }
    }

    public String generateName(int length) {
        String name; //our result

        Object[] keys = data.keySet().toArray();
        name = (String) keys[randomizer.nextInt(keys.length)]; //start width a random 2 symbols

        while (name.length() < length - 1) {

            String c = null;

            String lastSymbols = name.substring(name.length() - 2);
            if (data.containsKey(lastSymbols)) {

                Segment next = data.get(lastSymbols);

                int i = 0;

                Object[] chars = next.nextChars.keySet().toArray();

                while (i < chars.length) { //yes, that's awful
                    String o = next.getRandom();
                    if (data.containsKey(name.substring(name.length() - 1) + o)) {
                        c = o;
                    }
                    i++;
                }
            }
            if (c == null) {
                c = Character.toString(russianAlphabet[randomizer.nextInt(russianAlphabet.length - 1)]);
                //break;
            }

            name += c;
        }

        if (data.containsKey(name.substring(name.length() - 2)) && data.get(name.substring(name.length() - 2)).canBeEndWith != null) {
            name += data.get(name.substring(name.length() - 2)).canBeEndWith;
        } else {
            name += (String) keys[randomizer.nextInt(keys.length)];
        }


        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
