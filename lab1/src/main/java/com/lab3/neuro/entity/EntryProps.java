package com.lab3.neuro.entity;

public class EntryProps {

    private float xRangeStart;
    private float xRangeEnd;
    private float yRangeStart;
    private float yRangeEnd;
    private float step;

    private int xByteRepresentationLength;
    private int yByteRepresentationLength;

    public EntryProps(float xRangeStart, float xRangeEnd, float yRangeStart, float yRangeEnd, float step,
                      int xByteRepresentationLength, int yByteRepresentationLength) {
        this.xRangeStart = xRangeStart;
        this.xRangeEnd = xRangeEnd;
        this.yRangeStart = yRangeStart;
        this.yRangeEnd = yRangeEnd;
        this.step = step;
        this.xByteRepresentationLength = xByteRepresentationLength;
        this.yByteRepresentationLength = yByteRepresentationLength;
    }

    public float getxRangeStart() {
        return xRangeStart;
    }

    public float getxRangeEnd() {
        return xRangeEnd;
    }

    public float getyRangeStart() {
        return yRangeStart;
    }

    public float getyRangeEnd() {
        return yRangeEnd;
    }

    public float getStep() {
        return step;
    }
    public int getxByteRepresentationLength() {
        return xByteRepresentationLength;
    }

    public int getyByteRepresentationLength() {
        return yByteRepresentationLength;
    }
}
