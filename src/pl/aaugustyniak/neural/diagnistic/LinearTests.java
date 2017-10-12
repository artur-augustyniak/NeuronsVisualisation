package pl.aaugustyniak.neural.diagnistic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import pl.aaugustyniak.neural.elements.Neuron;
import pl.aaugustyniak.neural.elements.interfaces.NeuronInterface;
import pl.aaugustyniak.neural.functions.agreggation.LinearAgreggationFunction;
import pl.aaugustyniak.neural.model.TeachingCaseDAO;
import pl.aaugustyniak.neural.model.TeachingCaseDAO.TeachingCase;

/**
 * Klasa debugerska, proste testy struktur liniowych
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class LinearTests {
    //TODO Opracowanie wyników błędów do asercji, automatyczny test wrapujący proces uczenia do bool - działa Tak|Nie
    
    
    /**
     * De facto kąt między wektorami Za: R. Tadeusiewicz Odkrywanie Sieci
     * neuronowych s: 149 http://matematyka.pisz.pl/strona/1630.html
     */
    public static void singleLinearNeuron() {

        Neuron n;
        n = new Neuron(new LinearAgreggationFunction(), 5, false);

        n.setNWeight(0.3, 0);
        n.setNWeight(0.3, 1);
        n.setNWeight(0.5, 2);
        n.setNWeight(0.6, 3);
        n.setNWeight(0.6, 4);
        //n.setBiasWeight(0.3);


        n.setNInput(-0.3, 0);
        n.setNInput(-0.6, 1);
        n.setNInput(-0.2, 2);
        n.setNInput(-0.4, 3);
        n.setNInput(1.0, 4);
        
        
            /*
             *Ślad pamięciowy długosc wektora wag
             * Za: R. Tadeusiewicz Odkrywanie Sieci neuronowych s: 157
             * Pomysł na normalizację:
             * Skalowanie wejść do przedziału o promieniu długości wektora wag
             */
            System.out.println(n.getWeightsPower());
            System.out.println(n.getInputsPower());
            System.out.println(n.getOutput());

    }

    /**
     * Właściwie jest to metoda korelacji bądź wielowymiarowej regresji liniowej
     * Ryszard Tadeusiewicz, Tomasz Gąciarz, Barbara Borowik, Bartosz Leper
     * „Odkrywanie właściwości sieci neuronowych przy użyciu programów w języku
     * C#” Polska Akademia Umiejętności 2007 s: 171
     *
     * @eksperyment ciąg uczący tasowany i nie
     * @eksperyment separacja przy pomocy nieliniowej funkcji transferu, sigmoid
     * powinien jaśniej olreślac separację
     */
    public static void trainSingleLinearNeuron() {
        //TODO teaching ratio odkrywanie s. 178
        TeachingCaseDAO tc = new TeachingCaseDAO("./teaching_set_adaline.txt");
        Neuron n = new Neuron(new LinearAgreggationFunction(), tc.getInNum(), false);

        double learning_ratio = 0.1;
        double neuroResp = 0.0;
        double neuroError = 0.0;

        /**
         * Uczenie
         */
        for (int j = 0; j < 500; j++) {
            TeachingCase tCase = tc.getNextCase(); //opcja random case
            for (int i = 0; i < n.getInputsNum(); i++) {
                n.setNInput(tCase.getDataSet()[i], i);
            }
            
                /**
                 * adaptacja learnin ratio jest możliwa learning_ratio / (j + 1)
                 * Ryszard Tadeusiewicz, „Sieci Neuronowe”, Państwowa Oficyna
                 * Wydawnicza RM 1993 s 34; 35
                 */
                neuroResp = n.getOutput();
                neuroError = tCase.getResult() - neuroResp;

                n.deltaRule(neuroError, learning_ratio);

                System.out.println("Iter:" + j + " Info: " + tCase.getDescription());
                System.out.println("Exp. Val: " + tCase.getResult() + " Output: " + neuroResp);
                System.out.println("Error: " + neuroError);
                System.out.println();


        }
    }

    /**
     * Ryszard Tadeusiewicz, Tomasz Gąciarz, Barbara Borowik, Bartosz Leper
     * „Odkrywanie właściwości sieci neuronowych przy użyciu programów w języku
     * C#” Polska Akademia Umiejętności 2007 s: 158; 159
     *
     * @eksperyment jednowarstwowa_rozpoznawanie_wzorca_funkcja_aktywacji.odt
     *
     */
    public static void madaLineNetwork() {

        ArrayList<NeuronInterface> network;
        network = new ArrayList<NeuronInterface>();

        //{ilość nóg, czy żyje w wodzie, lata, ma pióra, składa jaja}
        double weights[][] = {
            {4, 0.01, 0.01, -1, -1.5}, //ssak
            {2, -1, 2, 2.5, 2}, // ptak
            {-1, 3.5, 0.01, -2, 1.5} //ryba
        };

        double input[] = {1.0, 1.0, 1.0, 1.0, 4.0};

        /*
         *Prosta realizacja połączenia każdy z każdym
         * Za: R. Tadeusiewicz Odkrywanie Sieci neuronowych s: 161
         */
        for (int i = 0; i < 3; i++) {
            
                Neuron n = new Neuron(new LinearAgreggationFunction(), 5, false);
                n.setNWeight(weights[i][0], 0);
                n.setNWeight(weights[i][1], 1);
                n.setNWeight(weights[i][2], 2);
                n.setNWeight(weights[i][3], 3);
                n.setNWeight(weights[i][4], 4);


                for (int j = 0; j < n.getInputsNum(); j++) {
                    n.setNInput(input[j], j);
                }
                System.out.println(n.getOutput());
                network.add(n);

        }
    }

    /**
     * Ryszard Tadeusiewicz, „Sieci Neuronowe ”, Państwowa Oficyna Wydawnicza RM
     * 1993 s 36 Filtracja adaptacyjna Example05
     */
    public static void madaLineNetworkTrain() {
        //TODO pierwsze koncepcje sieci !!!
        ArrayList<Neuron> network;
        network = new ArrayList<Neuron>();

        TeachingCaseDAO tc = new TeachingCaseDAO("./teching_set_madaline.txt");

        /**
         * Sieć jedna warstwa
         */
        for (int i = 0; i < tc.getOutNum(); i++) {
            Neuron n = new Neuron(new LinearAgreggationFunction(), tc.getInNum(), false);
            network.add(n);
        }

        double learning_ratio = 0.1;
        double neuroError = 0.0;
        int teachIterations = 20000;
        double[] neuroResp = new double[tc.getOutNum()];
        /**
         * Uczenie
         */
        System.out.println("TEACHING");
        int j;
        for (j = 0; j < teachIterations; j++) {
            TeachingCase tCase = tc.getNextCase(); //opcja random case
            int nNum = 0;
            for (Neuron n : network) {
                for (int i = 0; i < n.getInputsNum(); i++) {
                    n.setNInput(tCase.getDataSet()[i], i);
                }
                
                    /**
                     * adaptacja learnin ratio jest możliwa learning_ratio / (j
                     * + 1) Ryszard Tadeusiewicz, „Sieci Neuronowe”, Państwowa
                     * Oficyna Wydawnicza RM 1993 s 34; 35
                     */
                    neuroResp[nNum] = n.getOutput();
                    neuroError = tCase.getResults()[nNum] - neuroResp[nNum];
                    n.deltaRule(neuroError, learning_ratio);

                nNum++;
            }
//            System.out.println("Iter:" + j + " Info: " + tCase.getDescription());
//            System.out.println("Exp. Val: " + Arrays.toString(tCase.getResults()) + " Output: " + Arrays.toString(neuroResp));
//            System.out.println("Error: " + neuroError);
//            System.out.println();
        }
        System.out.println("FINISHED with: " + j + " iter.");

        double distortion_scalar = 6.1;
        double distortion = 0.0;
        int targetIndex = 0;
        Random random;
        random = new Random();

        /**
         * Brak możliwości nauczenia ostatniego przypadku de facto separowalnośc
         * liniowa Test z zakłóceniami Ryszard Tadeusiewicz, Tomasz Gąciarz,
         * Barbara Borowik, Bartosz Leper „Odkrywanie właściwości sieci
         * neuronowych przy użyciu programów w języku C#” Polska Akademia
         * Umiejętności 2007 s: 180
         *
         */
        System.out.println("TEST");
        for (TeachingCase tCase : tc.getTeachCases()) {
            System.out.println(tCase.getDescription());
            System.out.println("Clear data set: " + Arrays.toString(tCase.getDataSet()));
            distortion = 2.0 * (random.nextDouble() - 0.5) * distortion_scalar;
            tCase.getDataSet()[targetIndex] += distortion;
            System.out.println("Dist. data set: " + Arrays.toString(tCase.getDataSet()));

            int nNum = 0;
            for (Neuron n : network) {
                for (int i = 0; i < n.getInputsNum(); i++) {
                    n.setNInput(tCase.getDataSet()[i], i);
                }
                
                    neuroResp[nNum] = n.getOutput();

                nNum++;
            }
            System.out.println("Exp. Val: " + Arrays.toString(tCase.getResults()) + " Output: " + Arrays.toString(neuroResp));
            System.out.println();
        }
    }
}
