package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private MarkovChain markovChain;

    public static void main(String[] args) {
        Application app = new Application();

        app.createMarkovChain("training_data.txt");
        app.cmdlineUI(8);
    }

    public Application() {
        markovChain = new MarkovChain(2, 2);
    }

    public void cmdlineUI (int maxIter) {
        System.out.println("|----------------------------------------------------|");
        System.out.println("|                   SETUP COMPLETE                   |");
        System.out.println("|----------------------------------------------------|");
        System.out.println("|----------------------------------------------------|");
        System.out.println("|            = = = THE MARKOV CHAIN = = =            |");
        System.out.println("|        I can stiiiiillll hear you sayyyying        |");
        System.out.println("|       You would never break the markov chain       |");
        System.out.println("|----------------------------------------------------|");
        System.out.println("|     Enter text to use as the initial characters    |");
        System.out.println("|  Enter less than 2 characters to go totally random |");
        System.out.println("|         Enter 'exit' to end the application        |");
        System.out.println("|----------------------------------------------------|");
        System.out.println("|  Break the silence, damn the dark, damn the light: |");
        System.out.println(" ");

        String inp = "";
        Scanner scanner = new Scanner(System.in);
        while (!inp.equals("exit")) {
            System.out.print(" > ");
            inp  = scanner.nextLine();

            if (inp.equals("exit")){
                System.exit(0);
            }
            else if (inp.length() <= 1) {

                System.out.println("Listen to the wind blowwwww... I hear a new word: " + markovChain.generateWord(maxIter));
            }
            else {
                String word = markovChain.generateWord(inp, maxIter);
                if (word.isBlank()) { System.out.println("Damn your love, damn your lies. The last characters of that input arent in my training data."); continue; }
                System.out.println("Listen to the wind blowwwww... I hear a new word: " + word);
            }
        }
    }

    /**
     * creates training data and then prepares the markov chain.
     * @param trainingDataFileName name and address of training data text file
     */
    public void createMarkovChain(String trainingDataFileName){

        ArrayList<String> trainingData = loadTrainingDataFromFile(trainingDataFileName);
        markovChain.prepare(trainingData);
    }

    /**
     * generates training data from a file. Each datum is on new line. Appends '|' to each datum to indicate end of word.
     * @param fileName Name and address of source file
     * @return an ArrayList containing all training data
     */
    private ArrayList<String> loadTrainingDataFromFile(String fileName) {
        System.out.println("Collecting dataset from " + fileName);
        ArrayList<String> data = new ArrayList<String>();

        File trainingDataFile = new File(fileName);

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(trainingDataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        int i = 0;
        while (fileScanner.hasNextLine()){
            String newDatum;
            newDatum = fileScanner.nextLine();
            data.add(newDatum + "|");
            i++;
        }

        System.out.println("Found " + i + " strings");

        return data;
    }

    /**
     * generates training data from user input. Each datum entered separately. Appends '|' to each datum to indicate end of word.
     * @return an ArrayList containing all training data
     */
    private ArrayList<String> getTrainingDataFromCmdline() {
        System.out.println("Enter each datum, pressing ENTER after each: ");
        ArrayList<String> data = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);
        String newDatum;
        do {
            newDatum = scanner.nextLine();

            if (!newDatum.equals("exit")) {
                data.add(newDatum + "|");
            }
        } while (!newDatum.equals("exit"));

        return data;
    }
}
