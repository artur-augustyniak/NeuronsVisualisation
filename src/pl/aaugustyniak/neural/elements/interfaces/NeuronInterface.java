package pl.aaugustyniak.neural.elements.interfaces;

import pl.aaugustyniak.neural.elements.exceptions.FunctionFieldException;
import pl.aaugustyniak.neural.elements.exceptions.NeuronInputRangeException;
import pl.aaugustyniak.neural.model.TeachingCaseDAO.DataCase;

/**
 * Interfejs neuronu mogącego współpracować z frameworkiem
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public interface NeuronInterface {

    /**
     * Zwraca ilośc wejśc neuronu bez biasu, dla iteracji
     *
     * @return int
     */
    int getInputsNum();

    /**
     * Zwraca tablicę z aktualnymi stanami wejść, wraz z biasem indeksowanym [0]
     *
     * @return double[]
     */
    double[] getInputs();

    /**
     * Zwraca tablicę z aktualnymi wagami, wraz z wagą biasu indeksowaną [0]
     *
     * @return double[]
     */
    double[] getWeights();

    /**
     * Ustawienie n-tej wagi neuronu, ukrywa wagę biasu
     *
     * @param wi double wartośc wagi
     * @param i int indeks wagi
     * @throws NeuronInputRangeException
     */
    void setNWeight(double wi, int i) throws NeuronInputRangeException;

    /**
     * Ustawienie n-tego wejścia neuronu, ukrywa bias
     *
     * @param xi double wartośc wejścia
     * @param i int indeks wejścia
     * @throws NeuronInputRangeException
     */
    void setNInput(double xi, int i) throws NeuronInputRangeException;
    
    /**
     * Ustawienie wszystkich wejść neuronu, ukrywa bias
     * @param inputs 
     */
    void setInputs(DataCase inputs);

    /**
     * Zwraca aktualny sygnał wyjściowy neuronu wg schematu
     * transferFunction(netvalue) [1] Ryszard Tadeusiewicz, Tomasz Gąciarz,
     * Barbara Borowik, Bartosz Leper „Odkrywanie właściwości sieci neuronowych
     * przy użyciu programów w języku C#” s. 67
     *
     * @return double
     */
    double getOutput();

    /**
     * Zwraca aktualną wartośc net value - (agregacji) neuronu
     * transferFunction(netvalue) [1] Ryszard Tadeusiewicz, Tomasz Gąciarz,
     * Barbara Borowik, Bartosz Leper „Odkrywanie właściwości sieci neuronowych
     * przy użyciu programów w języku C#” s. 67
     *
     * @return double
     * @throws FunctionFieldException
     */
    double getNetValue() throws FunctionFieldException;

    /**
     * Ustawia wagę dla bias, wejście o indeksie [0]
     *
     * @param wi double wartość wagi
     * @throws NeuronInputRangeException
     */
    void setBiasWeight(double wi) throws NeuronInputRangeException;

    /**
     * Zmienia funkcję agregacji
     *
     * @param agrFun (AgreggationFunctionInterface funkcja agregacji
     */
    public void setAgrFun(AgreggationFunctionInterface agrFun);

    /**
     * Zmienia funkcję transferu/aktywacji
     *
     * @param actFun ContinuousFunctionInterface funkcja aktywacji
     */
    public void setActFun(ContinuousFunctionInterface actFun);

    /**
     * Moc (długośc wektora) sygnałów wejściowych Ryszard Tadeusiewicz, Tomasz
     * Gąciarz, Barbara Borowik, Bartosz Leper „Odkrywanie właściwości sieci
     * neuronowych przy użyciu programów w języku C# s. 157
     *
     * @return double
     */
    public double getInputsPower();

    /**
     * Normalizuje wektor wejść jeśli neuron ma wyłączoną flagę
     * autoNormalisation, w przeciwnym wypadku nie powoduje zmian a nueron sam
     * normalizuje swoje wejścia
     *
     * Wketor jednostkowy
     */
    public void normalizeInputs();

    /**
     * Ustawia flagę autoNormalisation -> false w efekcie wyłącza
     * autonormalizację wejść
     */
    public void diableAutoNormalisation();

    /**
     * Ustawia stały zestaw wag (ciąg rosnący od -0.5 do 0.5) z krokiem 1/inputNum
     * 
     */
    public void diableRandomization();

    /**
     * Moc (długośc wektora) wag Ryszard Tadeusiewicz, Tomasz Gąciarz, Barbara
     * Borowik, Bartosz Leper „Odkrywanie właściwości sieci neuronowych przy
     * użyciu programów w języku C# s. 157
     *
     * @return double
     */
    public double getWeightsPower();
    //TODO Pomysł na normalizację: Skalowanie wejść do przedziału o promieniu długości wektora wag

    /**
     * Reguła delta dla elementu liniowego watrośc pochodnej zawsze jeden
     * degenruje się do this.weights[i] += teachingRatio * error *
     * this.inputs[i]; http://en.wikipedia.org/wiki/Delta_rule
     *
     * Parametr błędu - nie jest to błąd średniokwadratowy całego wejścia !!
     * http://en.wikipedia.org/wiki/Delta_rule Derivation of the delta rule *
     * BEZ MOMENTUM całe uczenie reguła delta tylko teaching ratio podane z
     * zewnątrz Reguła delta to uaktualnienie po każdej prezentacji pary uczącej
     * ossowski s 22; s 12
     *
     * DOWÓD Ryszard Tadeusiewicz, „Sieci Neuronowe”, Państwowa Oficyna
     * Wydawnicza RM 1993 s 35
     *
     * http://galaxy.agh.edu.pl/~vlsi/AI/norm/teoria.html
     * http://galaxy.uci.agh.edu.pl/~vlsi/AI/rdelta/ ARTYKULY reguły_uczenia.pdf
     *
     *
     * Dla sieci Madaline poprawki obliczane jako jedna macierz albo per neuron
     * Zał 1 Zał 2
     *
     *
     * Reguła delta dla neuronu nieliniowego z czynnikiem wartości pochodnej
     * funkcji transferu Artukuly uczenie_neuronow_liniowych.pdf yszard
     * Tadeusiewicz, „Sieci Neuronowe ”, Państwowa Oficyna Wydawnicza RM 1993 s
     * 58
     *
     * @param error
     *
     * @param teachingRatio
     */
    void deltaRule(double error, double teachingRatio);
    //TODO momentum vs learning rate 8 http://www.willamette.edu/~gorr/classes/cs449/momrate.html
}
