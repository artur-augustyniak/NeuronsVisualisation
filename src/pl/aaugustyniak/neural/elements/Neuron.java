package pl.aaugustyniak.neural.elements;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.exceptions.NeuronInputRangeException;
import pl.aaugustyniak.neural.elements.interfaces.AgreggationFunctionInterface;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;
import pl.aaugustyniak.neural.elements.interfaces.NeuronInterface;
import pl.aaugustyniak.neural.model.TeachingCaseDAO.DataCase;

/**
 * Klasa neuronu Parametryzowana funkcjami agregacji i transferu
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class Neuron implements NeuronInterface {

    /**
     * Skala dla losowych wag początkowych. Ryszard Tadeusiewicz „Elementarne
     * wprowadzenie do techniki sieci neuronowych z przykładowymi programami” s.
     * 44
     *
     */
    public static final double RANDOM_SCALAR = 0.1;

    /**
     * Długośc wektora
     *
     */
    private double vectorLength(double[] vector) {
        double powLen = 0.0;
        for (int i = 0; i < vector.length; i++) {
            powLen += Math.pow(vector[i], 2);
        }
        return Math.sqrt(powLen);

    }

    private void innerNormalizeInputs() {
        //TODO ogólnie normalizacja i co z biasem
        double strength = this.getInputsPower();
        for (int i = 0; i < this.inputs.length; i++) {
            this.inputs[i] /= strength;
        }
    }

    /**
     * Wewnętrzna inicjalizacja z konstruktora
     */
    private void initializeNeuron(ContinuousFunctionInterface f, AgreggationFunctionInterface a, int inputsNum, boolean bias) {

        this.agrFun = a;
        this.actFun = f;

        //zawsze dodaję bias, wyłączony jest zerowany
        inputsNum += 1;

        this.weights = new double[inputsNum];
        this.inputs = new double[inputsNum];
        Random random;
        random = new Random();
        for (int i = 0; i < inputsNum; i++) {
            //TODO doświadczenie
            /**
             * Ryszard Tadeusiewicz, Tomasz Gąciarz, Barbara Borowik, Bartosz
             * Leper „Odkrywanie właściwości sieci neuronowych przy użyciu
             * programów w języku C#”, Polska Akademia Umiejętności 2007 s. 177
             * eksperymenty z zakresem wag losowych i szybkością uczenia
             */
            //TODO ekseryment inicjalizacja wag
            //this.weights[i] = random.nextGaussian();
            //this.normalizeWeigths();
            //TODO zapewnienie że każda waga inna
            this.weights[i] = 2.0 * (random.nextDouble() - 0.5) * RANDOM_SCALAR;
            this.inputs[i] = random.nextGaussian();
        }

        //zawsze dodaję bias, wyłączony jest zerowany
        this.inputs[0] = (bias) ? 1.0 : 0.0;
        this.weights[0] = (bias) ? this.weights[0] : 0.0;
    }
    private Boolean autoNormalisation = false;
    private AgreggationFunctionInterface agrFun;
    private ContinuousFunctionInterface actFun;
    /**
     * Wejścia neuronu
     */
    protected double[] weights;
    /**
     * Wagi neuronu
     */
    protected double[] inputs;

    /**
     * Konstruktor, Bias (próg) z formuły afinicznej Ryszard Tadeusiewicz,
     * Tomasz Gąciarz, Barbara Borowik, Bartosz Leper „Odkrywanie właściwości
     * sieci neuronowych przy użyciu programów w języku C# s. 68 jesli nie
     * podano funkcji transferu neuron liniowy
     *
     * @param a funkcja agregacji
     * @param inputsNum ilośc wejśc neuronu - bez biasu
     * @param bias czy stosować bias
     */
    public Neuron(AgreggationFunctionInterface a, int inputsNum, boolean bias) {
        this.initializeNeuron(null, a, inputsNum, bias);
    }

    /**
     * Konstruktor, Bias (próg) z formuły afinicznej Ryszard Tadeusiewicz,
     * Tomasz Gąciarz, Barbara Borowik, Bartosz Leper „Odkrywanie właściwości
     * sieci neuronowych przy użyciu programów w języku C# s. 68
     *
     * @param f funkcja transferu
     * @param a funkcja agregacji
     * @param inputsNum ilośc wejśc neuronu - bez biasu
     * @param bias czy stosować bias
     */
    public Neuron(ContinuousFunctionInterface f, AgreggationFunctionInterface a, int inputsNum, boolean bias) {
        this.initializeNeuron(f, a, inputsNum, bias);
    }

    @Override
    public void setNWeight(double wi, int i) throws NeuronInputRangeException {
        int realIndex = i + 1;
        if (realIndex >= this.weights.length) {
            throw new NeuronInputRangeException();
        }
        this.weights[realIndex] = wi;
    }

    @Override
    public void setNInput(double xi, int i) throws NeuronInputRangeException {
        int realIndex = i + 1;
        if (realIndex >= this.inputs.length) {
            throw new NeuronInputRangeException();
        }
        this.inputs[realIndex] = xi;
        if (this.autoNormalisation) {
            this.innerNormalizeInputs();
        }



    }

    @Override
    public double[] getInputs() {
        return this.inputs;
    }

    @Override
    public double[] getWeights() {
        return this.weights;
    }

    @Override
    public double getOutput() {
        if (this.autoNormalisation) {
            this.innerNormalizeInputs();
        }
        try {
            if (this.actFun != null) {
                return this.actFun.evaluate(this.agrFun.evaluate(this.inputs, this.weights));
            } else {
                return this.agrFun.evaluate(this.inputs, this.weights);
            }
        } catch (FunctionFieldException ex) {
            Logger.getLogger(Neuron.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
    }

    @Override
    public void setBiasWeight(double wi) throws NeuronInputRangeException {
        this.weights[0] = wi;
    }

    @Override
    public void setAgrFun(AgreggationFunctionInterface agrFun) {
        this.agrFun = agrFun;
    }

    @Override
    public void setActFun(ContinuousFunctionInterface actFun) {
        this.actFun = actFun;
    }

    @Override
    public double getInputsPower() {
        return this.vectorLength(this.inputs);
    }

    @Override
    public double getWeightsPower() {
        return this.vectorLength(this.weights);
    }

    @Override
    public int getInputsNum() {
        return this.inputs.length - 1;
    }

    @Override
    public void normalizeInputs() {
        if (!this.autoNormalisation) {
            this.innerNormalizeInputs();
        }
    }

    @Override
    public double getNetValue() throws FunctionFieldException {
        if (this.autoNormalisation) {
            this.innerNormalizeInputs();
        }
        return this.agrFun.evaluate(this.inputs, this.weights);
    }

    @Override
    public void deltaRule(double error, double teachingRatio) {
        try {
            /**
             * Pochodna tranferFunc po netVal w J-ym kroku
             */
            double transferDerivInJStep = this.actFun.evaluateDeriv(this.getNetValue());
            for (int i = 0; i < this.weights.length; i++) {
                this.weights[i] += teachingRatio * error * transferDerivInJStep * this.inputs[i];
            }
        } catch (FunctionFieldException ex) {
            Logger.getLogger(Neuron.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FunctionNotDifferentiableException ex) {
            Logger.getLogger(Neuron.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void diableAutoNormalisation() {
        this.autoNormalisation = false;
    }

    public void enableAutoNormalisation() {
        this.autoNormalisation = true;
    }

    @Override
    public void diableRandomization() {
        int j;
        double i;
        double step = 1.0 / this.inputs.length;
        for (j = 0, i = -0.5; j < this.inputs.length; i = i + step, j++) {
            this.weights[j] = i;
        }
    }

    @Override
    public void setInputs(DataCase inputs) {
        for (int i = 0; i < this.getInputsNum(); i++) {
            this.setNInput(inputs.getDataSet()[i], i);
        }
    }
}