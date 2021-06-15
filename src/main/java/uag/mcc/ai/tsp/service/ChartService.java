package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import uag.mcc.ai.tsp.model.City;

import java.awt.*;
import java.util.List;
import java.util.*;

@Slf4j
public class ChartService {

    private static final String X = "x";
    private static final String Y = "y";

    private static final String ROUTE_CHART_TITLE = "Best Route";
    private static final String ROUTE_CHART_SERIES_NAME = "Route";
    private static final String ROUTE_CHART_X_AXIS_TITLE = "x";
    private static final String ROUTE_CHART_Y_AXIS_TITLE = "y";
    private static final int ROUTE_CHART_INDEX = 0;

    private static final String BEST_DISTANCE_CHART_TITLE = "Best Distances Per Generation";
    private static final String BEST_DISTANCE_CHART_SERIES_NAME = "Fitness Function";
    private static final String BEST_DISTANCE_CHART_X_AXIS_TITLE = "Generation";
    private static final String BEST_DISTANCE_CHART_Y_AXIS_TITLE = "Best Distance";
    private static final int BEST_DISTANCE_CHART_INDEX = 1;

    private final CityService cityService;
    private SwingWrapper<XYChart> swingWrapper;

    private final Map<String, List<Double>> bestDistancesChartValues;
    private final Map<String, List<Double>> routeChartValues;

    public ChartService(CityService cityService) {
        this.cityService = cityService;
        this.bestDistancesChartValues = new HashMap<>();
        this.routeChartValues = new HashMap<>();
        this.bestDistancesChartValues.put(X, new ArrayList<>());
        this.bestDistancesChartValues.put(Y, new ArrayList<>());
        this.routeChartValues.put(X, new ArrayList<>());
        this.routeChartValues.put(Y, new ArrayList<>());
    }

    public void setXYForRouteChart(int[] route) {
        routeChartValues.put(X, new ArrayList<>());
        routeChartValues.put(Y, new ArrayList<>());
        for (int j : route) {
            City city = cityService.getCity(j);
            routeChartValues.get(X).add((double) city.getX());
            routeChartValues.get(Y).add((double) city.getY());
        }
    }

    public void displayCharts(double x, double y, int[] route) {
        bestDistancesChartValues.get(X).add(x);
        bestDistancesChartValues.get(Y).add(y);

        setXYForRouteChart(route);

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

            XYChart routeChart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                    .width(1200).height(600).title(ROUTE_CHART_TITLE)
                    .xAxisTitle(ROUTE_CHART_X_AXIS_TITLE).yAxisTitle(ROUTE_CHART_Y_AXIS_TITLE).build();

            routeChart.addSeries(ROUTE_CHART_SERIES_NAME, routeChartValues.get(X), routeChartValues.get(Y), null);


            routeChart.getStyler().setMarkerSize(10);
            routeChart.getStyler().setSeriesColors(new Color[]{ Color.ORANGE });
            routeChart.getStyler().setToolTipsEnabled(true);
            routeChart.getStyler().setToolTipFont(new Font("Verdana", Font.PLAIN, 10));
            routeChart.getStyler().setToolTipHighlightColor(Color.BLACK);
            routeChart.getStyler().setToolTipBorderColor(Color.DARK_GRAY);
            routeChart.getStyler().setToolTipBackgroundColor(Color.LIGHT_GRAY);
            routeChart.getStyler().setToolTipsAlwaysVisible(true);

            swingWrapper = new SwingWrapper<>(Arrays.asList(routeChart, bestDistancesChart));
            swingWrapper.displayChartMatrix();
        } else {

            swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).getChart()
                    .updateXYSeries(
                            ROUTE_CHART_SERIES_NAME, routeChartValues.get(X), routeChartValues.get(Y), null
                    );

            swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).getChart().setTitle(String.format("Generation: %s, Best Route: %s", x, Arrays.toString(route)));
            swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).revalidate();
            swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).repaint();


            swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).getChart()
                    .updateXYSeries(
                            BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartValues.get(X), bestDistancesChartValues.get(Y), null
                    );

            swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).getChart().setTitle(String.format("Generation: %s, Best Distance: %s", x, y));

            swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).revalidate();
            swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).repaint();


        }

    }

}
