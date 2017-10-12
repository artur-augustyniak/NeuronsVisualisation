package pl.aaugustyniak.neural.elements.helpers;

/**
 * Enumeracja do przekazywania "operatora" jako parametru
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public enum SignOperatorEnum {

    PLUS(1.0), MINUS(-1.0);
    private double value;

    private SignOperatorEnum(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
