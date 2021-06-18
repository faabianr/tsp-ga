package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;
import uag.mcc.ai.tsp.util.RandomizeUtils;

@Slf4j
public class ReproductiveMethodBySwapping implements ReproductiveMethod {

    private static final String REPRODUCTIVE_METHOD_NAME = "Sub-arrays Swapping Reproductive Method";

    @Override
    public int[] apply(int[] array) {
        log.info("applying reproductive method by swapping two sub-arrays");

        int chunkLength = RandomizeUtils.randomNumberBetweenInclusiveRange(1, array.length / 2);

        int index1;

        if (chunkLength >= array.length / 3) {
            index1 = RandomizeUtils.randomNumberBetweenInclusiveRange(0, array.length - (chunkLength * 2));
        } else {
            index1 = RandomizeUtils.randomNumberBetweenInclusiveRange(0, array.length - chunkLength);
        }

        log.info("chunkLength = {}", chunkLength);

        log.info("index1 = {}", index1);

        int index2;

        if (index1 > chunkLength) {
            int min = 0;
            int max = index1 - chunkLength;
            log.info("index1({}) > chunkLength({}): range with values min = {}, max = {}", index1, chunkLength, min, max);
            index2 = RandomizeUtils.randomNumberBetweenInclusiveRange(min, max);
        } else {
            int min = index1 + chunkLength;
            int max = array.length - chunkLength;
            log.info("index1({}) < chunkLength({}): range with values min = {}, max = {}", index1, chunkLength, min, max);
            index2 = RandomizeUtils.randomNumberBetweenInclusiveRange(min, max);
        }

        log.info("index2 = {}", index2);
        log.info("original array = {}", array);

        int temp;

        for (int i = 0; i < chunkLength; i++) {
            temp = array[index1 + i];
            array[index1 + i] = array[index2 + i];
            array[index2 + i] = temp;
        }

        log.info("swapped array = {}", array);

        return array;
    }

    @Override
    public String getName() {
        return REPRODUCTIVE_METHOD_NAME;
    }

}