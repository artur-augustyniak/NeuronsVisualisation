package pl.aaugustyniak.neural.functions.agreggation;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;

/**
 * Radialna funkcja agregacji Odległośc punktów w przestrzeni euklidesowej Dla
 * uzyskania charakterystyki zbliżonej do RBF
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class RadialAgreggationFunction implements AgreggationFunctionInterface {

    private SignOperatorEnum profile;

    /**
     * Konstruktor
     *
     * @param profile SignOperatorEnum MINUS - wzgórze; PLUS - dolina
     */
    public RadialAgreggationFunction(SignOperatorEnum profile) {
        this.profile = profile;
    }

    @Override
    public double evaluate(double[] xi, double[] wi) throws FunctionFieldException {
        double sum = 0.0;
        if (xi.length != wi.length) {
            throw new FunctionFieldException();
        }
        for (int i = 0; i < xi.length; i++) {
            sum += this.profile.getValue() * Math.pow((wi[i] - xi[i]), 2);
        }
        return sum;
    }
}
