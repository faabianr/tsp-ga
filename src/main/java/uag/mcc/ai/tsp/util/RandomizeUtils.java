package uag.mcc.ai.tsp.util;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.model.City;

import java.util.Random;

@Slf4j
public class RandomizeUtils {

    private RandomizeUtils() {
    }

    /**
     * Returns a random number between a range. The min value is inclusive and the max one is exclusive.
     *
     * @param min the min number of the range. This number is inclusive.
     * @param max the max number of the range. This number is exclusive.
     * @return a random int generated using the given min and max values.
     */
    public static int randomNumberBetweenRange(int min, int max) {
        Random random = new Random();
        return random.ints(min, max).findAny().getAsInt();
    }

    /**
     * Returns a random number between a range. The min and max values are inclusive.
     *
     * @param min the min number of the range. This number is inclusive.
     * @param max the max number of the range. This number is inclusive.
     * @return a random int generated using the given min and max values.
     */
    public static int randomNumberBetweenInclusiveRange(int min, int max) {
        return randomNumberBetweenRange(min, max + 1);
    }

    public static int randomNumberZeroOrOne() {
        Random random = new Random();
        return (random.nextInt(2) + 1) - 1;
    }

    public static int randomNumber(int maxNumber) {
        return (int) (Math.random() * maxNumber);
    }

    public static City createCityWithRandomCoordinates() {
        int x = (int) (Math.random() * 100);
        int y = (int) (Math.random() * 100);
        return City.builder().x(x).y(y).build();
    }

    public static int[] generateRandomRoute(int numberOfCities) {
        int[] array = new int[numberOfCities];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        arrayRandomPermutation(array);
        return array;
    }

    public static void arrayRandomPermutation(int[] array) {
        Random r = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

    }

}
