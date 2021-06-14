package uag.mcc.ai.tsp.reproduction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReproductiveMethodBySwapping implements ReproductiveMethod {

    private static final String REPRODUCTIVE_METHOD_NAME = "Sub-arrays Swapping Reproductive Method";

    @Override
    public int[] apply(int[] array) {
        log.trace("applying reproductive method by swapping two sub-arrays");

        int startIndexSubArray1 = 1;
        int startIndexSubArray2 = (array.length / 2);
        int temp;

        log.trace("startIndexSubArray1 = {}, startIndexSubArray2 = {}", startIndexSubArray1, startIndexSubArray2);
        log.trace("original array = {}", array);

        temp = array[startIndexSubArray1];
        array[startIndexSubArray1] = array[startIndexSubArray2];
        array[startIndexSubArray2] = temp;

        temp = array[startIndexSubArray1 + 1];
        array[startIndexSubArray1 + 1] = array[startIndexSubArray2 + 1];
        array[startIndexSubArray2 + 1] = temp;

        log.trace("swapped array = {}", array);

        return array;
    }

    @Override
    public String getName() {
        return REPRODUCTIVE_METHOD_NAME;
    }

}