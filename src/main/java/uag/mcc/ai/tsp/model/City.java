package uag.mcc.ai.tsp.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@ToString
@Builder
@Data
public class City {

    private int id;
    private int x;
    private int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return x == city.x && y == city.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double calculateDistanceToCity(City targetCity) {
        int x = Math.abs(targetCity.getX() - getX());
        int y = Math.abs(targetCity.getY() - getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

}
