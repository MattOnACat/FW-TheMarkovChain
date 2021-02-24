package com.company;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a dictionary of strings and the probabilities for strings which could follow it.
 */
public class MarkovChain {
    private Map<String, OccurrencesArray> lookupTable;    // stores the initial strings (x) and the occurrence frequency (y frequency) of strings which follow them (y)
    private int xLength;
    private int yLength;

    /**
     * Constructor, creates a HashMap for storing the lookup table.
     */
    public MarkovChain(int xLen, int yLen) {
        lookupTable = new HashMap<String, OccurrencesArray>();

        xLength = xLen;
        yLength = yLen;
    }

    /**
     * Generates the lookup table from an ArrayList of strings.
     * @param data ArrayList of words to be used in generating the lookup table
     * @return true if successful, false if failed
     */
    public boolean prepare(@NotNull ArrayList<String> data) {
        System.out.println("Generating lookup table");
        int line = 1;
        for (String datum:data) {
            if (datum.length() < xLength+1) {
                System.err.println("EXCEPTION: Datum too short during lookup table generation: '" + datum + "' line " + line);
            }

            for (int i = 0; i < datum.length()-(xLength+1); i++) {
                System.out.print(line + "| ");

                String x = datum.substring(i, i+xLength);
                String y = datum.substring(i+xLength, i+xLength+yLength);

                boolean success = add(x, y); // add data to hashmap
            }
            line++;
        }

        System.out.println("Generated " + lookupTable.size() + " lookup elements, current lookup table is:");
        System.out.println(lookupTable.toString());

        this.toProbabilities();

        return true;
    }

    /**
     * generates a new word from scratch, randomly selecting the first 2 chars from the lookup table
     * @param maxIterate max amount of times to add new chars
     * @return the generated word (will always find at least 1 new char in the lookup table)
     */
    public String generateWord(int maxIterate) {

        Random random = new Random();
        int r = random.nextInt(lookupTable.size());
        String initial = (String) lookupTable.keySet().toArray()[r];

        System.out.println("Generating word using initial string: " + initial);

        return generateWord(initial, maxIterate);
    }

    /**
     * generates a new word using an initial string
     * @param initial initial string to generate from
     * @param maxIterate max amount of times to add new chars
     * @return the generated word, or empty if could not find new chars in lookup table for initial string
     */
    public String generateWord(String initial, int maxIterate) {
        StringBuilder word = new StringBuilder(initial);
        int count = 0;
        do {
            String nextChars = generateNext(word.substring(word.length() - 2, word.length()));
            if (nextChars == null) {
                if (count == 0) {
                    return "";        // if could not find new chars in lookup table for initial string
                }
                return word.toString();
            }
            word.append(nextChars);
            count++;
        } while (!word.toString().contains("|") && count <= maxIterate);

        return word.toString().replace("|", ""); // remove word end character
    }

    /**
     * generates 2 characters following on from the ctx String
     * @param ctx 2 character context String
     * @return next 2 characters in String
     */
    private String generateNext(String ctx) {
        if (ctx.length() != xLength) throw new RuntimeException("Error during word generation, input (x) was of unexpected length");
        if (lookupTable.get(ctx) == null) return null;
        return lookupTable.get(ctx).selectWeightedRandom();
    }

    /**
     * converts all frequencies in OccurrenceArrays to probabilities
     */
    private void toProbabilities() {
        for (OccurrencesArray datum: lookupTable.values()) {

            float sumFreq = 0f;
            for (Float freq : datum.getOccurrences().values()) {
                sumFreq += freq;
            }

            for (Map.Entry<String, Float> occurrence : datum.getOccurrences().entrySet()) {
                datum.setOccurrenceFreq(occurrence.getKey(), occurrence.getValue()/sumFreq);
            }
        }

        System.out.println("Converted lookup table to probabilities, current lookup table is:");
        System.out.println(lookupTable.toString());
    }

    /**
     * Updates the lookup table with a new element.
     * @return true successful, false if failed
     */
    private boolean add(@NotNull String x, @NotNull String y) {
        if (x.length() != xLength){
            System.err.println("EXCEPTION: element x passed to lookup table was of unexpected length: " + x);
            return false;
        }

        if (lookupTable.containsKey(x)) {
            System.out.print("Updated X: " + x + "  ");
            lookupTable.get(x).add(y);    // add occurrence to occurrence array
            return true;
        }
        else {
            System.out.print("Created X: " + x + "  ");
            OccurrencesArray newY = new OccurrencesArray(y, yLength);
            lookupTable.put(x, newY);
            return true;
        }
    }

    @Override
    public String toString() {
        return "LookupTable{" +
                "table=" + lookupTable +
                ", xLength=" + xLength +
                ", yLength=" + yLength +
                '}';
    }
}
