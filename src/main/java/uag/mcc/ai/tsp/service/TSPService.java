package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.reproduction.ReproductiveMethodsProvider;

@Slf4j
public class TSPService {

    private final GenerationService generationService;

    public TSPService() {
        CityService cityService = new CityService(false);
        generationService = new GenerationService(cityService, new ReproductiveMethodsProvider(), new ChartService(cityService));
    }

    public void startSimulation() {
        generationService.startSimulation();
    }

}
