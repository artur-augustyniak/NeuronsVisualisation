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
public class RidellaAgreggationQuadricFunction implements AgreggationFunctionInterface {

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {
        double sum = wi[0];
        double quadricComp = 0.0;
        double quadricWeight = 5.0;
        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        for (int i = 1; i < xi.length; i++) {
            sum += (wi[i] * xi[i]);
            quadricComp += -1.0 * Math.pow(xi[i], 2);
        }
        return sum + (quadricWeight * quadricComp);
    }    
}
