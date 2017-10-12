package pl.aaugustyniak.neural.diagnistic;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.sf.surfaceplot.ExampleSurfaceModel;
import org.sf.surfaceplot.SurfaceCanvas;


import pl.aaugustyniak.neural.app.main;
import pl.aaugustyniak.neural.elements.Neuron;
import pl.aaugustyniak.neural.elements.helpers.SignOperatorEnum;
import pl.aaugustyniak.neural.elements.interfaces.ContinuousFunctionInterface;
import pl.aaugustyniak.neural.functions.agreggation.LinearAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.SquareAgreggationFunction;
import pl.aaugustyniak.neural.functions.transfer.BiSigmoidFunction;
import pl.aaugustyniak.neural.functions.transfer.GaussErrorFunction;
import pl.aaugustyniak.neural.functions.transfer.GaussianFunction;
import pl.aaugustyniak.neural.functions.transfer.GeneralisedLogisticFunction;
import pl.aaugustyniak.neural.functions.transfer.LinearFunction;
import pl.aaugustyniak.neural.functions.transfer.NearHeavisideUniSigmFunction;
import pl.aaugustyniak.neural.functions.transfer.SoftmaxActivationFunction;
import pl.aaugustyniak.neural.functions.transfer.TrigFunction;
import pl.aaugustyniak.neural.functions.transfer.UniSigmoidFunction;

/**
 * Klasa debugerska, proste dane do wykresów chrakterystyk
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class Plotter {

    public static void plotSingleNeuron(Neuron n) {



        n.diableAutoNormalisation();

        double ox[] = new double[100];
        double oy[] = new double[100];
        double oz[] = new double[100];

        double x = -10.0;
        for (int i = 0; i < 100; i++) {
            ox[i] = x;
            double y = -10.0;
            for (int j = 0; j < 100; j++) {
                oy[j] = y;
                n.setNInput(ox[i], 0);
                n.setNInput(oy[j], 1);
                oz[j] = n.getOutput();

                //out.write(ox[i] + " " + oy[j] + " " + oz[j]);
                //out.newLine();
                y += 0.2;
            }
            x += 0.2;
        }


        javax.swing.JPanel centerPanel = new javax.swing.JPanel();
        ExampleSurfaceModel model = new ExampleSurfaceModel();
        SurfaceCanvas canvas = new SurfaceCanvas();
        canvas.setModel(model);
        centerPanel.add(canvas, BorderLayout.CENTER);
        canvas.repaint();

        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(centerPanel);
        
        
        
        
        
        frame.setVisible(true);
        frame.pack();
        //centerPanel.setVisible(true);
        n.enableAutoNormalisation();
    }

    public static void plotFunctions() {

        double beta = 1.0;
        ArrayList<ContinuousFunctionInterface> actFuncs;
        actFuncs = new ArrayList<ContinuousFunctionInterface>();
        //actFuncs.add(new LinearFunction());
        //actFuncs.add(new UniSigmoidFunction(0.1));
        //actFuncs.add(new UniSigmoidFunction(0.4));
        //actFuncs.add(new UniSigmoidFunction(0.6));
        actFuncs.add(new UniSigmoidFunction(1.0));
        // actFuncs.add(new UniSigmoidFunction(5.0));
//        actFuncs.add(new BiSigmoidFunction(beta));
//        actFuncs.add(new AlgebraicSigmoidFunction(beta));
//        actFuncs.add(new GaussErrorFunction(beta));
//        actFuncs.add(new TrigFunction(new TrigFunction.Sin(), beta));
//        actFuncs.add(new TrigFunction(new TrigFunction.Cos(), beta));
//        actFuncs.add(new NearHeavisideUniSigmFunction());
//        actFuncs.add(new GeneralisedLogisticFunction(beta));
//        actFuncs.add(new GaussianFunction(beta));


        double ox[] = new double[100];
        double oy[] = new double[100];

        Random random;
        random = new Random();
        for (ContinuousFunctionInterface actFun : actFuncs) {
            FileWriter fstream = null;
            try {
                fstream = new FileWriter("/home/artur/Pulpit/sigmoid/" + actFun.getClass().getName() + random.nextGaussian() + ".plot");
                BufferedWriter out = new BufferedWriter(fstream);
                double x = -2.0;
                for (int i = 0; i < 100; i++) {
                    ox[i] = x;
                    oy[i] = actFun.evaluate(x);
                    out.write(ox[i] + " " + oy[i]);
                    out.newLine();
                    x += 0.04;
                }
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fstream.close();
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        }


    }

    public static void plotNeurons() {

        double beta = 1.0;
        ArrayList<ContinuousFunctionInterface> actFuncs;
        actFuncs = new ArrayList<ContinuousFunctionInterface>();
        actFuncs.add(new LinearFunction());
        actFuncs.add(new UniSigmoidFunction(beta));
        actFuncs.add(new BiSigmoidFunction(beta));
        
        actFuncs.add(new GaussErrorFunction(beta));
        actFuncs.add(new TrigFunction(new TrigFunction.Sin(), beta));
        actFuncs.add(new TrigFunction(new TrigFunction.Cos(), beta));
        actFuncs.add(new NearHeavisideUniSigmFunction());
        actFuncs.add(new GeneralisedLogisticFunction(beta));
        actFuncs.add(new GaussianFunction(beta));

        Random random;
        random = new Random();
        // 2.0 * (random.nextDouble() - 0.5) * RANDOM_SCALAR;           
        //TODO http://code.google.com/p/jmathplot/
        Neuron n;
        n = new Neuron(new LinearAgreggationFunction(), 2, true);

        double ox[] = new double[100];
        double oy[] = new double[100];
        double oz[] = new double[100];

        for (ContinuousFunctionInterface actFun : actFuncs) {
            FileWriter fstream = null;
            try {
                n.setActFun(actFun);
                fstream = new FileWriter("/home/artur/plots/neurons/" + actFun.getClass().getName() + random.nextGaussian() + ".plot");
                BufferedWriter out = new BufferedWriter(fstream);
                double x = -2.0;
                for (int i = 0; i < 100; i++) {
                    ox[i] = x;
                    double y = -2.0;
                    for (int j = 0; j < 100; j++) {
                        oy[j] = y;
                        n.setNInput(ox[i], 0);
                        n.setNInput(oy[j], 1);
                        //n.setNInput(random.nextGaussian(), 2);
                        //n.setNInput(random.nextGaussian(), 3);
                        //n.setNInput(random.nextGaussian(), 4);

                        oz[j] = n.getOutput();



                        out.write(ox[i] + " " + oy[j] + " " + oz[j]);
                        out.newLine();
                        y += 0.04;
                    }
                    x += 0.04;
                }
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fstream.close();
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        }

        FileWriter fstream = null;
        n.setActFun(new BiSigmoidFunction(beta));
        n.setAgrFun(new SquareAgreggationFunction(SignOperatorEnum.MINUS));
        try {
            fstream = new FileWriter("/home/artur/plots/neurons/SquareAgreggationFunction" + random.nextGaussian() + ".plot");
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter out = new BufferedWriter(fstream);
        double x = -2.0;
        for (int i = 0; i < 100; i++) {
            ox[i] = x;
            double y = -2.0;
            for (int j = 0; j < 100; j++) {
                try {
                    oy[j] = y;
                    n.setNInput(ox[i], 0);
                    n.setNInput(oy[j], 1);
                    //n.setNInput(random.nextGaussian(), 2);
                    //n.setNInput(random.nextGaussian(), 3);
                    //n.setNInput(random.nextGaussian(), 4);

                    oz[j] = n.getOutput();

                    out.write(ox[i] + " " + oy[j] + " " + oz[j]);
                    out.newLine();
                    y += 0.04;
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            x += 0.04;
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }





        n.setActFun(new SoftmaxActivationFunction(n));
        try {
            fstream = new FileWriter("/home/artur/plots/neurons/SoftmaxActivationFunction" + random.nextGaussian() + ".plot");
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        out = new BufferedWriter(fstream);
        x = -2.0;
        for (int i = 0; i < 100; i++) {
            ox[i] = x;
            double y = -2.0;
            for (int j = 0; j < 100; j++) {
                try {
                    oy[j] = y;
                    n.setNInput(ox[i], 0);
                    n.setNInput(oy[j], 1);
                    //n.setNInput(random.nextGaussian(), 2);
                    //n.setNInput(random.nextGaussian(), 3);
                    //n.setNInput(random.nextGaussian(), 4);

                    oz[j] = n.getOutput();

                    out.write(ox[i] + " " + oy[j] + " " + oz[j]);
                    out.newLine();
                    y += 0.04;
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            x += 0.04;
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
