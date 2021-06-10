package uag.mcc.ai.tsp.model;

import lombok.Data;

@Data
public class Generation {

    public static final int TOTAL_TRIPS_PER_GENERATION = 100;

    private Trip[] trips;
    private int indexOfBestTrip;

    public Generation(Trip[] trips) {
        this.trips = trips;
    }

}
