/* Advent of Code 2023, Day 5: If You Give A Seed A Fertilizer
 * Adrien Abbey, Jan. 2024
 * Multithreading Class
 */

// Day 5 Part Two has a LOT of calculations, making it rather slow.
// Fortunately, it seems easy enough to implement multithreading!

// https://www.geeksforgeeks.org/callable-future-java/

import java.util.concurrent.Callable;

public class AoC2023Day5Callable implements Callable<Long> {

    /* Fields */
    long startSeed;
    long endSeed;

    /* Constructor */
    public AoC2023Day5Callable(long startSeed, long endSeed) {
        // Store data for future run:
        this.startSeed = startSeed;
        this.endSeed = endSeed;
    }

    @Override
    public Long call() {
        // Searches the given seed range for the lowest location.

        // Track the lowest location number found:
        long lowestLocation = Long.MAX_VALUE;

        // For each seed number given:
        for (long seed = startSeed; seed <= endSeed; seed++) {
            long soil = AoC2023Day5.parseMap(AoC2023Day5.seedToSoilMap, seed);
            long fertilizer = AoC2023Day5.parseMap(AoC2023Day5.soilToFertilizerMap, soil);
            long water = AoC2023Day5.parseMap(AoC2023Day5.fertilizerToWaterMap, fertilizer);
            long light = AoC2023Day5.parseMap(AoC2023Day5.waterToLightMap, water);
            long temperature = AoC2023Day5.parseMap(AoC2023Day5.lightToTemperatureMap, light);
            long humidity = AoC2023Day5.parseMap(AoC2023Day5.temperatureToHumidityMap, temperature);
            long location = AoC2023Day5.parseMap(AoC2023Day5.humidityToLocationMap, humidity);

            if (location < lowestLocation) {
                lowestLocation = location;
            }
        }

        // Return the lowest location found:
        return lowestLocation;
    }
}
