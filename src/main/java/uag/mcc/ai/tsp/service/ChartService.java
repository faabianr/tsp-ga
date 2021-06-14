package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ChartService {

    private static final String X = "x";
    private static final String Y = "y";

    private static final String BEST_DISTANCE_CHART_TITLE = "Best Distances Per Generation";
    private static final String BEST_DISTANCE_CHART_SERIES_NAME = "Fitness Function";
    private static final String BEST_DISTANCE_CHART_X_AXIS_TITLE = "Generation";
    private static final String BEST_DISTANCE_CHART_Y_AXIS_TITLE = "Best Distance";

    private SwingWrapper<XYChart> swingWrapper;

    private final Map<String, List<Double>> bestDistancesChartValues;

    public ChartService() {
        this.bestDistancesChartValues = new HashMap<>();
        this.bestDistancesChartValues.put(X, new ArrayList<>());
        this.bestDistancesChartValues.put(Y, new ArrayList<>());
    }


    public void updateBestDistancesChart(double x, double y) {
        bestDistancesChartValues.get(X).add(x);
        bestDistancesChartValues.get(Y).add(y);

        displayCharts();
    }

    public void displayCharts() {
        if (swingWrapper == null) {
            XYChart bestDistancesChart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                    .width(1200).height(600).title(BEST_DISTANCE_CHART_TITLE)
                    .xAxisTitle(BEST_DISTANCE_CHART_X_AXIS_TITLE).yAxisTitle(BEST_DISTANCE_CHART_Y_AXIS_TITLE).build();

            bestDistancesChart.getStyler().setMarkerSize(0);
            bestDistancesChart.getStyler().setToolTipsEnabled(true);
            bestDistancesChart.getStyler().setToolTipFont(new Font("Verdana", Font.PLAIN, 10));
            bestDistancesChart.getStyler().setToolTipHighlightColor(Color.BLACK);
            bestDistancesChart.getStyler().setToolTipBorderColor(Color.DARK_GRAY);
            bestDistancesChart.getStyler().setToolTipBackgroundColor(Color.LIGHT_GRAY);
            bestDistancesChart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);

            bestDistancesChart.addSeries(BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartValues.get(X), bestDistancesChartValues.get(Y), null);

            swingWrapper = new SwingWrapper<>(bestDistancesChart);
            swingWrapper.displayChart();
        } else {
            swingWrapper.getXChartPanel().getChart()
                    .updateXYSeries(
                            BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartValues.get(X), bestDistancesChartValues.get(Y), null
                    );
            swingWrapper.getXChartPanel().revalidate();
            swingWrapper.getXChartPanel().repaint();
        }

    }

}
