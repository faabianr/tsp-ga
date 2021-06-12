package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.model.City;
import uag.mcc.ai.tsp.model.Generation;
import uag.mcc.ai.tsp.model.Trip;
import uag.mcc.ai.tsp.reproduction.ReproductiveMethod;
import uag.mcc.ai.tsp.reproduction.ReproductiveMethodsProvider;
import uag.mcc.ai.tsp.util.RandomizeUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static uag.mcc.ai.tsp.service.CityService.TOTAL_CITIES;
import static uag.mcc.ai.tsp.util.RandomizeUtils.randomNumber;

@Slf4j
public class GenerationService {

    private static final int TOTAL_GENERATIONS = 100;
    private static final int TOTAL_TRIPS_PER_GENERATION = 100;
    private static final int TOURNAMENT_PARTICIPANTS_NUMBER = 5;

    private final int generationCount;
    private final CityService cityService;
    private final ReproductiveMethodsProvider reproductiveMethodsProvider;
    private final Generation currentGeneration;

    public GenerationService(CityService cityService, ReproductiveMethodsProvider reproductiveMethodsProvider) {
        generationCount = 0;
        this.cityService = cityService;
        this.reproductiveMethodsProvider = reproductiveMethodsProvider;
        this.currentGeneration = buildInitialGeneration();
    }

    public void startSimulation() {
        log.info("starting simulation of {} generations using {} cities, {} trips per generation and {} participants per tournament",
                TOTAL_GENERATIONS, TOTAL_CITIES, TOTAL_TRIPS_PER_GENERATION, TOURNAMENT_PARTICIPANTS_NUMBER);
        startTournamentsForCurrentGeneration();
    }

    public void startTournamentsForCurrentGeneration() {
        // TODO use TOTAL_TRIPS_PER_GENERATION
        for (int i = 0; i < 1; i++) {
            log.info("Picking {} participants for tournament #{}", TOURNAMENT_PARTICIPANTS_NUMBER, i);
            Set<Trip> participants = pickRandomParticipants(currentGeneration.getTrips());
            participants.forEach(participant ->
                    log.info("Participant: {}", participant)
            );

            Trip bestParticipant = findBestParticipant(participants);
            log.info("best participant of tournament #{}: {}", i, bestParticipant);
            bestParticipant.setRoute(applyReproduction(bestParticipant.getRoute()));
            log.info("registering child of best participant = {}", bestParticipant);
            currentGeneration.getTrips()[bestParticipant.getId()] = bestParticipant;
        }
    }

    private int[] applyReproduction(int[] route) {
        ReproductiveMethod reproductiveMethod = reproductiveMethodsProvider.getRandomReproductiveMethod();
        return reproductiveMethod.apply(route);
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

        log.info("Generated {} random trips", trips.length);

        for (int i = 0; i < TOTAL_TRIPS_PER_GENERATION; i++) {
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
