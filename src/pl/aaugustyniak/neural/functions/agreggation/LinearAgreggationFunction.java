package pl.aaugustyniak.neural.functions.agreggation;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;

/**
 * Podstawowa agregacja liniowa
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class LinearAgreggationFunction implements AgreggationFunctionInterface {

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {
        double sum = 0.0;
        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        for (int i = 0; i < xi.length; i++) {
            sum += wi[i] * xi[i];
        }
        return sum;
    }
}
