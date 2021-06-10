package uag.mcc.ai.tsp;

import uag.mcc.ai.tsp.service.CityService;
import uag.mcc.ai.tsp.service.TSPService;

public class Application {

    public static void main(String[] args) {
        TSPService tspService = new TSPService(new CityService(false));
        tspService.generateInitialTrips();
    }

}
