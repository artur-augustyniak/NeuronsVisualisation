package pl.aaugustyniak.neural.model;

import pl.aaugustyniak.neural.model.TeachingCaseDAO.TeachingCase;

/**
 * Encja przypadku uczącego
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class RtadTeachingCase implements TeachingCase {

    private String description;
    private double[] dataSet;
    private double result;
    private double[] results;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double[] getDataSet() {
        return dataSet;
    }

    @Override
    public void setDataSet(double[] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public double getResult() {
        return result;
    }

    @Override
    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public double[] getResults() {
        return results;
    }

    @Override
    public void setResults(double[] results) {
        this.results = results;
    }
}
