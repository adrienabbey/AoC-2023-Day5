/* Advent of Code 2023, Day 5: If You Give A Seed A Fertilizer
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day5 {
    /* Global Variables */
    public static String inputFileName = "example-input.txt";

    public static void main(String[] args) throws FileNotFoundException {
        // Load the input into an array of strings:
        ArrayList<String> inputStrings = loadInputStrings();

        // Create array lists to hold relevant data:
        ArrayList<Integer> seeds = new ArrayList<>();
        ArrayList<Integer[]> seedToSoilMap, soilToFertilizerMap, fertilizerToWaterMap, waterToLightMap,
                lightToTemperatureMap, temperatureToHumidityMap, humidityToLocationMap = new ArrayList<>();

        // Parse the input into a usable format:
        for (int i = 0; i < inputStrings.size(); i++) {
            // Check for 'seeds':
            if (inputStrings.get(i).contains("seeds")) {
                seeds = parseSeeds(inputStrings, i);
            }

            // Check for "seed-to-soil":
        }
    }

    public static ArrayList<String> loadInputStrings() throws FileNotFoundException {
        // Loads the input file into an array list of strings.

        // Open the input file:
        File inputFile = new File(inputFileName);
        Scanner inputScanner = new Scanner(inputFile);

        // Copy the input into an array list:
        ArrayList<String> inputStrings = new ArrayList<String>();
        while (inputScanner.hasNextLine()) {
            inputStrings.add(inputScanner.nextLine());
        }

        // Close the scanner:
        inputScanner.close();

        // Return the array list:
        return inputStrings;
    }

    public static boolean checkIntegerString(String inputString) {
        // Checks if the given string is a valid integer.
        // https://www.geeksforgeeks.org/check-if-a-given-string-is-a-valid-number-integer-or-floating-point-in-java/

        try {
            Integer.parseInt(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static ArrayList<Integer> parseSeeds(ArrayList<String> inputStrings, int index) {
        // Parses the seed integers at the given index, returning an array list.

        // Split the input string:
        String[] firstSplit = inputStrings.get(index).split(":");
        String[] seedStrings = firstSplit[1].split(" ");

        // Create an array list to contain valid seed integers:
        ArrayList<Integer> seedIntegers = new ArrayList<>();

        // Convert the seed strings into integers:
        for (String seed : seedStrings) {
            // Validate the input:
            if (checkIntegerString(seed)) {
                seedIntegers.add(Integer.parseInt(seed));
            }
        }

        // Return the seed integer array list:
        return seedIntegers;
    }

    public static ArrayList<Integer[]> parseNumbers(ArrayList<String> inputStrings, int index) {
        // Parses the number maps following the given index.
    }
}