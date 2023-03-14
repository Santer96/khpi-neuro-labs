package com.lab1.entity;

public class Result {

    private float trainEpsilonSquareSum = 0;
    private float testEpsilonSquareSum = 0;

    public void increaseTrain(float f) {
        trainEpsilonSquareSum += f;
    }

    public void increaseTest(float f) {
        testEpsilonSquareSum += f;
    }

    public double getTrainE() {
        return trainEpsilonSquareSum / 2;
    }

    public double getTestE() {
        return testEpsilonSquareSum / 2;
    }
}
