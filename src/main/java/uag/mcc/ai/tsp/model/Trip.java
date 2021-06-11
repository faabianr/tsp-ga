package uag.mcc.ai.tsp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class Trip {

    private int id;
    private int[] route;
    private double totalDistance;

}
