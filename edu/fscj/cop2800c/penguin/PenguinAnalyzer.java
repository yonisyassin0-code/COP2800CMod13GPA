// PenguinAnalyzer.java
// D. Singletary
// 3/14/25
// Handles file reading, processing, and writing of penguin data

package edu.fscj.cop2800c.penguin;

import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class PenguinAnalyzer {
    static final String FILE_NAME = "PalmerPenguins.csv";
    static final String[] HEADERS = {
        "SampleNum", "Species", "CulmenLen", 
        "CulmenDepth", "FlipperLen", "BodyMass", "Sex"
    };
    static final String FORMAT_STR = 
        "%-12s %-10s %-12s %-12s %-12s %-12s %-8s%n";
    static final String FORMAT_NUM_STR = 
        "%-12d %-10s %-12.1f %-12.1f %-12.1f %-12.1f %-8s%n";
    
    private ArrayList<Penguin> penguinList;

    // Constructor
    public PenguinAnalyzer() {
        penguinList = new ArrayList<>();
    }

    // Method to add a penguin
    public void addPenguin(Penguin penguin) {
        penguinList.add(penguin);
    }
    
    // Method to retrieve a penguin by sample number
    public Penguin getPenguinBySampleNum(int sampleNum) {
        for (Penguin penguin : penguinList) {
            if (penguin.getSampleNum() == sampleNum) {
                return penguin;
            }
        }
        return null; // Return null if not found
    }
    
    // Method to display penguin details using toString
    public void showRawData() {
        // use "magic" toString properties to print each penguin
        for (Penguin penguin : penguinList) {
            System.out.println(penguin);
        }
    }

    // Read data from CSV and populate penguinList
    // Returns number of penguins read.
    public int readPenguins() {
        if (penguinList == null)
            penguinList = new ArrayList<>();
    
        int count = 0; // Track the number of penguins read
    
        try (BufferedReader reader = 
                new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            reader.readLine(); // Skip header
    
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
    
                // Ensure correct column count
                if (tokens.length >= 7) {
                    try {
                        int sampleNumber = 
                            Integer.parseInt(tokens[0].trim());
                        String species = 
                            tokens[1].trim();
                        double culmenLength = 
                            Double.parseDouble(tokens[2].trim());
                        double culmenDepth = 
                            Double.parseDouble(tokens[3].trim());
                        double flipperLength = 
                            Double.parseDouble(tokens[4].trim());
                        double bodyMass = 
                            Double.parseDouble(tokens[5].trim());
                        String sex = 
                            tokens[6].trim();
    
                        // Ensure constructor order matches attribute order
                        Penguin penguin = new Penguin(sampleNumber, species, 
                                culmenLength, culmenDepth, 
                                bodyMass, sex, flipperLength);
                        penguinList.add(penguin);
                        count++; // Increment count after adding a penguin
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid row: " + line);
                    } catch (InvalidBirdDataException e) {
                        System.out.println(e);
                    }
                } else {
                    System.err.println("Skipping malformed row: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        PalmerPenguinsDB.createDB(penguinList);
    
        return count; // Return the total number of penguins read
    }


    // Print formatted table of penguin data
    public void printPenguins() {
        System.out.printf(FORMAT_STR, (Object[]) HEADERS);

        for (Penguin penguin : penguinList) {
            System.out.printf(
                FORMAT_NUM_STR,
                penguin.getSampleNum(), penguin.getSpecies(),
                penguin.getCulmenLength(), penguin.getCulmenDepth(),
                penguin.getFlipperLength(), penguin.getBodyMass(),
                penguin.getSex());
        }
    }

    // Write processed data to an output file
    public void writePenguins() {
        try (PrintWriter writer = 
                new PrintWriter(new FileWriter("PenguinOutput.txt"))) {
            writer.printf(FORMAT_STR,(Object[]) HEADERS);

            for (Penguin penguin : penguinList) {
                writer.printf(
                    FORMAT_NUM_STR,
                    penguin.getSampleNum(), penguin.getSpecies(),
                    penguin.getCulmenLength(), penguin.getCulmenDepth(),
                    penguin.getFlipperLength(), penguin.getBodyMass(),
                    penguin.getSex());
            }
            System.out.println(
                "Penguin data successfully written to PenguinOutput.txt.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
