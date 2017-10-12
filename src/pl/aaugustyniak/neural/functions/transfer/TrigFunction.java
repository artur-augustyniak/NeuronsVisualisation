package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Funkcje trygonometryczne, argument z parametrem beta
 * http://www.uci.agh.edu.pl/uczelnia/tad/dorobek_naukowy.php?id=sn]
 * 03a-Funkcje_przejscia.pdf s.1 implementacja szczególowej funkcji wg Command
 * pattern
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class TrigFunction implements ContinuousFunctionInterface {

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Interfejs wewnętrznej funkcji trygonometrycznej
     */
    public interface ExactFunction {

        /**
         * Wartość
         *
         * @param x double
         * @return double
         */
        public double eval(double x);
    }

    /**
     * Klasa osłonowa dla Math.sin();
     */
    public static class Sin implements ExactFunction {

        @Override
        public double eval(double param) {
            return Math.sin(param);
        }
    }

    /**
     * Klasa osłonowa dla Math.sin();
     */
    public static class Cos implements ExactFunction {

        @Override
        public double eval(double param) {
            return Math.cos(param);
        }
    }
    private double beta;
    private ExactFunction f;

    /**
     * Konstruktor
     *
     * @param f ExactFunction konkeretna funkcja tryg
     * @param beta double przesunięcie, w tym przypadku dopasowanie do wymaganej
     * separacji sygnału
     */
    public TrigFunction(ExactFunction f, double beta) {
        this.beta = beta;
        this.f = f;
    }

    @Override
    public double evaluate(double x) {
        return this.f.eval(x * this.beta);
    }
}
