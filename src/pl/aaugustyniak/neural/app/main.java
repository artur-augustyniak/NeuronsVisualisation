/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.neural.app;

import pl.aaugustyniak.neural.view.NeuronSurfPlot;

/**
 *
 * @author artur
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NeuronSurfPlot().setVisible(true);
            }
        });
    }
}
