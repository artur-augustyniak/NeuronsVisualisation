package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

//TODO czy nie wystarczy uogólniona sigmoida ?

/**
 * Sigmoid Funkcja błędu Gaussa
 * http://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html
 *
 * @version 0.1
 * @author Robert Sedgewick rs@cs.princeton.edu
 * @author Kevin Wayne wayne@cs.princeton.edu
 */
public class GaussErrorFunction implements ContinuousFunctionInterface {

    private double beta;

    /**
     * Konstrutor
     * 
     * @param beta double bias dla stromości sigmoidy
     */
    public GaussErrorFunction(double beta) {
        this.beta = beta;
    }
  
    @Override
    public double evaluate(double x) {
        return this.erf(x *beta);
    }



    // fractional error in math formula less than 1.2 * 10 ^ -7.
    // although subject to catastrophic cancellation when z in very close to 0
    // from Chebyshev fitting formula for erf(z) from Numerical Recipes, 6.2
    private double erf(double z) {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp(-z * z - 1.26551223
                + t * (1.00002368
                + t * (0.37409196
                + t * (0.09678418
                + t * (-0.18628806
                + t * (0.27886807
                + t * (-1.13520398
                + t * (1.48851587
                + t * (-0.82215223
                + t * (0.17087277))))))))));
        if (z >= 0) {
            return ans;
        } else {
            return -ans;
        }
    }

    // fractional error less than x.xx * 10 ^ -4.
    // Algorithm 26.2.17 in Abromowitz and Stegun, Handbook of Mathematical.
    private double erf2(double z) {
        double t = 1.0 / (1.0 + 0.47047 * Math.abs(z));
        double poly = t * (0.3480242 + t * (-0.0958798 + t * (0.7478556)));
        double ans = 1.0 - poly * Math.exp(-z * z);
        if (z >= 0) {
            return ans;
        } else {
            return -ans;
        }
    }

    // cumulative normal distribution
    // See Gaussia.java for a better way to compute Phi(z)
    private double Phi(double z) {
        return 0.5 * (1.0 + this.erf(z / (Math.sqrt(2.0))));
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
