package pl.aaugustyniak.neural.functions.transfer;

import pl.aaugustyniak.neural.elements.exceptions.FunctionNotDifferentiableException;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;

/**
 * Uogólniona sigmoida
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class GeneralisedLogisticFunction implements ContinuousFunctionInterface {

    private double lowBorder;
    private double hiBorder;
    private double growthRate;
    private double growthMaxBias; // > 0
    private double ordinatePos;
    private double growthMaxTime; // > 0

    /**
     * Konstruktor
     *
     * @param lowBorder double dolna asymptota
     * @param hiBorder double górna asymptota
     * @param growthRate double szybkośc wzrostu
     * @param growthMaxBias double maksymalny wzrost przy asymptocie
     * @param ordinatePos double początek ?
     * @param growthMaxTime double cas maksymalnego wzrostu
     */
    public GeneralisedLogisticFunction(double lowBorder, double hiBorder, double growthRate, double growthMaxBias, double ordinatePos, double growthMaxTime) {
        if (growthMaxBias <= 0) {
            throw new IllegalArgumentException("growthMaxBias must be positive");
        }
        this.lowBorder = lowBorder;
        this.hiBorder = hiBorder;
        this.growthRate = growthRate;
        this.ordinatePos = ordinatePos;
        this.growthMaxTime = growthMaxTime;
        this.growthMaxBias = 1 / growthMaxBias;
    }

    /*
     * konstruktor, wstępna parametryzacja dla bi polarnej sigmoidy
     * 
     * @param growthRate double stromośc sigmoidy
     */
    public GeneralisedLogisticFunction(double growthRate) {

        this.lowBorder = -1.0;
        this.hiBorder = 1.0;
        this.growthRate = growthRate;
        this.ordinatePos = 0.5;
        this.growthMaxTime = 0.5;
        this.growthMaxBias = 0.5;
    }

    @Override
    public double evaluate(double x) {
        return this.lowBorder
                + (this.hiBorder - this.lowBorder)
                / Math.pow(1 + this.ordinatePos
                * Math.exp(-1 * this.growthRate
                * (x - this.growthMaxTime)), this.growthMaxBias);
    }

    @Override
    public double evaluateDeriv(double x) throws FunctionNotDifferentiableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
