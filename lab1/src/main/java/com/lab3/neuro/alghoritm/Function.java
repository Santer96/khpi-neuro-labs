package com.lab3.neuro.alghoritm;

import com.lab3.neuro.entity.Entry;

import java.util.List;

public class Function {
    public static float calculateF(Entry e) {
        return (4 * e.getX()) - (2 * e.getY());
    }

    public static float sumF(List<Entry> entries) {
        float result = 0f;
        for (Entry e : entries) {
            result += e.getF();
        }
        return result;
    }

    public static float wholePopulationF(List<Entry> entries) {
        return sumF(entries) / entries.size();
    }

    public static float calculateFModified(Entry e, float increaseEach) {
        return (4 * e.getX()) - (2 * e.getY()) + increaseEach;
    }

    public static float sumFModified(List<Entry> entries, float increaseEach) {
        float result = 0f;
        for (Entry e : entries) {
            result += e.getF() + increaseEach;
        }
        return result;
    }

    public static float wholePopulationFModified(List<Entry> entries, float increaseEach) {
        return sumFModified(entries, increaseEach) / entries.size();
    }
}
