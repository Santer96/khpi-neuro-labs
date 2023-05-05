package com.lab3.neuro.alghoritm;

import com.lab3.neuro.entity.Entry;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static List<Integer> rouletteMethod(List<Entry> entities, float sumF, float increaseF) {
        List<Integer> winnerIndexes = new LinkedList<Integer>();
        for (int i = 0; i < entities.size(); i++) {
            float rouletteResult = getRandomFloat(0f, 100f);
            float currentPointer = 0f;
            for (int j = 0; j < entities.size(); j++) {
                currentPointer += ((entities.get(j).getF() + increaseF) / sumF) * 100;
                if (rouletteResult <= currentPointer) {
                    winnerIndexes.add(j);
                    break;
                }
            }
        }
        return winnerIndexes;
    }

    public static float getRandomFloat(float min, float max) {
        return (float) (min + Math.random() * (max - min));
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
