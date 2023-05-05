package com.lab3.neuro.alghoritm;

import com.lab3.neuro.entity.Entry;
import com.lab3.neuro.entity.EntryProps;
import javafx.util.Pair;

import java.util.List;

public class Generation {
    public static void crossTwoEntries(List<Entry> newGenerationList, EntryProps props, Entry e1, Entry e2) {
        Entry newE1 = new Entry(props);
        Entry newE2 = new Entry(props);
        Pair<String, String> newXBites = crossBiteStrings(e1.getXBytes(), e2.getXBytes());
        newE1.setXBytes(newXBites.getKey(), e1.getXBytes());
        newE2.setXBytes(newXBites.getValue(), e2.getXBytes());
        Pair<String, String> newYBites = crossBiteStrings(e1.getYBytes(), e2.getYBytes());
        newE1.setYBytes(newYBites.getKey(), e1.getYBytes());
        newE2.setYBytes(newYBites.getValue(), e2.getYBytes());

        newGenerationList.add(newE1);
        newGenerationList.add(newE2);
    }

    public static void mutateEntry(Entry entry) {
        if (Randomizer.getRandomFloat(0f, 100f) < 1f) {
            char[] xCharArray = entry.getXBytes().toCharArray();
            int indexX = Randomizer.getRandomInt(0, xCharArray.length - 1);
            xCharArray[indexX] = xCharArray[indexX] == '0' ? '1' : '0';
            entry.setXBytes(String.valueOf(xCharArray), entry.getXBytes());
        }
        if (Randomizer.getRandomFloat(0f, 100f) < 1f) {
            char[] yCharArray = entry.getYBytes().toCharArray();
            int indexY = Randomizer.getRandomInt(0, yCharArray.length - 1);
            yCharArray[indexY] = yCharArray[indexY] == '0' ? '1' : '0';
            entry.setYBytes(String.valueOf(yCharArray), entry.getYBytes());
        }
    }

    private static Pair<String, String> crossBiteStrings(String s1, String s2) {
        int index = Randomizer.getRandomInt(1, s1.length() - 1);
        String r1 = s1.substring(0, index) + s2.substring(index);
        String r2 = s2.substring(0, index) + s1.substring(index);
        return new Pair<>(r1, r2);
    }
}
