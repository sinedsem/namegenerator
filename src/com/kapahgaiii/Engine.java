package com.kapahgaiii;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Engine {
    private char[] russianAlphabet = {'а', 'е', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т'}; //most-used characters
    private HashMap<String, Segment> data = new HashMap<String, Segment>();//our char-links
    Random randomizer = new Random();

    public class Segment { //pair of characters with the list of possible next chars
        String subs;
        HashSet<String> nextChars = new HashSet<String>();
        HashSet<String> canBeEndWith = new HashSet<String>();//we need it for good sounding the rest of a word

        public Segment(String subs) {
            this.subs = subs;
        }
    }

    public Engine() {
        readData(Genders.MALE.getSrc());
        readData(Genders.FEMALE.getSrc());
    }

    public void readData(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String s;
            while ((s = in.readLine()) != null) {
                s = s.toLowerCase();
                for (int i = 0; i < s.length() - 2; i++) {
                    //get substrings
                    String subs = s.substring(i, i + 2);//our pair
                    String nextChar = s.substring(i + 2, i + 3);//next char
                    //adding pair
                    if (!data.containsKey(subs)) {
                        data.put(subs, new Segment(subs));
                    }
                    //adding nextChar
                    if (!data.get(subs).nextChars.contains(nextChar)) {
                        data.get(subs).nextChars.add(nextChar);
                    }
                    //adding ending
                    if (i == s.length() - 4) {
                        String end = s.substring(i + 2);//word ending
                        if (!data.get(subs).canBeEndWith.contains(end)) {
                            data.get(subs).canBeEndWith.add(end);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void clearData() {
        data = new HashMap<String, Segment>();
    }

    public void printData() {
        for (HashMap.Entry<String, Segment> out : data.entrySet()) {
            for (String s : out.getValue().nextChars) {
                System.out.println(out.getKey() + " " + s);
            }
        }
    }

    public String generateName(int length) {
        /*
        * algorithm read list of Russian names and for all pairs of characters forms a lists of possible next chars
        * and of possible word endings after this pair
        * then word formation starts
        * it takes random pair of characters, not starting with 'ь', 'ы' or 'ъ'
        * then it takes random char from this pair's list
        * then it similarly work with last 2 characters
        * if some pair hasn't list of possible next chars next char takes randomly from the list of most-used chars
        * at the last step it adds two chars at once, chars from the list "canBeEndWith"
        * if list "canBeEndWith" it empty, it takes random pair
        * */
        String name; //our result

        Object[] keys = data.keySet().toArray();
        do {
            name = (String) keys[randomizer.nextInt(keys.length)]; //start width a random 2 symbols, don't start with Ъ, Ы, Ь
        }
        while (name.substring(0, 1).equals("ь") || name.substring(0, 1).equals("ъ") || name.substring(0, 1).equals("ы"));

        while (name.length() < length - 2) {//each iteration adds one character, last two will be added later
            String lastSymbols = name.substring(name.length() - 2);//we take two last characters
            String c = null;//and add suitable for it

            if (data.containsKey(lastSymbols)) { //searching for c
                Segment next = data.get(lastSymbols);
                String[] chars = stringSetToMixedArray(next.nextChars);
                int i = 0;
                while (i < chars.length) {
                    String o = chars[i];
                    if (data.containsKey(name.substring(name.length() - 1) + o)) {
                        c = o;
                        break;
                    }
                    i++;
                }
            }
            if (c == null) {//if not found, use one of the most-used characters
                c = Character.toString(russianAlphabet[randomizer.nextInt(russianAlphabet.length - 1)]);
            }

            name += c;
        }

        //make ending
        if (data.containsKey(name.substring(name.length() - 2)) && !data.get(name.substring(name.length() - 2)).canBeEndWith.isEmpty()) {
            Object[] end = data.get(name.substring(name.length() - 2)).canBeEndWith.toArray();
            name += end[randomizer.nextInt(end.length)];
        } else {//if haven't suitable use random pair
            name += (String) keys[randomizer.nextInt(keys.length)];
        }

        if (name.length() > length) { //it can be truth when length < 4
            name = name.substring(name.length() - length);
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String[] stringSetToMixedArray(Set<String> set) {
        String[] array = new String[set.size()];
        int i = 0;
        for (String s : set) {
            array[i] = s;
            i++;
        }
        for (int j = 0; j < array.length; j++) {
            int k = randomizer.nextInt(array.length);
            String tmp = array[j];
            array[j] = array[k];
            array[k] = tmp;
        }
        return array;
    }
}
