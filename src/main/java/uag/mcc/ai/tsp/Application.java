package uag.mcc.ai.tsp;

public class Application {

    public static void main(String[] args) {
        TSPService tspService = new TSPService(new CityService());
        tspService.generateInitialTrips();
    }

}
