package com.lab1.entity;

public class Entry {

    private final float x1;
    private final float x2;
    private final int d;

    private float y;
    private float epsilon;

    public Entry(float x1, float x2, int d) {
        this.x1 = x1;
        this.x2 = x2;
        this.d = d;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(float epsilon) {
        this.epsilon = epsilon;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public int getD() {
        return d;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", d=" + d +
                '}';
    }
}
