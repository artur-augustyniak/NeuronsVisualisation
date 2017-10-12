package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Funkcja tożsamościowa
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class LinearFunction implements ContinuousFunctionInterface {

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        return 1.0;
    }
}
