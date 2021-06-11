package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.model.City;
import uag.mcc.ai.tsp.model.Generation;
import uag.mcc.ai.tsp.model.Trip;
import uag.mcc.ai.tsp.util.RandomizeUtils;

import static uag.mcc.ai.tsp.service.CityService.TOTAL_CITIES;

@Slf4j
public class TSPService {

    private final int generationCount;
    private final CityService cityService;
    private final Generation currentGeneration;

    public TSPService(CityService cityService) {
        this.generationCount = 1;
        this.cityService = cityService;
        this.currentGeneration = buildInitialGeneration();
    }

    public int getGenerationCount() {
        return this.generationCount;
    }

    public void startSimulation() {
        currentGeneration.startTournament();
    }

    private Generation buildInitialGeneration() {
        Trip[] trips = new Trip[Generation.TOTAL_TRIPS_PER_GENERATION];

        for (int i = 0; i < Generation.TOTAL_TRIPS_PER_GENERATION; i++) {
            int[] route = RandomizeUtils.generateRandomRoute(TOTAL_CITIES);
            double totalDistance = calculateTotalRouteDistance(route);
            Trip trip = new Trip(i, route, totalDistance);
            trips[i] = trip;
        }

        log.info("Generated {} random trips", trips.length);

        for (int i = 0; i < Generation.TOTAL_TRIPS_PER_GENERATION; i++) {
            log.info("Trip {}: {}", i + 1, trips[i]);
        }

        return new Generation(trips);
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
