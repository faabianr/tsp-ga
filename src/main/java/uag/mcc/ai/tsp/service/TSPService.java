package uag.mcc.ai.tsp.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TSPService {

    private final GenerationService generationService;

    public TSPService() {
        generationService = new GenerationService(new CityService(false));
    }

    public void startSimulation() {
        generationService.startSimulation();
    }

}
