package pl.aaugustyniak.neural.elements.helpers;

import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * n-tka opisująca warstwę sieci
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class LayerTuple {

    private Integer neuronsNum;
    private AgreggationFunctionInterface agreggationFunction;
    private ContinuousFunctionInterface transferFunction;

    public LayerTuple(Integer neuronsNum, AgreggationFunctionInterface agreggationFunction, ContinuousFunctionInterface transferFunction) {
        this.neuronsNum = neuronsNum;
        this.agreggationFunction = agreggationFunction;
        this.transferFunction = transferFunction;
    }

    public Integer getNeuronsNum() {
        return neuronsNum;
    }

    public AgreggationFunctionInterface getAgreggationFunction() {
        return agreggationFunction;
    }

    public ContinuousFunctionInterface getTransferFunction() {
        return transferFunction;
    }
}
