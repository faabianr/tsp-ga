package uag.mcc.ai.tsp.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static uag.mcc.ai.tsp.util.RandomizeUtils.randomNumber;

@Slf4j
@Data
public class Generation {

    public static final int TOTAL_TRIPS_PER_GENERATION = 100;
    private static final int TOURNAMENT_PARTICIPANTS_NUMBER = 5;

    private Trip[] trips;
    private Trip bestTrip;

    public Generation(Trip[] trips) {
        this.trips = trips;
    }

    public Trip getBestTrip() {
        return bestTrip;
    }

    public void startTournament() {
        for (int i = 0; i < 1; i++) {
            log.info("Picking {} participants for tournament #{}", TOURNAMENT_PARTICIPANTS_NUMBER, i);
            Set<Trip> participants = pickRandomParticipants();
            participants.forEach(participant ->
                    log.info("Participant: {}", participant)
            );

            Trip bestParticipant = findBestParticipant(participants);
            log.info("best participant of tournament #{}: {}", i, bestParticipant);
        }
    }

    private Trip findBestParticipant(Set<Trip> participants) {
        return participants.stream()
                .min(Comparator.comparing(Trip::getTotalDistance))
                .orElseThrow(NoSuchElementException::new);
    }

    private Set<Trip> pickRandomParticipants() {
        Set<Trip> participants = new HashSet<>();

        while (participants.size() < TOURNAMENT_PARTICIPANTS_NUMBER) {
            Trip participant = trips[randomNumber(TOTAL_TRIPS_PER_GENERATION)];
            participants.add(participant);
        }

        return participants;
    }

}
