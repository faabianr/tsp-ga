package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.model.City;
import uag.mcc.ai.tsp.util.RandomizeUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CityService {

    public static final int TOTAL_CITIES = 20;

    private final Map<Integer, City> citiesMap;

    public CityService(boolean generateRandomCities) {
        this.citiesMap = new HashMap<>();

        if (generateRandomCities) {
            generateRandomCities();
        } else {
            generateStaticCities();
        }
        printGeneratedCities();
    }

    public void printGeneratedCities() {
        citiesMap.forEach((key, value) -> log.info(value.toString()));
    }

    private void generateStaticCities() {
        log.info("Generating static cities");

        citiesMap.put(0, new City(0, 1, 7));
        citiesMap.put(1, new City(1, 1, 1));
        citiesMap.put(2, new City(2, 2, 3));
        citiesMap.put(3, new City(3, 2, 5));
        citiesMap.put(4, new City(4, 3, 8));
        citiesMap.put(5, new City(5, 4, 1));
        citiesMap.put(6, new City(6, 4, 3));
        citiesMap.put(7, new City(7, 5, 4));
        citiesMap.put(8, new City(8, 5, 7));
        citiesMap.put(9, new City(9, 6, 5));
        citiesMap.put(10, new City(10, 7, 4));
        citiesMap.put(11, new City(11, 7, 7));
        citiesMap.put(12, new City(12, 8, 5));
        citiesMap.put(13, new City(13, 10, 2));
        citiesMap.put(14, new City(14, 10, 5));
        citiesMap.put(15, new City(15, 10, 7));
        citiesMap.put(16, new City(16, 10, 8));
        citiesMap.put(17, new City(17, 11, 1));
        citiesMap.put(18, new City(18, 12, 3));
        citiesMap.put(19, new City(19, 12, 4));

    }

    private void generateRandomCities() {
        log.info("Generating random cities");
        while (citiesMap.size() < TOTAL_CITIES) {
            City city = RandomizeUtils.createCityWithRandomCoordinates();
            if (!citiesMap.containsValue(city)) {
                int cityId = citiesMap.size();
                city.setId(cityId);
                citiesMap.put(cityId, city);
            }
        }

        log.info("Created {} unique cities with random coordinates", citiesMap.size());
    }

    public City getCity(int id) {
        return citiesMap.get(id);
    }

}
