package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;
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
    private double bestDistanceFound;
    private int[] bestRouteFound;
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

    private void updateBestDistanceAndRoute(double distance, int[] route) {
        if (bestDistanceFound == 0) {
            bestDistanceFound = distance;
        } else if (distance < bestDistanceFound) {
            bestDistanceFound = distance;
            bestRouteFound = route.clone();
        }
    }

    public void displayCharts(double x, double y, int[] route) {
        bestDistancesChartValues.get(X).add(x);
        bestDistancesChartValues.get(Y).add(y);

        setXYForRouteChart(route);
        updateBestDistanceAndRoute(y, route);

        if (swingWrapper == null) {
            XYChart bestDistancesChart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                    .width(1200).height(600).title(BEST_DISTANCE_CHART_TITLE)
                    .xAxisTitle(BEST_DISTANCE_CHART_X_AXIS_TITLE).yAxisTitle(BEST_DISTANCE_CHART_Y_AXIS_TITLE).build();

            bestDistancesChart.getStyler().setMarkerSize(5);
            bestDistancesChart.getStyler().setChartTitleFont(new Font("Verdana", Font.PLAIN, 12));
            bestDistancesChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            bestDistancesChart.getStyler().setSeriesMarkers(new Marker[]{SeriesMarkers.CIRCLE});
            bestDistancesChart.getStyler().setToolTipsEnabled(true);
            bestDistancesChart.getStyler().setPlotGridLinesVisible(false);
            bestDistancesChart.getStyler().setPlotBorderVisible(false);
            bestDistancesChart.getStyler().setToolTipFont(new Font("Verdana", Font.PLAIN, 10));
            bestDistancesChart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);

            bestDistancesChart.addSeries(BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartValues.get(X), bestDistancesChartValues.get(Y), null);

            XYChart routeChart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                    .width(1200).height(600).title(ROUTE_CHART_TITLE)
                    .xAxisTitle(ROUTE_CHART_X_AXIS_TITLE).yAxisTitle(ROUTE_CHART_Y_AXIS_TITLE).build();

            routeChart.addSeries(ROUTE_CHART_SERIES_NAME, routeChartValues.get(X), routeChartValues.get(Y), null);


            routeChart.getStyler().setMarkerSize(10);
            routeChart.getStyler().setChartTitleFont(new Font("Verdana", Font.PLAIN, 12));
            routeChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            routeChart.getStyler().setSeriesMarkers(new Marker[]{SeriesMarkers.DIAMOND});
            routeChart.getStyler().setSeriesLines(new BasicStroke[]{SeriesLines.SOLID});
            routeChart.getStyler().setPlotGridLinesVisible(false);
            routeChart.getStyler().setPlotBorderVisible(false);
            routeChart.getStyler().setSeriesColors(new Color[]{Color.RED});
            routeChart.getStyler().setToolTipsEnabled(true);
            routeChart.getStyler().setToolTipFont(new Font("Verdana", Font.PLAIN, 10));
            routeChart.getStyler().setToolTipsAlwaysVisible(true);

            swingWrapper = new SwingWrapper<>(Arrays.asList(routeChart, bestDistancesChart));
            swingWrapper.displayChartMatrix();
        } else {
            updateBestDistanceChart(x, y);
            updateRouteChart(route, x, null);
        }

    }

    public void updateBestDistanceChart(double x, double y) {
        swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).getChart()
                .updateXYSeries(
                        BEST_DISTANCE_CHART_SERIES_NAME, bestDistancesChartValues.get(X), bestDistancesChartValues.get(Y), null
                );

        swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).getChart().setTitle(String.format("Generation: %s, Best Distance of Generation: %s", x, y));

        swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).revalidate();
        swingWrapper.getXChartPanel(BEST_DISTANCE_CHART_INDEX).repaint();
    }

    public void updateRouteChart(int[] route, double x, String title) {
        if (title == null) {
            title = String.format("Generation: %s, Best Route: %s", x, Arrays.toString(route));
        }

        swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).getChart()
                .updateXYSeries(
                        ROUTE_CHART_SERIES_NAME, routeChartValues.get(X), routeChartValues.get(Y), null
                );

        swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).getChart().setTitle(title);
        swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).revalidate();
        swingWrapper.getXChartPanel(ROUTE_CHART_INDEX).repaint();
    }

    public void updateRouteChartWithBestOfGenerations() {
        String title = String.format("BestDist: %s, Route: %s", bestDistanceFound, Arrays.toString(bestRouteFound));
        updateRouteChart(bestRouteFound, 0, title);
    }

}
