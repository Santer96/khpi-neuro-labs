package com.lab1;

import com.lab1.entity.Entry;
import com.lab1.entity.Result;
import com.lab1.entity.WeighCoef;
import com.lab1.generator.Generator;
import com.lab1.util.FormulasUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int eras = 5000;
    private static final float k = 1f;
    private static final float eta = 1f;

    private static final String EPSILON_FILE = "src/main/resources/epsilon.csv";
    private static final String WEIGHTS_FILE_NAME = "src/main/resources/weights.csv";

    private static WeighCoef weighCoef;
    private static final List<Entry> trainData = new ArrayList<>();
    private static final List<Entry> testData = new ArrayList<>();

    public static void main(String[] args) {
        initialize();

        for (int i = 0; i < eras; i++) {
            Result result = new Result();
            for (Entry entry : trainData) {
                float epsilon = calculateEntry(entry, true);
                result.increaseTrain((float) Math.pow(epsilon, 2));
            }
            for (Entry entry : testData) {
                float epsilon = calculateEntry(entry, false);
                result.increaseTest((float) Math.pow(epsilon, 2));
            }

            appendCsv(EPSILON_FILE, parseResult(result, trainData.size(), testData.size(), i));
            appendCsv(WEIGHTS_FILE_NAME, parseWeights(weighCoef, i));
        }
    }

    private static float calculateEntry(Entry entry, boolean recalculateWeights) {
        float u11 = weighCoef.getW110() * 1 + weighCoef.getW111() * entry.getX1() + weighCoef.getW112() * entry.getX2();
        float u12 = weighCoef.getW120() * 1 + weighCoef.getW121() * entry.getX1() + weighCoef.getW122() * entry.getX2();
        float y11 = FormulasUtil.calculateF(k, u11);
        float y12 = FormulasUtil.calculateF(k, u12);
        float u21 = weighCoef.getW210() * 1 + weighCoef.getW211() * y11 + weighCoef.getW212() * y12;

        entry.setY(FormulasUtil.calculateF(k, u21));
        entry.setEpsilon(entry.getD() - entry.getY());

        if (recalculateWeights) {
            weighCoef.setW210(weighCoef.getW210() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)));
            weighCoef.setW211(weighCoef.getW211() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21) * y11));
            weighCoef.setW212(weighCoef.getW212() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21) * y12));

            weighCoef.setW110(weighCoef.getW110() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW211() * FormulasUtil.calculateFDash(k, u11)));
            weighCoef.setW111(weighCoef.getW111() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW211() * FormulasUtil.calculateFDash(k, u11) * entry.getX1()));
            weighCoef.setW112(weighCoef.getW112() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW211() * FormulasUtil.calculateFDash(k, u11) * entry.getX2()));

            weighCoef.setW120(weighCoef.getW120() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW212() * FormulasUtil.calculateFDash(k, u12)));
            weighCoef.setW121(weighCoef.getW121() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW212() * FormulasUtil.calculateFDash(k, u12) * entry.getX1()));
            weighCoef.setW122(weighCoef.getW122() + (eta * entry.getEpsilon() * FormulasUtil.calculateFDash(k, u21)
                    * weighCoef.getW212() * FormulasUtil.calculateFDash(k, u12) * entry.getX2()));
        }

        return entry.getEpsilon();
    }

    private static void initialize() {
        readTrainData();
        fillTestData();

        weighCoef = new WeighCoef();
        weighCoef.setW110(0.1f);
        weighCoef.setW111(-0.3f);
        weighCoef.setW112(0.4f);
        weighCoef.setW120(-0.7f);
        weighCoef.setW121(-0.1f);
        weighCoef.setW122(0.01f);
        weighCoef.setW210(0.4f);
        weighCoef.setW211(-0.2f);
        weighCoef.setW212(0.1f);
    }

    private static void readTrainData() {
        try (FileReader filereader = new FileReader(Generator.FILE_NAME);
             CSVReader csvReader = new CSVReader(filereader)) {

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Entry entry = new Entry(Float.parseFloat(nextRecord[0]),
                        Float.parseFloat(nextRecord[1]),
                        Integer.parseInt(nextRecord[2]));
                trainData.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fillTestData() {
        Entry entry00 = new Entry(0f, 0f, 0);
        Entry entry01 = new Entry(0f, 1f, 1);
        Entry entry10 = new Entry(1f, 0f, 1);
        Entry entry11 = new Entry(1f, 1f, 0);

        testData.add(entry00);
        testData.add(entry01);
        testData.add(entry10);
        testData.add(entry11);
    }

    private static void appendCsv(String fileName, String[] params) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            csvWriter.writeNext(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] parseResult(Result result, int kTrain, int kTest, int age) {
        float trainE = (float) result.getTrainE();
        float trainEAverage = trainE / kTrain;
        float testE = (float) result.getTestE();
        float testEAverage = testE / kTest;

        return new String[]{"era-" + age, String.valueOf(trainE), String.valueOf(trainEAverage),
                String.valueOf(testE), String.valueOf(testEAverage)};
    }

    private static String[] parseWeights(WeighCoef weighCoef, int era) {
        return new String[]{
                "era-" + era,
                String.valueOf(weighCoef.getW110()),
                String.valueOf(weighCoef.getW111()),
                String.valueOf(weighCoef.getW112()),
                String.valueOf(weighCoef.getW120()),
                String.valueOf(weighCoef.getW121()),
                String.valueOf(weighCoef.getW122()),
                String.valueOf(weighCoef.getW210()),
                String.valueOf(weighCoef.getW211()),
                String.valueOf(weighCoef.getW212()),
        };
    }
}
