package uag.mcc.ai.tsp;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Trip {

    private int[] route;
    private double totalDistance;

    public Trip(int[] route) {
        this.route = route;
    }

    public void setRoute(int[] route) {
        this.route = route;
        this.totalDistance = 0;
    }

}
