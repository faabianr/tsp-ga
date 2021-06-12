package uag.mcc.ai.tsp.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Generation {

    private Trip[] trips;
    private Trip bestTrip;

    public Generation(Trip[] trips) {
        this.trips = trips;
    }

    public Trip getBestTrip() {
        return bestTrip;
    }

}
