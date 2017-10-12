package pl.aaugustyniak.neural.elements.interfaces;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;

/**
 * Interfejs ciągłej funkcji jednej zmiennej - transferu/przejścia
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public interface ContinuousFunctionInterface {

    /**
     * Wartość
     *
     * @param x double
     * @return double
     */
    double evaluate(double x);

    /**
     * Wartośc pochodnej
     *
     * @param x double
     * @return double
     * @throws FunctionNotDifferentiableException
     */
    double evaluateDeriv(double x) throws FunctionNotDifferentiableException;
}
