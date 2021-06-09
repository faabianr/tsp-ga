package uag.mcc.ai.tsp;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CityService {

    public static final int TOTAL_CITIES = 20;

    private final Map<Integer, City> citiesMap;

    public CityService() {
        this.citiesMap = new HashMap<>();
        generateCities();
    }

    private void generateCities() {

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
