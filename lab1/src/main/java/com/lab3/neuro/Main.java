package com.lab3.neuro;

import com.lab3.neuro.alghoritm.Function;
import com.lab3.neuro.alghoritm.Generation;
import com.lab3.neuro.alghoritm.Randomizer;
import com.lab3.neuro.entity.Entry;
import com.lab3.neuro.entity.EntryProps;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final float F_INCREMENT = 27f; // needed to avoid values less then 0
    private static final int ERAS = 2500;
    private static final String CSV_FILE_PATH = "src/main/resources/results.csv";
    private static final DecimalFormat df = new DecimalFormat("0.0");
    private static final EntryProps entryProps =
            new EntryProps(-5f, 1f, -1f, 3f, 0.1f, 6, 6);

    public static void main(String[] args) {
        List<Entry> entries = initialize();
        for (int i = 0; i < ERAS; i++) {
            // Calculate F and save results for current generation
            entries.forEach(e -> e.setF(Function.calculateF(e)));
            String[] results = parseResults(entries);
            appendCsv(CSV_FILE_PATH, results);

            // Cross entries
            List<Entry> newGenerationEntries = new LinkedList<>();
            List<Integer> indexesToCross = Randomizer.rouletteMethod(entries, Function.sumFModified(entries, F_INCREMENT), F_INCREMENT);
            Iterator<Integer> iterator = indexesToCross.iterator();
            while (iterator.hasNext()) {
                int i1 = iterator.next();
                if (!iterator.hasNext()) {
                    break;
                }
                int i2 = iterator.next();

                Generation.crossTwoEntries(newGenerationEntries, entryProps, entries.get(i1), entries.get(i2));
            }

            // Mutate with 1% chance
            newGenerationEntries.forEach(Generation::mutateEntry);
            // Switch generation lists
            entries = newGenerationEntries;
        }
    }

    private static String[] parseResults(List<Entry> entries) {
        Entry bestEntry = entries.stream().max((o1, o2) -> (int) ((o1.getF() * 100) - (o2.getF() * 100))).orElse(null);
        return new String[]{String.valueOf(Function.wholePopulationF(entries)),
                String.valueOf(bestEntry.getF()),
                bestEntry.getXBytes(),
                bestEntry.getYBytes(),
                df.format(bestEntry.getX()),
                df.format(bestEntry.getY())};
    }

    private static void appendCsv(String fileName, String[] params) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            csvWriter.writeNext(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Entry> initialize() {
        Entry e1 = new Entry(entryProps);
        Entry e2 = new Entry(entryProps);
        Entry e3 = new Entry(entryProps);
        Entry e4 = new Entry(entryProps);
        Entry e5 = new Entry(entryProps);
        Entry e6 = new Entry(entryProps);
        Entry e7 = new Entry(entryProps);
        Entry e8 = new Entry(entryProps);
        Entry e9 = new Entry(entryProps);
        Entry e10 = new Entry(entryProps);
        Entry e11 = new Entry(entryProps);
        Entry e12 = new Entry(entryProps);
        Entry e13 = new Entry(entryProps);
        Entry e14 = new Entry(entryProps);
        Entry e15 = new Entry(entryProps);
        Entry e16 = new Entry(entryProps);
        e1.setX(-4f);
        e2.setX(-4f);
        e3.setX(-2f);
        e4.setX(-2f);
        e5.setX(0f);
        e6.setX(0f);
        e7.setX(-3f);
        e8.setX(-3f);
        e9.setX(-1f);
        e10.setX(-1f);
        e11.setX(-4f);
        e12.setX(-3f);
        e13.setX(-2f);
        e14.setX(-1f);
        e15.setX(0f);
        e16.setX(0.5f);
        e1.setY(0f);
        e2.setY(2f);
        e3.setY(0f);
        e4.setY(2f);
        e5.setY(0f);
        e6.setY(2f);
        e7.setY(0f);
        e8.setY(2f);
        e9.setY(0f);
        e10.setY(2f);
        e11.setY(1f);
        e12.setY(1f);
        e13.setY(1f);
        e14.setY(1f);
        e15.setY(1f);
        e16.setY(0.5f);


        return Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16);
    }
}
