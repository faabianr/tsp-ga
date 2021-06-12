package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReproductiveMethodBySwapping implements ReproductiveMethod {

    private static final String REPRODUCTIVE_METHOD_NAME = "Sub-arrays Swapping Reproductive Method";

    @Override
    public int[] apply(int[] array) {
        log.info("applying reproductive method by swapping two sub-arrays");
        return new int[0];
    }

    @Override
    public String getName() {
        return REPRODUCTIVE_METHOD_NAME;
    }

}
