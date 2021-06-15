package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.model.City;
import uag.mcc.ai.tsp.model.Generation;
import uag.mcc.ai.tsp.model.Trip;
import uag.mcc.ai.tsp.reproduction.ReproductiveMethod;
import uag.mcc.ai.tsp.reproduction.ReproductiveMethodsProvider;
import uag.mcc.ai.tsp.util.RandomizeUtils;

import java.util.*;

import static uag.mcc.ai.tsp.service.CityService.TOTAL_CITIES;
import static uag.mcc.ai.tsp.util.RandomizeUtils.randomNumber;

@Slf4j
public class GenerationService {

    private static final int TOTAL_GENERATIONS = 100;
    private static final int TOTAL_TRIPS_PER_GENERATION = 100;
    private static final int TOURNAMENT_PARTICIPANTS_NUMBER = 5;

    private int generationCount;
    private final CityService cityService;
    private final ChartService chartService;
    private final ReproductiveMethodsProvider reproductiveMethodsProvider;
    private final Generation currentGeneration;

    public GenerationService(CityService cityService, ReproductiveMethodsProvider reproductiveMethodsProvider, ChartService chartService) {
        this.generationCount = 0;
        this.cityService = cityService;
        this.chartService = chartService;
        this.reproductiveMethodsProvider = reproductiveMethodsProvider;
        this.currentGeneration = buildInitialGeneration();

    }

    public void startSimulation() {
        log.debug("starting simulation of {} generations using {} cities, {} trips per generation and {} participants per tournament",
                TOTAL_GENERATIONS, TOTAL_CITIES, TOTAL_TRIPS_PER_GENERATION, TOURNAMENT_PARTICIPANTS_NUMBER);

        for (int i = 0; i < TOTAL_GENERATIONS; i++) {
            generationCount = i;
            log.debug("starting simulation of generation #{}", generationCount);
            startTournamentsForCurrentGeneration();
            log.debug("best of generation #{}: {}", generationCount, currentGeneration.getBestTrip());

            try {
                chartService.displayCharts(generationCount + 1, currentGeneration.getBestTrip().getTotalDistance(), currentGeneration.getBestTrip().getRoute());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void startTournamentsForCurrentGeneration() {

        Trip[] children = new Trip[TOTAL_TRIPS_PER_GENERATION];

        for (int i = 0; i < TOTAL_TRIPS_PER_GENERATION; i++) {
            log.debug("Picking {} participants for tournament #{}", TOURNAMENT_PARTICIPANTS_NUMBER, i);
            Set<Trip> participants = pickRandomParticipants(currentGeneration.getTrips());
            participants.forEach(participant ->
                    log.debug("Participant: {}", participant)
            );

            Trip bestParticipant = findBestParticipant(participants);
            log.debug("best participant of tournament #{}: {}", i, bestParticipant);

            int[] route = applyReproduction(bestParticipant.getRoute());
            Trip child = new Trip(i, route, calculateTotalRouteDistance(route));
            log.debug("registering child of best participant = {}", child);
            children[i] = child;
        }

        log.debug("replacing parent generation with children");
        currentGeneration.setTrips(children);
        setBestOfGeneration(currentGeneration);
    }

    private void setBestOfGeneration(Generation generation) {
        Set<Trip> generationTrips = new HashSet<>();
        Collections.addAll(generationTrips, generation.getTrips());
        Trip bestOfGeneration = findBestParticipant(generationTrips);
        currentGeneration.setBestTrip(bestOfGeneration);
    }

    private void printTrips(Trip[] trips) {
        for (int i = 0; i < trips.length; i++) {
            log.debug("Trip {}: {}", i + 1, trips[i]);
        }
    }

    private int[] applyReproduction(int[] route) {
        int[] newArray = route.clone();
        ReproductiveMethod reproductiveMethod = reproductiveMethodsProvider.getRandomReproductiveMethod();
        return reproductiveMethod.apply(newArray);
    }

    private Trip findBestParticipant(Set<Trip> participants) {
        return participants.stream()
                .min(Comparator.comparing(Trip::getTotalDistance))
                .orElseThrow(NoSuchElementException::new);
    }

    private Set<Trip> pickRandomParticipants(Trip[] trips) {
        Set<Trip> participants = new HashSet<>();

        while (participants.size() < TOURNAMENT_PARTICIPANTS_NUMBER) {
            Trip participant = trips[randomNumber(TOTAL_TRIPS_PER_GENERATION)];
            participants.add(participant);
        }

        return participants;
    }

    private Generation buildInitialGeneration() {
        Trip[] trips = new Trip[TOTAL_TRIPS_PER_GENERATION];

        for (int i = 0; i < TOTAL_TRIPS_PER_GENERATION; i++) {
            int[] route = RandomizeUtils.generateRandomRoute(TOTAL_CITIES);
            double totalDistance = calculateTotalRouteDistance(route);
            Trip trip = new Trip(i, route, totalDistance);
            trips[i] = trip;
        }

        log.debug("Generated {} random trips", trips.length);

        printTrips(trips);

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
