package pl.aaugustyniak.neural.functions.transfer;

/**
 * Fynkcja skoku jednostkowego, przybliżenie stromą sigmoidą [1] s. 55
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class NearHeavisideUniSigmFunction extends UniSigmoidFunction {
    
    /**
     * Nieco krótkowzrocznie widziana nieskończonośc
     */
    public static final double HEAVISIDE_APPROX = 99.0;

    /**
     * Konstruktor
     */
    public NearHeavisideUniSigmFunction(){
        super(HEAVISIDE_APPROX);
    }
    
    
}
