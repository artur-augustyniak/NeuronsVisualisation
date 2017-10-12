package pl.aaugustyniak.neural.diagnistic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.aaugustyniak.neural.app.main;
import pl.aaugustyniak.neural.elements.Neuron;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.functions.agreggation.LinearAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.RadialAgreggationFunction;
import pl.aaugustyniak.neural.functions.transfer.BiSigmoidFunction;
import pl.aaugustyniak.neural.functions.transfer.UniSigmoidFunction;
import pl.aaugustyniak.neural.model.TeachingCaseDAO;
import pl.aaugustyniak.neural.model.TeachingCaseDAO.TeachingCase;

/**
 * Klasa debugerska, proste testy struktur nieliniowych
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class NonLinearTests {

    /**
     * Zachowanie Dla wszystkich funkcji aktywacji w pakiecie example6a -> w
     * przypadku przybliżenia Heavisite unipolar na "umieranie" zróżnicowania
     * przy którymś sygnale wejściowym 0.0 pomaga bias
     */
    public static void singleNonLinearNeuron() {
        Neuron n;
        n = new Neuron(new UniSigmoidFunction(10000.0), new LinearAgreggationFunction(), 4, false);
        n.setNWeight(-0.2, 0);
        n.setNWeight(0.1, 1);
        n.setNWeight(-0.3, 2);
        n.setNWeight(0.0, 3);
        //n.setBiasWeight(0.3);

        n.setNInput(-0.3, 0);
        n.setNInput(-0.6, 1);
        n.setNInput(-0.0, 2);
        n.setNInput(0.6, 3);

        System.out.println(n.getWeightsPower());
        System.out.println(n.getInputsPower());
        System.out.println("Ralna " + n.getOutput());
        System.out.format("Przybliżona %f \n", n.getOutput());

    }

    /**
     * Separacja punktów Example 06e ### UCZENIE NEURONU NIELINIOWEGO ####
     * Metoda perceprtonowa (chba związana z reguła delta) Widriwa i Hoffa
     * (uczeniu podlega tylko część liniowa) dkrywanie właściwości sieci
     * neuronowych przy użyciu programów w języku C# s 216
     *
     */
    public static void singleNonLinearNeuronTrain() {

        //TODO teaching ratio odkrywanie s. 178
        //TeachingCaseDAO tc = new TeachingCaseDAO("./teaching_set_adaline.txt", true);
        TeachingCaseDAO tc = new TeachingCaseDAO("./plane_plot_problem", true);

        Neuron n = new Neuron(new BiSigmoidFunction(1), new LinearAgreggationFunction(), tc.getInNum(), false);
        //n.diableRandomization();

        double learning_ratio = 0.3;
        double neuroResp;
        double neuroError;
        TeachingCase tCase;
        double distortion_scalar = 0.9;
        double distortion;
        int targetIndex = 0;
        Random random;
        random = new Random();

        /**
         * Uczenie
         */
        for (int j = 0; j < 100; j++) {
            tCase = tc.getNextCase();
            n.setInputs(tCase);
            neuroResp = n.getOutput();
            neuroError = tCase.getResult() - neuroResp;
            //Plotter.plotSingleNeuron(j, n);
            n.deltaRule(neuroError, learning_ratio);
            System.out.println("Iter:" + j + " Info: " + tCase.getDescription());
            System.out.println("Exp. Val: " + tCase.getResult() + " Output: " + neuroResp);
            System.out.println("Error: " + (tCase.getResult() - neuroResp));
            System.out.println();
        }

        /* TEST*/
        System.out.println();
        System.out.println("TEST - Distorted data - dist. factor (*=):  " + distortion_scalar);
        System.out.println();
        for (TeachingCase Case : tc.getTeachCases()) {

            System.out.println(Case.getDescription());
            System.out.println("Clear data set: " + Arrays.toString(Case.getDataSet()));
            distortion = 2.0 * (random.nextDouble() - 0.5) * distortion_scalar;
            targetIndex = random.nextInt(Case.getDataSet().length);
            Case.getDataSet()[targetIndex] *= distortion;
            System.out.println("Dist. data set: " + Arrays.toString(Case.getDataSet()));

            n.setInputs(Case);
            neuroResp = n.getOutput();

            System.out.println("Exp. Val: " + Case.getResult() + " Output: " + neuroResp);
            System.out.println();
        }
    }

    /**
     * Example 6b + próby z separacją liniową - porównaj z madaline przypadek -1
     * -1 -1 przy neuronie i sieci jednowarstwowej dyskryminacja liniowa example
     * 06 c
     */
    public static void nonLinearNetworkTrain() {
        //TODO pierwsze koncepcje sieci !!!
        ArrayList<Neuron> network;
        network = new ArrayList<Neuron>();

        TeachingCaseDAO tc = new TeachingCaseDAO("./teaching_set_non_linear_one_layer.txt");

        /**
         * Sieć jedna warstwa
         */
        for (int i = 0; i < tc.getOutNum(); i++) {
            Neuron n = new Neuron(new BiSigmoidFunction(99.0), new LinearAgreggationFunction(), tc.getInNum(), false);
            network.add(n);
        }

        double learning_ratio = 0.1;
        double neuroError = 0.0;
        int teachIterations = 200;
        double[] neuroResp = new double[tc.getOutNum()];
        /**
         * Uczenie
         */
        System.out.println("TEACHING");
        int j;
        for (j = 0; j < teachIterations; j++) {
            TeachingCaseDAO.TeachingCase tCase = tc.getNextCase(); //opcja random case
            int nNum = 0;
            for (Neuron n : network) {
                n.setInputs(tCase);
                neuroResp[nNum] = n.getOutput();
                neuroError = tCase.getResults()[nNum] - neuroResp[nNum];
                n.deltaRule(neuroError, learning_ratio);

                nNum++;
            }
            System.out.println("Iter:" + j + " Info: " + tCase.getDescription());
            System.out.println("Exp. Val: " + Arrays.toString(tCase.getResults()) + " Output: " + Arrays.toString(neuroResp));
            //System.out.println("Error: " + neuroError);
            System.out.println();
        }
        System.out.println("FINISHED with: " + j + " iter.");

        double distortion_scalar = 0.1;
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
        for (TeachingCaseDAO.TeachingCase tCase : tc.getTeachCases()) {
            //System.out.println(tCase.getDescription());
            //System.out.println("Clear data set: " + Arrays.toString(tCase.getDataSet()));
            distortion = 2.0 * (random.nextDouble() - 0.5) * distortion_scalar;
            tCase.getDataSet()[targetIndex] += distortion;
            //System.out.println("Dist. data set: " + Arrays.toString(tCase.getDataSet()));

            int nNum = 0;
            for (Neuron n : network) {
                for (int i = 0; i < n.getInputsNum(); i++) {
                    n.setNInput(tCase.getDataSet()[i], i);
                }
                neuroResp[nNum] = n.getOutput();
                nNum++;
            }
            //System.out.println("Exp. Val: " + Arrays.toString(tCase.getResults()) + " Output: " + Arrays.toString(neuroResp));
            //System.out.println();
        }
    }

    public static void nonLinearMultilayerNetworkTrain() {
        //TODO pierwsze koncepcje sieci !!!


        ArrayList<Neuron> layer;
        ArrayList<ArrayList<Neuron>> network;
        network = new ArrayList<ArrayList<Neuron>>();

        TeachingCaseDAO tc = new TeachingCaseDAO("./plane_plot_problem");

        /**
         * Sieć dwie warstwy
         */
        
        /*Pierwsza warstwa*/
        layer = new ArrayList<Neuron>();
        for (int i = 0; i < tc.getOutNum() + 3; i++) {
            Neuron n = new Neuron(new BiSigmoidFunction(1.0), new LinearAgreggationFunction(), tc.getInNum(), true);

            layer.add(n);
        }

        network.add(layer);

        /*Druga warstwa*/
        layer = new ArrayList<Neuron>();
        for (int i = 0; i < tc.getOutNum(); i++) {
            Neuron n = new Neuron(new BiSigmoidFunction(1.0), new LinearAgreggationFunction(), tc.getOutNum() + 3, false);

            layer.add(n);
        }

        network.add(layer);


        /*Pierwsza warstwa wyjście*/


        TeachingCaseDAO.TeachingCase tCase = tc.getNextCase(); //opcja random case

        double ox[] = new double[100];
        double oy[] = new double[100];
        double oz[] = new double[100];


         Random random;
        random = new Random();

        FileWriter fstream = null;
        try {
            fstream = new FileWriter("/home/artur/Pulpit/anim/"+random.nextGaussian()+"main_plot");
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter out = new BufferedWriter(fstream);
        out = new BufferedWriter(fstream);

        double x = -5.0;
        for (int z = 0; z < 100; z++) {



            ox[z] = x;
            double y = -5.0;
            for (int j = 0; j < 100; j++) {
                try {
                    ArrayList<Double> hiddenSignals = new ArrayList<Double>();
                    ArrayList<Double> outSignals = new ArrayList<Double>();
                    oy[j] = y;

                    /*Pierwsza warstwa*/
                    for (Neuron n : network.get(0)) {
                        n.setNInput(ox[z], 0);
                        n.setNInput(oy[j], 1);
                        hiddenSignals.add(n.getOutput());
                    }

                    for (Neuron ne : network.get(1)) {
                        for (int i = 0; i < hiddenSignals.size(); i++) {
                            ne.setNInput(hiddenSignals.get(i), i);
                        }
                        //outSignals.add(ne.getOutput());
                        oz[j] = ne.getOutput();

                    }

                    out.write(ox[z] + " " + oy[j] + " " + oz[j]);
                    out.newLine();


                    System.out.println(ox[z] + " " + oy[j] + " " + oz[j]);
                    y += 0.1;
                } catch (IOException ex) {
                    Logger.getLogger(NonLinearTests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            x += 0.1;

        }

    }

    public static void plotSingleNeuron(int identifier, Neuron n) {

        n.diableAutoNormalisation();
        //TODO przesiew funkcji wg właściwości, heviside osto w górę + heviside unipolar i bipolar
        double ox[] = new double[100];
        double oy[] = new double[100];
        double oz[] = new double[100];




        FileWriter fstream = null;
        try {
            fstream = new FileWriter("/home/artur/Pulpit/anim/" + identifier + "_plot");
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter out = new BufferedWriter(fstream);
        out = new BufferedWriter(fstream);
        double x = -5.0;
        for (int i = 0; i < 100; i++) {
            ox[i] = x;
            double y = -5.0;
            for (int j = 0; j < 100; j++) {
                try {
                    oy[j] = y;
                    n.setNInput(ox[i], 0);
                    n.setNInput(oy[j], 1);
                    oz[j] = n.getOutput();

                    out.write(ox[i] + " " + oy[j] + " " + oz[j]);
                    out.newLine();
                    y += 0.1;
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            x += 0.1;
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        n.enableAutoNormalisation();
    }
}
