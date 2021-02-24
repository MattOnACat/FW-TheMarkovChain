package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap of strings and the frequency they occur.
 */
public class OccurrencesArray {
    Map<String, Float> occurrences;
    private int yLength; // max length of string element

    /**
     * Constructor for OccurrencesArray class. Adds an initial occurrence to the hashmap.
     * @param element initial String element
     * @param yLen expected length of String elements
     */
    public OccurrencesArray(String element, int yLen) {
        occurrences = new HashMap<String, Float>();
        yLength = yLen;

        // add initial element
        add(element);
    }

    /**
     * Adds a string element to the Hashmap.
     * If it already appears in the HashMap, the frequency of the element is incremented.
     * @param element string element to be added
     */
    public void add(String element) {
        if (element.length() > yLength || element.length() == 0){
            System.err.println("EXCEPTION: element passed to occurrence array was of unexpected length: " + element);
            return;
        }

        if (occurrences.containsKey(element)) {
            System.out.println("Incremented Y: " + element);
            float newFreq = occurrences.get(element) + 1f;
            occurrences.replace(element, newFreq);
        }
        else {
            System.out.println("Created Y: " + element);
            occurrences.put(element, 1f);
        }
    }

    public Map<String, Float> getOccurrences() {
        return occurrences;
    }

    public void setOccurrenceFreq(String key, Float freq) {
        occurrences.replace(key, freq);
    }

    public String selectWeightedRandom(){
        double totalWeight = 0f;

        for (Float w : occurrences.values()) {
            totalWeight += w.doubleValue();
        }

        double r = Math.random() * totalWeight;

        double countWeight = 0.0;
        for (Map.Entry<String, Float> occurrence : occurrences.entrySet()) {
            countWeight += occurrence.getValue().doubleValue();
            if (countWeight >= r) {
                return occurrence.getKey();
            }
        }
        throw new RuntimeException("Weighted Random selection failed. Big doo doo.");
    }

    @Override
    public String toString() {
        return occurrences.toString();
    }
}
