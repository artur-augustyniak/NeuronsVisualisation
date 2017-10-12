/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.functions.agreggation;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;
import pl.aaugustyniak.neural.functions.transfer.UniSigmoidFunction;

/**
 *
 * @author artur
 */
public class BicentralAgreggationFunction implements AgreggationFunctionInterface {

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {
        double t = 0.0;
        double[] b = {0.0, 0.3, 3.9};
        double[] s = {0.0, 1.7, 0.1};
        double[] s2 = {0.0, 0.5, 0.3};

        double prod = 1.0;
        double lSig;
        double rSig;
        UniSigmoidFunction sig = new UniSigmoidFunction(1.0);
        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        for (int i = 1; i < xi.length; i++) {
            lSig = sig.evaluate(Math.exp(s[i]) * (xi[i] - t + Math.exp(b[i])));
            rSig = sig.evaluate(Math.exp(s2[i]) * (xi[i] - t - Math.exp(b[1])));
            rSig = 1.0 - rSig;
            prod *= (lSig * rSig);
        }
        return prod;
    }
}
