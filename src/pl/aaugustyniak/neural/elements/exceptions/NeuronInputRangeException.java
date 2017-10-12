/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.elements.exceptions;

/**
 *
 * @author artur
 */
public class NeuronInputRangeException extends ArrayIndexOutOfBoundsException {

    String error;

    public NeuronInputRangeException() {
        super();
        error = "Out of neuron inputs range";
    }

    /**
     *
     * @param err
     */
    public NeuronInputRangeException(String err) {
        super(err);
        error = err;
    }

    public String getError() {
        return error;
    }
}
