/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.view.model;

import org.sf.surfaceplot.ISurfacePlotModel;
import pl.aaugustyniak.neural.elements.Neuron;

/**
 *
 * @author salagarsamy
 */
public class NeuronSurfaceModel implements ISurfacePlotModel {

    private Neuron n;

    public NeuronSurfaceModel(Neuron n) {
        this.n = n;
    }

    @Override
    public float calculateZ(float x, float y) {
        n.setNInput(x, 0);
        n.setNInput(y, 1);
        return (float) n.getOutput();
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
        return false;
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
        return 50;
    }

    @Override
    public int getDispDivisions() {
        return 50;
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
        return "x[1]";
    }

    @Override
    public String getYAxisLabel() {
        return "x[2]";
    }

    @Override
    public String getZAxisLabel() {
        return "Łączne pobudzenie";
    }
}
