package pl.aaugustyniak.neural.functions.agreggation;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;

/**
 * Kwadratowa funkcja agregacji dla neuronu sigmoidalnego Dla uzyskania
 * charakterystyki zbliżonej do RBF [5
 * http://www.uci.agh.edu.pl/uczelnia/tad/dorobek_naukowy.php?id=sn]
 * 03a-Funkcje_przejscia.pdf s.5 S. Ridella, S. Rovetta, and R. Zunino. Circular
 * backpropagation networks for * classification. IEEE Transaction on Neural
 * Networks, 8(1):84–97, 1997.
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class SquareAgreggationFunction implements AgreggationFunctionInterface {

    private SignOperatorEnum sign;

    /**
     * Konstruktor
     *
     * @param sign SignOperatorEnum monotonicznosc paraboli
     */
    public SquareAgreggationFunction(SignOperatorEnum sign) {
        this.sign = sign;
    }

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {

        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        double sum, squareSum;
        sum = wi[0];
        squareSum = wi[xi.length - 1];

        for (int i = 1; i < xi.length - 1; i++) {
            sum += wi[i] * xi[i];
            squareSum += this.sign.getValue() * Math.pow(xi[i], 2);
        }
        return sum + squareSum;
    }
}
