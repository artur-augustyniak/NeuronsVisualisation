package pl.aaugustyniak.neural.elements.interfaces;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;

/**
 * Interfejs funkcji agregacji dla dwóch wektorów
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public interface AgreggationFunctionInterface {
    
    /**
     * Oblicz agregację dla wektorów
     * 
     * @param xi double[N]
     * @param wi double[N]
     * @return double
     * @throws FunctionFieldException 
     */
    double evaluate(double[] xi, double[] wi) throws FunctionFieldException;
}
