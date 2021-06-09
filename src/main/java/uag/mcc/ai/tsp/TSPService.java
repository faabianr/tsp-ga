package uag.mcc.ai.tsp;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TSPService {

    private static final int TOTAL_CITIES = 20;

    private final List<Trip> trips;
    private final Map<Integer, City> citiesMap;

    public TSPService() {
        this.trips = new ArrayList<>();
        this.citiesMap = new HashMap<>();
    }

    public void generateCities() {

        while (citiesMap.size() < TOTAL_CITIES) {
            City city = RandomizeUtils.createCityWithRandomCoordinates();
            if (!citiesMap.containsValue(city)) {
                int cityId = citiesMap.size() + 1;
                city.setId(cityId);
                citiesMap.put(cityId, city);
            }
        }

        log.info("Created {} unique cities with random coordinates", citiesMap.size());
        citiesMap.forEach((key, value) -> log.info(value.toString()));
    }

}
