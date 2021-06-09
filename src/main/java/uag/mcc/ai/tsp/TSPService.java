package uag.mcc.ai.tsp;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static uag.mcc.ai.tsp.CityService.TOTAL_CITIES;

@Slf4j
public class TSPService {

    private static final int TOTAL_TRIPS = 100;

    private final List<Trip> trips;
    CityService cityService;

    public TSPService(CityService cityService) {
        this.cityService = cityService;
        this.trips = new ArrayList<>();
    }

    public void generateInitialTrips() {

        for (int i = 0; i < TOTAL_TRIPS; i++) {
            int[] route = RandomizeUtils.generateRandomRoute(TOTAL_CITIES);
            double totalDistance = calculateTotalRouteDistance(route);
            Trip trip = new Trip(route, totalDistance);
            trips.add(trip);
        }

        log.info("Generated {} random trips", trips.size());

        for (int i = 0; i < TOTAL_TRIPS; i++) {
            log.info("Trip {}: {}", i + 1, trips.get(i));
        }
    }

    private double calculateTotalRouteDistance(int[] route) {
        double totalDistance = 0;

        for (int i = 0; i < route.length; i++) {
            City currentCity = cityService.getCity(route[i]);
            City targetCity;
            if (i + 1 < route.length) {
                targetCity = cityService.getCity(i + 1);
            } else {
                targetCity = cityService.getCity(route[0]);
            }
            totalDistance += currentCity.calculateDistanceToCity(targetCity);
        }

        return totalDistance;
    }

}
