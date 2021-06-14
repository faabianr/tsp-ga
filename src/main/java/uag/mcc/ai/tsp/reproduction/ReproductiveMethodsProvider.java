package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.util.RandomizeUtils;

@Slf4j
public class ReproductiveMethodsProvider {

    private final ReproductiveMethod[] reproductiveMethods;

    public ReproductiveMethodsProvider() {
        this.reproductiveMethods = new ReproductiveMethod[2];
        log.trace("registering ReproductiveMethodBySwapping at position 0");
        log.trace("registering ReproductiveMethodByReversion at position 1");
        reproductiveMethods[0] = new ReproductiveMethodBySwapping();
        reproductiveMethods[1] = new ReproductiveMethodByReversion();
    }

    public ReproductiveMethod getRandomReproductiveMethod() {
        int randomIndex = RandomizeUtils.randomNumberZeroOrOne();
        ReproductiveMethod randomlyChosenReproductiveMethod = reproductiveMethods[randomIndex];
        log.trace("random selection of reproductive method - randomIndex={}, methodName={}",
                randomIndex, randomlyChosenReproductiveMethod.getName());
        return randomlyChosenReproductiveMethod;
    }

}
