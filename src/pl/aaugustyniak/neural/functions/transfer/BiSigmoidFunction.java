package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Bipolarna sigmoida tangens hiperboliczny 
 * Ryszard Tadeusiewicz, „Sieci Neuronowe”, Państwowa Oficyna Wydawnicza RM 1993 s. 56, 
 * http://www.uci.agh.edu.pl/uczelnia/tad/dorobek_naukowy.php?id=sn 03a-Funkcje_przejscia.pdf s.1
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class BiSigmoidFunction implements ContinuousFunctionInterface {

    private double beta;
    
    /**
     * Konstruktor
     * 
     * @param beta double stromośc sigmoidy
     */
    public BiSigmoidFunction(double beta) {
        this.beta = beta;
    }

    @Override
    public double evaluate(double x) {
        return Math.tanh(beta * x);
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
         return (beta * x)/(1.0+ Math.sqrt(1.0+ Math.pow(beta, 2) * Math.pow(x, 2)));
        //return Math.pow(1.0/Math.cosh(x), 2);
    }
}
