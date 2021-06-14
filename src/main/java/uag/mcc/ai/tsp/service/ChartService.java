package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChartService {

    private static final String BEST_DISTANCE_CHART_TITLE = "Best Distances Per Generation";
    private static final String BEST_DISTANCE_CHART_SERIES_NAME = "Fitness Function";
    private static final String BEST_DISTANCE_CHART_X_AXIS_TITLE = "Generation";
    private static final String BEST_DISTANCE_CHART_Y_AXIS_TITLE = "Best Distance";

    private SwingWrapper<XYChart> swingWrapper;

    private final List<Double> bestDistancesChartXValues;
    private final List<Double> bestDistancesChartYValues;

    public ChartService() {
        this.bestDistancesChartXValues = new ArrayList<>();
        this.bestDistancesChartYValues = new ArrayList<>();
    }


    public void updateBestDistancesChart(double x, double y) {
        bestDistancesChartXValues.add(x);
        bestDistancesChartYValues.add(y);

        displayXYChart();
    }

    public void displayXYChart() {
        if (swingWrapper == null) {
            XYChart bestDistancesChart = new XYChartBuilder()
                    .width(1200).height(600).title(BEST_DISTANCE_CHART_TITLE)
                    .xAxisTitle(BEST_DISTANCE_CHART_X_AXIS_TITLE).yAxisTitle(BEST_DISTANCE_CHART_Y_AXIS_TITLE).build();

            bestDistancesChart.addSeries(BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartXValues, bestDistancesChartYValues, null);

            swingWrapper = new SwingWrapper<>(bestDistancesChart);
            swingWrapper.displayChart();
        } else {
            swingWrapper.getXChartPanel().getChart().updateXYSeries(BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartXValues, bestDistancesChartYValues, null);
            swingWrapper.getXChartPanel().revalidate();
            swingWrapper.getXChartPanel().repaint();
        }

    }

}
