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
        log.info("applying reproductive method by reversion of sub-array");

        // always respecting first and last 2 elements (max is exclusive, that's why we use -2 instead of -3)
        int randomLength = RandomizeUtils.randomNumberBetweenRange(1, array.length - 2);

        List<Integer> subArray = new ArrayList<>();

        for (int i = 1; i < randomLength; i++) {
            subArray.add(array[i]);
        }

        log.info("original array: {}", array);
        log.info("sub-array (length={}): {}", randomLength, subArray);
        Collections.reverse(subArray);
        log.info("reversed sub-array: {}", subArray);

        for (int i = 1; i < randomLength; i++) {
            array[i] = subArray.get(i - 1);
        }

        log.info("merged array: {}", array);

        return array;
    }

    @Override
    public String getName() {
        return REPRODUCTIVE_METHOD_NAME;
    }

}
