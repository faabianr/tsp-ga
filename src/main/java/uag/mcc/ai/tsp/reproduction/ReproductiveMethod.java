package uag.mcc.ai.tsp.reproduction;

public interface ReproductiveMethod {

    int[] apply(int[] array);

    String getName();

}
