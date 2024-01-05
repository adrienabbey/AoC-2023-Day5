/* Advent of Code 2023, Day 5: If You Give A Seed A Fertilizer
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class day5 {
    /* Global Variables */
    public static String inputFileName = "example-input.txt";
    public static boolean testing = true;

    public static void main(String[] args) throws FileNotFoundException {
        // Load the input into an array of strings:
        ArrayList<String> inputStrings = loadInputStrings();

        // Create array lists to hold relevant data:
        ArrayList<Integer> seeds = new ArrayList<>();
        ArrayList<Integer[]> seedToSoilMap = null;
        ArrayList<Integer[]> soilToFertilizerMap = null;
        ArrayList<Integer[]> fertilizerToWaterMap = null;
        ArrayList<Integer[]> waterToLightMap = null;
        ArrayList<Integer[]> lightToTemperatureMap = null;
        ArrayList<Integer[]> temperatureToHumidityMap = null;
        ArrayList<Integer[]> humidityToLocationMap = null;

        // Parse the input into a usable format:
        for (int i = 0; i < inputStrings.size(); i++) {
            // Check for 'seeds':
            if (inputStrings.get(i).contains("seeds")) {
                seeds = parseSeeds(inputStrings, i);
            }

            // Check for "seed-to-soil":
            if (inputStrings.get(i).contains("seed-to-soil")) {
                seedToSoilMap = parseNumbers(inputStrings, i);
            }

            // Check for "soil-to-fertilizer":
            if (inputStrings.get(i).contains("soil-to-fertilizer")) {
                soilToFertilizerMap = parseNumbers(inputStrings, i);
            }

            // Check for "fertilizer-to-water":
            if (inputStrings.get(i).contains("fertilizer-to-water")) {
                fertilizerToWaterMap = parseNumbers(inputStrings, i);
            }

            // Check for "water-to-light":
            if (inputStrings.get(i).contains("water-to-light")) {
                waterToLightMap = parseNumbers(inputStrings, i);
            }

            // Check for "light-to-temperature":
            if (inputStrings.get(i).contains("light-to-temperature")) {
                lightToTemperatureMap = parseNumbers(inputStrings, i);
            }

            // Check for "temperature-to-humidity":
            if (inputStrings.get(i).contains("temperature-to-humidity")) {
                temperatureToHumidityMap = parseNumbers(inputStrings, i);
            }

            // Check for "humidity-to-location":
            if (inputStrings.get(i).contains("humidity-to-location")) {
                humidityToLocationMap = parseNumbers(inputStrings, i);
            }
        }

        // Test code:
        if (testing) {
            System.out.println(" seeds: " + seeds.toString());
            System.out.println(" seed-to-soil map: ");
            for (Integer[] intArr : seedToSoilMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" soil-to-fertilizer map: ");
            for (Integer[] intArr : soilToFertilizerMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" fertilizer-to-water map: ");
            for (Integer[] intArr : fertilizerToWaterMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" water-to-light map: ");
            for (Integer[] intArr : waterToLightMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" light-to-temperature map: ");
            for (Integer[] intArr : lightToTemperatureMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" temperature-to-humidity map: ");
            for (Integer[] intArr : temperatureToHumidityMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" humidity-to-location map: ");
            for (Integer[] intArr : humidityToLocationMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
        }

        // We want to find the lowest location number of any of the initial seeds:
        int lowestLocation = Integer.MAX_VALUE;

        // Start finding the location of each seed:
        for (int seed : seeds) {
            int soil = parseMap(seedToSoilMap, seed);
            int fertilizer = parseMap(soilToFertilizerMap, soil);
            int water = parseMap(fertilizerToWaterMap, fertilizer);
            int light = parseMap(waterToLightMap, water);
            int temperature = parseMap(lightToTemperatureMap, light);
            int humidity = parseMap(temperatureToHumidityMap, temperature);
            int location = parseMap(humidityToLocationMap, humidity);

            // Test code:
            if (testing) {
                System.out.println("Seed: " + seed);
                System.out.println(" Soil: " + soil);
                System.out.println(" Fertilizer: " + fertilizer);
                System.out.println(" Water: " + water);
                System.out.println(" Light: " + light);
                System.out.println(" Temperature: " + temperature);
                System.out.println(" Humidity: " + humidity);
                System.out.println(" Location: " + location);
            }

            // Update the lowest location if necesary:
            if (location < lowestLocation) {
                lowestLocation = location;
            }
        }

        // Print out the result:
        System.out.println("The lowest location number is: " + lowestLocation);
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

        // Create an array list of integer arrays to hold the map values:
        ArrayList<Integer[]> mapValues = new ArrayList<>();

        // For each input string following the input:
        for (int i = index; i < inputStrings.size(); i++) {
            // Assumption: There's always a blank line after the final map line.
            if (inputStrings.get(i).length() <= 1) {
                break;
            } else {
                // Split the input string:
                String[] integerStrings = inputStrings.get(i).split(" ");

                // Create an integer array to hold the output:
                ArrayList<Integer> integerArrayList = new ArrayList<>();

                // Parse each string into an integer:
                for (String intString : integerStrings) {
                    if (checkIntegerString(intString)) {
                        integerArrayList.add(Integer.parseInt(intString));
                    }
                }

                // Map values always come in 3:
                if (integerArrayList.size() == 3) {
                    // Convert the Array List to an array:
                    Integer[] integerArray = new Integer[integerArrayList.size()];
                    integerArray = integerArrayList.toArray(integerArray);

                    // Add the integer array to the map value array list:
                    mapValues.add(integerArray);
                }
            }
        }

        // Return the map value array list:
        return mapValues;
    }

    public static int parseMap(ArrayList<Integer[]> mapList, int input) {
        // Parses the given input to the given map, returning the mapped value.

        // Maps have three numbers: `destination range start`, `source range
        // start`, and `range length`. If the input value is within the source
        // range start and range length, it is mapped to the desintation range
        // start and offset by the difference from the source range start.
        // If the input does not fall within the source range, return the input
        // value unmodified.

        // There are multiple maps. I'm assuming that each map is exclusive,
        // and that the first map to match the input is the only one that will.

        // For each map:
        for (Integer[] map : mapList) {

            // For readability, use variables:
            int destinationRangeStart = map[0];
            int sourceRangeStart = map[1];
            int sourceRangeEnd = map[1] + map[2] - 1;

            // If the input is within the source range:
            if (input >= sourceRangeStart && input <= sourceRangeEnd) {
                // Adjust the input value accordingly:
                int adjustedInput = destinationRangeStart + (input - sourceRangeStart);
                return adjustedInput;
            }
        }

        // No valid map adjustment, return unmodified input:
        return input;
    }
}