package com.lab1.util;

public class FormulasUtil {

    public static float calculateF(float k, float u) {
        return (float) (1f / (1f + Math.exp(-1 * k * u)));
    }

    public static float calculateFDash(float k, float u) {
        float F = calculateF(k, u);
        return F * k * Math.abs(1f - F);
    }
}
