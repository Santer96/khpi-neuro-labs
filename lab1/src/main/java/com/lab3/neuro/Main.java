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
        List<Entry> entries = initialization();
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

    private static List<Entry> initialization() {
        List<Entry> entries = new LinkedList<>();
        for (float i = -0.3f; i < 1.8f; i += 0.5f) {
            for (float j = -4.5f; j < 0.5; j += 0.5f) {
                Entry e = new Entry(entryProps);
                e.setY(i);
                e.setX(j);
                entries.add(e);
            }
        }
        return entries;
    }
}
