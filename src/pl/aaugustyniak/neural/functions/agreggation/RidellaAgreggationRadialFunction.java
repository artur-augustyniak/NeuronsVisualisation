/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.functions.agreggation;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;

/**
 *
 * @author artur
 */
public class RidellaAgreggationRadialFunction implements AgreggationFunctionInterface {

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {
        double sum = 0.0;
        double quadricComp = 0.0;
        double quadricWeight = 1.8;
        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        for (int i = 1; i < xi.length; i++) {
            sum += Math.pow(xi[i] - (-1.0 * (wi[i] / (2 * quadricWeight))), 2);
            quadricComp += Math.pow(xi[i], 2) / (4 * quadricWeight);
        }
        return quadricWeight * (sum - (1.0 / quadricWeight) * (quadricComp - wi[0]));
    }
}
