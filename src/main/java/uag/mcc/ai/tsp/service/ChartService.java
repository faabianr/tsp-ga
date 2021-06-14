package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import uag.mcc.ai.tsp.model.Generation;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChartService {

    XYChart bestDistancesChart;

    private final List<Double> bestDistancesChartXValues;
    private final List<Double> bestDistancesChartYValues;

    public ChartService() {
        this.bestDistancesChartXValues = new ArrayList<>();
        this.bestDistancesChartYValues = new ArrayList<>();
    }

    public void updateBestRouteSChart(Generation generation) {

    }

    public void updateBestDistancesChart(double x, double y) {
        this.bestDistancesChartXValues.add(x);
        this.bestDistancesChartYValues.add(y);
    }

    public void displayBestDistancesChart() {
        bestDistancesChart = QuickChart.getChart("Best Distances Per Generation", "X", "Y", "y(x)",
                bestDistancesChartXValues, bestDistancesChartYValues);
        new SwingWrapper(bestDistancesChart).displayChart();
    }

}
