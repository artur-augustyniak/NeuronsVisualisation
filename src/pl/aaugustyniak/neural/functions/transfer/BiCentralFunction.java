/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 *
 * @author artur
 */
public class BiCentralFunction  implements ContinuousFunctionInterface  {

    @Override
    public double evaluate(double x) {
        UniSigmoidFunction sig = new UniSigmoidFunction(15.0);
        double one = sig.evaluate(x) * (1.0 - sig.evaluate(x));
        return one * one* one* one * one;
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
