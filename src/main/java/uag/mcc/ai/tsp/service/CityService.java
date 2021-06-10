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
    }

    private void generateStaticCities() {
        log.info("Generating static cities");

        citiesMap.put(0, new City(0, 4, 3));
        citiesMap.put(1, new City(1, 3, 8));
        citiesMap.put(2, new City(2, 10, 8));
        citiesMap.put(3, new City(3, 2, 5));
        citiesMap.put(4, new City(4, 1, 7));
        citiesMap.put(5, new City(5, 10, 7));
        citiesMap.put(6, new City(6, 1, 1));
        citiesMap.put(7, new City(7, 7, 7));
        citiesMap.put(8, new City(8, 5, 4));
        citiesMap.put(9, new City(9, 2, 3));
        citiesMap.put(10, new City(10, 6, 5));
        citiesMap.put(11, new City(11, 7, 4));
        citiesMap.put(12, new City(12, 12, 4));
        citiesMap.put(13, new City(13, 8, 5));
        citiesMap.put(14, new City(14, 10, 2));
        citiesMap.put(15, new City(15, 5, 7));
        citiesMap.put(16, new City(16, 10, 5));
        citiesMap.put(17, new City(17, 12, 3));
        citiesMap.put(18, new City(18, 11, 1));
        citiesMap.put(19, new City(19, 4, 1));
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
        citiesMap.forEach((key, value) -> log.info(value.toString()));
    }

    public City getCity(int id) {
        return citiesMap.get(id);
    }

}
