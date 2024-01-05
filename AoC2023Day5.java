/* Advent of Code 2023, Day 5: If You Give A Seed A Fertilizer
 * Adrien Abbey, Jan. 2024
 * Part One Solution: 218513636
 * Part Two Solution: 81956384
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class AoC2023Day5 {
    /* Global Variables */
    public static String inputFileName = "input.txt";
    public static boolean testing = false;
    public static boolean partTwo = true;

    // Create array lists to hold relevant data:
    public static ArrayList<Long> seeds = null;
    public static ArrayList<Long[]> seedToSoilMap = null;
    public static ArrayList<Long[]> soilToFertilizerMap = null;
    public static ArrayList<Long[]> fertilizerToWaterMap = null;
    public static ArrayList<Long[]> waterToLightMap = null;
    public static ArrayList<Long[]> lightToTemperatureMap = null;
    public static ArrayList<Long[]> temperatureToHumidityMap = null;
    public static ArrayList<Long[]> humidityToLocationMap = null;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException {
        // Load the input into an array of strings:
        ArrayList<String> inputStrings = loadInputStrings();

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
            for (Long[] intArr : seedToSoilMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" soil-to-fertilizer map: ");
            for (Long[] intArr : soilToFertilizerMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" fertilizer-to-water map: ");
            for (Long[] intArr : fertilizerToWaterMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" water-to-light map: ");
            for (Long[] intArr : waterToLightMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" light-to-temperature map: ");
            for (Long[] intArr : lightToTemperatureMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" temperature-to-humidity map: ");
            for (Long[] intArr : temperatureToHumidityMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
            System.out.println(" humidity-to-location map: ");
            for (Long[] intArr : humidityToLocationMap) {
                System.out.println(" - " + Arrays.toString(intArr));
            }
        }

        // We want to find the lowest location number of any of the initial seeds:
        long lowestLocation = Long.MAX_VALUE;

        // If using Part One's method:
        if (!partTwo) {
            // Start finding the location of each seed:
            for (long seed : seeds) {
                long soil = parseMap(seedToSoilMap, seed);
                long fertilizer = parseMap(soilToFertilizerMap, soil);
                long water = parseMap(fertilizerToWaterMap, fertilizer);
                long light = parseMap(waterToLightMap, water);
                long temperature = parseMap(lightToTemperatureMap, light);
                long humidity = parseMap(temperatureToHumidityMap, temperature);
                long location = parseMap(humidityToLocationMap, humidity);

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
        } else {
            // Using part two's method.

            // Configure a Future object to grab results from Callable threads:
            int threadCount = seeds.size() / 2;
            System.out.println(" Using " + threadCount + " threads.");
            FutureTask<Long>[] threadedTasks = new FutureTask[threadCount];

            // Treat the seed values as pairs.
            // Start with the first index value, adding two each round:
            for (int i = 0; i < threadCount; i += 1) {
                // The second value of a seed pair is the range of seed values
                // to calculate. Calculate every seed value, from the starting
                // seed to the ending seed:

                // Create a Callable object to thread each seed pair:
                // https://www.geeksforgeeks.org/callable-future-java/
                Callable<Long> callable = new AoC2023Day5Callable(seeds.get(i * 2),
                        seeds.get(i * 2) + seeds.get(i * 2 + 1));
                threadedTasks[i] = new FutureTask<Long>(callable);
                Thread t = new Thread(threadedTasks[i]);
                t.start();
            }

            // Grab the results and determine the lowest location:
            for (int i = 0; i < seeds.size() / 2; i++) {
                if (threadedTasks[i].get() < lowestLocation) {
                    lowestLocation = threadedTasks[i].get();
                }
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
        // Checks if the given string is a valid Long.
        // https://www.geeksforgeeks.org/check-if-a-given-string-is-a-valid-number-integer-or-floating-point-in-java/

        try {
            Long.parseLong(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static ArrayList<Long> parseSeeds(ArrayList<String> inputStrings, int index) {
        // Parses the seed integers at the given index, returning an array list.

        // Split the input string:
        String[] firstSplit = inputStrings.get(index).split(":");
        String[] seedStrings = firstSplit[1].split(" ");

        // Create an array list to contain valid seed integers:
        ArrayList<Long> seedIntegers = new ArrayList<>();

        // Convert the seed strings into integers:
        for (String seed : seedStrings) {
            // Validate the input:
            if (checkIntegerString(seed)) {
                seedIntegers.add(Long.parseLong(seed));
            }
        }

        // Return the seed Long array list:
        return seedIntegers;
    }

    public static ArrayList<Long[]> parseNumbers(ArrayList<String> inputStrings, int index) {
        // Parses the number maps following the given index.

        // Create an array list of Long arrays to hold the map values:
        ArrayList<Long[]> mapValues = new ArrayList<>();

        // For each input string following the input:
        for (int i = index; i < inputStrings.size(); i++) {
            // Assumption: There's always a blank line after the final map line.
            if (inputStrings.get(i).length() <= 1) {
                break;
            } else {
                // Split the input string:
                String[] integerStrings = inputStrings.get(i).split(" ");

                // Create an Long array to hold the output:
                ArrayList<Long> integerArrayList = new ArrayList<>();

                // Parse each string into an Long:
                for (String intString : integerStrings) {
                    if (checkIntegerString(intString)) {
                        integerArrayList.add(Long.parseLong(intString));
                    }
                }

                // Map values always come in 3:
                if (integerArrayList.size() == 3) {
                    // Convert the Array List to an array:
                    Long[] integerArray = new Long[integerArrayList.size()];
                    integerArray = integerArrayList.toArray(integerArray);

                    // Add the Long array to the map value array list:
                    mapValues.add(integerArray);
                }
            }
        }

        // Return the map value array list:
        return mapValues;
    }

    public static long parseMap(ArrayList<Long[]> mapList, long input) {
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
        for (Long[] map : mapList) {

            // For readability, use variables:
            long destinationRangeStart = map[0];
            long sourceRangeStart = map[1];
            long sourceRangeEnd = map[1] + map[2] - 1;

            // If the input is within the source range:
            if (input >= sourceRangeStart && input <= sourceRangeEnd) {
                // Adjust the input value accordingly:
                long adjustedInput = destinationRangeStart + (input - sourceRangeStart);
                return adjustedInput;
            }
        }

        // No valid map adjustment, return unmodified input:
        return input;
    }
}