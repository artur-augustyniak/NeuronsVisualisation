package pl.aaugustyniak.neural.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO dla Encji przypadków uczących
 *
 * @version 0.1
 * @author Artur Augustyniak
 */
public class TeachingCaseDAO {

    private File techningSet;
    private Scanner stream;
    private int inNum, outNum;
    private ArrayList<TeachingCase> teachCases = new ArrayList<TeachingCase>();
    private int counter = 0;
    private boolean shuffle = true;

    /**
     * Formalny interfejs dla wszystkich encji wejściowych
     */
    public interface DataCase {

        String getDescription();

        void setDescription(String description);

        double[] getDataSet();

        void setDataSet(double[] dataSet);
    };

    /**
     * Formalny interfejs dla wszystkich przypadków uczących
     */
    public interface TeachingCase extends DataCase {

        double getResult();

        void setResult(double result);

        double[] getResults();

        void setResults(double[] results);
    }

    public TeachingCaseDAO(String file) {
        this.TeachingCaseDAOinit(file);
    }

    public TeachingCaseDAO(String file, boolean shuffle) {
        this.shuffle = shuffle;
        this.TeachingCaseDAOinit(file);
    }

    private void TeachingCaseDAOinit(String file) {
        this.techningSet = new File(file);

        try {
            this.stream = new Scanner(this.techningSet);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TeachingCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String rawNetParams = this.stream.nextLine();
        StringTokenizer tokenizer = new StringTokenizer(rawNetParams, ", ");

        this.inNum = Integer.parseInt(tokenizer.nextToken());
        this.outNum = Integer.parseInt(tokenizer.nextToken());


        while (stream.hasNext()) {
            TeachingCase c = new RtadTeachingCase();
            c.setDescription(stream.nextLine());
            c.setDataSet(this.tokenizeInput(stream.nextLine()));
            if (this.outNum == 1) {
                c.setResult(Double.parseDouble(stream.nextLine()));
            } else {
                c.setResults(this.tokenizeInput(stream.nextLine()));
            }
            teachCases.add(c);
        }
    }

    private double[] tokenizeInput(String raw) {
        StringTokenizer tokenizer = new StringTokenizer(raw, ", ");
        double input[] = new double[tokenizer.countTokens()];
        for (int i = 0; i < input.length; i++) {
            input[i] = Double.parseDouble(tokenizer.nextToken());

        }
        return input;
    }

    public ArrayList<TeachingCase> getTeachCases() {
        return teachCases;
    }

    public int getInNum() {
        return inNum;
    }

    public int getOutNum() {
        return outNum;
    }

    private TeachingCase getRandomCase() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(this.teachCases.size());
        return this.teachCases.get(index);
    }

    public TeachingCase getNextCase() {
        if (this.shuffle) {
            return this.getRandomCase();
        }
        if (this.counter >= this.teachCases.size()) {
            this.counter = 0;
            return this.teachCases.get(0);
        }
        return this.teachCases.get(this.counter++);
    }
}
