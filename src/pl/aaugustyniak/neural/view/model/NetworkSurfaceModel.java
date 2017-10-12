/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.view.model;

import java.util.ArrayList;
import java.util.List;
import org.sf.surfaceplot.ISurfacePlotModel;
import pl.aaugustyniak.neural.elements.Neuron;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.functions.agreggation.BicentralAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.LinearAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.RadialAgreggationFunction;
import pl.aaugustyniak.neural.functions.transfer.BiSigmoidFunction;
import pl.aaugustyniak.neural.functions.transfer.GaussianFunction;
import pl.aaugustyniak.neural.functions.transfer.UniSigmoidFunction;

/**
 *
 * @author salagarsamy
 */
public class NetworkSurfaceModel implements ISurfacePlotModel {

    private List<Neuron> n;
    private Neuron out;
    private Integer hiddenNum = 5;

    public NetworkSurfaceModel(Neuron ne) {
        out = new Neuron(new BiSigmoidFunction(24.0), new LinearAgreggationFunction(), hiddenNum, true);
        n = new ArrayList<Neuron>();
        for (int i = 0; i < hiddenNum; i++) {
            n.add(i, new Neuron(new BiSigmoidFunction(14.0), new LinearAgreggationFunction(), 2, true));
        }
    }

    @Override
    public float calculateZ(float x, float y) {
        
        x = (x - this.getXMin())/ (this.getXMax()- this.getXMin());
        y = (y - this.getYMin())/ (this.getYMax()- this.getYMin());
        
        for (int i = 0; i < hiddenNum; i++) {
            n.get(i).setNInput(x, 0);
            n.get(i).setNInput(y, 1);
           n.get(i).normalizeInputs();
            out.setNInput(n.get(i).getOutput(), i);
        }
        out.normalizeInputs();
        return (float) out.getOutput();
    }

    @Override
    public int getPlotMode() {
        return ISurfacePlotModel.PLOT_MODE_SPECTRUM;
    }

    @Override
    public boolean isBoxed() {
        return true;
    }

    @Override
    public boolean isMesh() {
        return true;
    }

    @Override
    public boolean isScaleBox() {
        return true;
    }

    @Override
    public boolean isDisplayXY() {
        return true;
    }

    @Override
    public boolean isDisplayZ() {
        return true;
    }

    @Override
    public boolean isDisplayGrids() {
        return true;
    }

    @Override
    public int getCalcDivisions() {
        return 100;
    }

    @Override
    public int getDispDivisions() {
        return 100;
    }

    @Override
    public float getXMin() {
        return -10.0f;
    }

    @Override
    public float getXMax() {
        return 10.0f;
    }

    @Override
    public float getYMin() {
        return -10.0f;
    }

    @Override
    public float getYMax() {
        return 10.0f;
    }

    @Override
    public float getZMin() {
        return -1.0f;
    }

    @Override
    public float getZMax() {
        return 1.0f;
    }

    @Override
    public String getXAxisLabel() {
        return "X";
    }

    @Override
    public String getYAxisLabel() {
        return "Y";
    }

    @Override
    public String getZAxisLabel() {
        return "Łączne pobudzenie";
    }
}
