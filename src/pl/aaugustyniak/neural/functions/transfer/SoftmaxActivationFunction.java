package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;
import pl.aaugustyniak.neural.elements.interfaces.NeuronInterface;

/**
 * Specjalistyczna funkcja aktywacji przystosowana do problemów
 * klasyfikacyjnych, w których wykorzystywana jest reprezentacja zmiennej
 * wyjściowej typu jeden-z-N. Stosowana jest w neuronach warstwy wyjściowej
 * sieci. Wyznacza znormalizowane wartości funkcji wykładniczej (sumują się do
 * jedności). W połączeniu z funkcją błędu opartą na entropii wzajemnej pozwala
 * szacować przy pomocy perceptronu wielowarstwowego prawdopodobieństwa
 * przynależności do poszczególnych klas (Bishop, 1995; Bridle, 1990).
 * http://www.statsoft.pl/textbook/glosfra_stat.html?http%3A%2F%2Fwww.statsoft.pl%2Ftextbook%2Fglosf.html
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class SoftmaxActivationFunction implements ContinuousFunctionInterface {

    private NeuronInterface n;

    /**
     * Konstruktor, brzydki fix zapewniający dostęp do wartości wejść w funkcji
     * Softmax
     *
     * @param n NeuronInterface neuron zawierający ta funkcję
     */
    public SoftmaxActivationFunction(NeuronInterface n) {
        this.n = n;
    }

    @Override
    public double evaluate(double x) {

        double numerator = Math.exp(x);
        double denominator = 0.0; // e^0 =1

        for (int i = 0; i < this.n.getInputsNum(); i++) {
            denominator += Math.exp(this.n.getInputs()[i]);
        }
        return numerator / denominator;
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
