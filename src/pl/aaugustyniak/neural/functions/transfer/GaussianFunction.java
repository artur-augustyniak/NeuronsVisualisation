package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Krzywa dzwonowa 
 * http://lcn.epfl.ch/tutorial/english/aneuron/html/components.html
 * Dla charekterystyk RBF podobnych
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class GaussianFunction implements ContinuousFunctionInterface {

    private double expVal;
    private double stdDev;

    /**
     * Konstruktor dla wartości oczekiwanej 0
     *
     * @param stdDev double odchylenie std.
     */
    public GaussianFunction(double stdDev) {
        this.expVal = 0.0;
        this.stdDev = stdDev;
    }

    /**
     * Konstruktor
     *
     * @param stdDev double wartośc oczekiwana
     * @param expVal double odchylenie std.
     */
    public GaussianFunction(double stdDev, double expVal) {
        this.expVal = expVal;
        this.stdDev = stdDev;
    }

    @Override
    public double evaluate(double x) {
        return (1 / (this.stdDev * Math.sqrt(2 * Math.PI))) * Math.exp((-1.0 * Math.pow((x - this.expVal), 2)) / (2 * Math.pow(this.stdDev, 2)));
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
