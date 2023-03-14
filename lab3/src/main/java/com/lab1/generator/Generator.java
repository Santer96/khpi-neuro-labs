package com.lab1.generator;

import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final int ENTRY_NUMBER = 1000;
    public static final String FILE_NAME = "src/main/resources/entryData.csv";

    public static void main(String[] args) {
        List<String[]> entryList = new ArrayList<String[]>();

        for (int i = 0; i < ENTRY_NUMBER; i++) {
            float x1 = generateRandomFloat();
            float x2 = generateRandomFloat();
            int d = (x2 < (x1 + 0.5f) && x2 > (x1 - 0.5f)) ? 0 : 1;
            entryList.add(new String[]{String.valueOf(x1), String.valueOf(x2), String.valueOf(d)});
        }

        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(osw)) {

            writer.writeAll(entryList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static float generateRandomFloat()
    {
        float rangeMin = 0.0f;
        float rangeMax = 1.0f;
        Random r = new Random();
        return ((float) (rangeMin + (rangeMax - rangeMin) * r.nextDouble()));
    }
}
