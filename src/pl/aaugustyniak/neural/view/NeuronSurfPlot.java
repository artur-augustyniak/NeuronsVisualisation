/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NeuronSurfPlot.java
 *
 * Created on Mar 13, 2010, 2:46:54 PM
 */
package pl.aaugustyniak.neural.view;

import java.awt.BorderLayout;
import java.awt.Color;
import org.sf.surfaceplot.ISurfacePlotModel;
import org.sf.surfaceplot.SurfaceCanvas;
import pl.aaugustyniak.neural.elements.Neuron;
import pl.aaugustyniak.neural.functions.agreggation.BicentralAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.LinearAgreggationFunction;
import pl.aaugustyniak.neural.functions.agreggation.RidellaAgreggationQuadricFunction;
import pl.aaugustyniak.neural.functions.agreggation.RidellaAgreggationRadialFunction;
import pl.aaugustyniak.neural.functions.transfer.BiCentralFunction;
import pl.aaugustyniak.neural.functions.transfer.BiSigmoidFunction;
import pl.aaugustyniak.neural.functions.transfer.GaussianFunction;
import pl.aaugustyniak.neural.functions.transfer.UniSigmoidFunction;
import pl.aaugustyniak.neural.view.model.NetworkSurfaceModel;
import pl.aaugustyniak.neural.view.model.NeuronSurfaceModel;

/**
 *
 * @author siva
 */
public class NeuronSurfPlot extends javax.swing.JFrame {

    /**
     * Creates new form NeuronSurfPlot
     */
    public NeuronSurfPlot() {
        initComponents();
        setSize(1000, 800);
        this.getContentPane().setBackground(Color.BLACK);
        //Neuron n = new Neuron(new UniSigmoidFunction(31.0), new LinearAgreggationFunction(), 2, true);
        //Neuron n = new Neuron(new GaussianFunction(34.4), new LinearAgreggationFunction(), 2, true);
        Neuron n = new Neuron(new BiSigmoidFunction(5.1), new BicentralAgreggationFunction(), 2, true);
        
        ISurfacePlotModel model = new NeuronSurfaceModel(n);
        //ISurfacePlotModel model = new NetworkSurfaceModel(n);
        
        SurfaceCanvas canvas = new SurfaceCanvas();
        canvas.setModel(model);
        canvas.setBackground(Color.yellow);
        centerPanel.add(canvas, BorderLayout.CENTER);  
        centerPanel.setBackground(Color.yellow);
        canvas.repaint();
        setVisible(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        centerPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        centerPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        jLabel1.setText("Rotate: Mouse Click & Drag");
        jPanel1.add(jLabel1);

        jLabel2.setText("Zoom: Shift Key + Mouse Click & Drag");
        jPanel1.add(jLabel2);

        jLabel3.setText("Move: Control Key + Mouse Click & Drag");
        jPanel1.add(jLabel3);

        centerPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new NeuronSurfPlot().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
