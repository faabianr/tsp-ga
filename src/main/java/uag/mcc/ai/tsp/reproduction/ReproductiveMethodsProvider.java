package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.util.RandomizeUtils;

@Slf4j
public class ReproductiveMethodsProvider {

    private final ReproductiveMethod[] reproductiveMethods;

    public ReproductiveMethodsProvider() {
        this.reproductiveMethods = new ReproductiveMethod[2];
        log.debug("registering ReproductiveMethodBySwapping at position 0");
        log.debug("registering ReproductiveMethodByReversion at position 1");
        reproductiveMethods[0] = new ReproductiveMethodByReversion();
        reproductiveMethods[1] = new ReproductiveMethodByReversion();
    }

    public ReproductiveMethod getRandomReproductiveMethod() {
        int randomIndex = RandomizeUtils.randomNumberZeroOrOne();
        ReproductiveMethod randomlyChosenReproductiveMethod = reproductiveMethods[randomIndex];
        log.debug("random selection of reproductive method - randomIndex={}, methodName={}",
                randomIndex, randomlyChosenReproductiveMethod.getName());
        return randomlyChosenReproductiveMethod;
    }

}
