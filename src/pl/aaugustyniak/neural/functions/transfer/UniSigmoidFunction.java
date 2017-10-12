package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Funkcja sigmoidalna unipolarna z parametrem, na bazie std. funkcji
 * logistycznej (beta==1) 
 * Ryszard Tadeusiewicz, „Sieci Neuronowe”, Państwowa Oficyna Wydawnicza RM 1993 s. 55; 
 * http://www.princeton.edu/~achaney/tmve/wiki100k/docs/Sigmoid_function.html
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class UniSigmoidFunction implements ContinuousFunctionInterface {


    private double beta;
    private SignOperatorEnum sign = SignOperatorEnum.MINUS;

    /**
     * Domyślny konstruktor bez zmiany znaku
     *
     * @param beta double stromość sigmoidy
     */
    public UniSigmoidFunction(double beta) {
        this.beta = beta;
    }

    /**
     * Konstruktor ze zmianą znaku
     *
     * @param beta double stromość sigmoidy
     * @param sign SignOperatorEnum mnożnik razy znak
     */
    public UniSigmoidFunction(double beta, SignOperatorEnum sign) {
        this.sign = sign;
        this.beta = beta;
    }

    @Override
    public double evaluate(double x) {
        //return (beta * x)/(Math.sqrt(1.0+ Math.pow(x, 2)));
        //return (beta * x)/(1.0+ Math.sqrt(1.0+ Math.abs(x * beta)));
        return (1 / (1 + Math.pow(Math.E, (this.sign.getValue() * beta * x))));
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        return (1.0 - this.evaluate(x)) * this.evaluate(x);
        
    }
}
