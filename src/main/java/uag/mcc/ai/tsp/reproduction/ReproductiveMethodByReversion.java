package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.util.RandomizeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ReproductiveMethodByReversion implements ReproductiveMethod {

    private static final String REPRODUCTIVE_METHOD_NAME = "Sub-array Reversion Reproductive Method";

    @Override
    public int[] apply(int[] array) {
        log.debug("applying reproductive method by reversion of sub-array");

        // always respecting first and last elements
        int randomLength = RandomizeUtils.randomNumberBetweenRange(1, array.length - 1);

        List<Integer> subArray = new ArrayList<>();

        for (int i = 1; i < randomLength; i++) {
            subArray.add(array[i]);
        }

        log.debug("original array: {}", array);
        log.debug("sub-array (length={}): {}", randomLength, subArray);
        Collections.reverse(subArray);
        log.debug("reversed sub-array: {}", subArray);

        for (int i = 1; i < randomLength; i++) {
            array[i] = subArray.get(i - 1);
        }

        log.debug("merged array: {}", array);

        return array;
    }

    @Override
    public String getName() {
        return REPRODUCTIVE_METHOD_NAME;
    }

}
