package uag.mcc.ai.tsp;

import java.util.Random;

public class RandomizeUtils {

    private RandomizeUtils() {
    }

    public static City createCityWithRandomCoordinates() {
        int x = (int) (Math.random() * 100);
        int y = (int) (Math.random() * 100);
        return City.builder().x(x).y(y).build();
    }

    public static int[] generateRandomRoute(int numberOfCities) {
        int[] array = new int[numberOfCities];

        // creating the array with numbers from 1 - 20 that represent the
        // 20 cities
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        // since the array is sorted, we need to randomly permute it
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
