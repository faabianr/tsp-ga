package uag.mcc.ai.tsp;

import lombok.Getter;

@Getter
public class Trip {

    private int[] route;
    private double totalDistance;

    public void setRoute(int[] route) {
        this.route = route;
        this.totalDistance = 0;
    }

}
