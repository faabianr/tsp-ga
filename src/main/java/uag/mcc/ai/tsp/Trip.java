package uag.mcc.ai.tsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class Trip {

    private int[] route;
    private double totalDistance;

}
