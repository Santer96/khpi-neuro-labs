package com.lab3.neuro.entity;

public class Entry {

    private float xRangeStart;
    private float xRangeEnd;
    private float yRangeStart;
    private float yRangeEnd;
    private float step;

    private int xMaxAllowed;
    private int yMaxAllowed;
    private int xByteRepresentationLength;
    private int yByteRepresentationLength;
    private String xBytes;
    private String yBytes;
    private float F;

    public Entry(float xRangeStart, float xRangeEnd, float yRangeStart, float yRangeEnd, float step,
                 int xByteRepresentationLength, int yByteRepresentationLength) {
        this.xRangeStart = xRangeStart;
        this.xRangeEnd = xRangeEnd;
        this.yRangeStart = yRangeStart;
        this.yRangeEnd = yRangeEnd;
        this.step = step;
        this.xByteRepresentationLength = xByteRepresentationLength;
        this.yByteRepresentationLength = yByteRepresentationLength;
        xMaxAllowed = (int) ((xRangeEnd - xRangeStart) / step);
        yMaxAllowed = (int) ((yRangeEnd - yRangeStart) / step);
    }

    public Entry(EntryProps entryProps) {
        this.xRangeStart = entryProps.getxRangeStart();
        this.xRangeEnd = entryProps.getxRangeEnd();
        this.yRangeStart = entryProps.getyRangeStart();
        this.yRangeEnd = entryProps.getyRangeEnd();
        this.step = entryProps.getStep();
        this.xByteRepresentationLength = entryProps.getxByteRepresentationLength();
        this.yByteRepresentationLength = entryProps.getyByteRepresentationLength();
        xMaxAllowed = (int) ((xRangeEnd - xRangeStart) / step);
        yMaxAllowed = (int) ((yRangeEnd - yRangeStart) / step);
    }

    public void setXBytes(String s, String previousValue) {
        if (xRangeStart + (step * Integer.parseInt(s, 2)) > xRangeEnd
                || (xRangeStart + (step * Integer.parseInt(s, 2)) < xRangeStart)) {
            xBytes = previousValue;
            return;
        }
        if (s.length() == xByteRepresentationLength) {
            xBytes = s;
        }
    }

    public void setYBytes(String s, String previousValue) {
        if (yRangeStart + (step * Integer.parseInt(s, 2)) > yRangeEnd
                || (yRangeStart + (step * Integer.parseInt(s, 2)) < yRangeStart)) {
            yBytes = previousValue;
            return;
        }
        if (s.length() == yByteRepresentationLength) {
            yBytes = s;
        }
    }

    public String getXBytes() {
        return xBytes;
    }

    public String getYBytes() {
        return yBytes;
    }

    public float getX() {
        int xInt = Integer.parseInt(xBytes, 2);
        return xRangeStart + (step * xInt);
    }

    public float getY() {
        int yInt = Integer.parseInt(yBytes, 2);
        return yRangeStart + (step * yInt);
    }

    public void setX(float x) {
        if (x > xRangeEnd || x < xRangeStart) {
            return;
        }
        int xInt = (int) ((x - xRangeStart) / step);
        String s = Integer.toBinaryString(xInt);
        xBytes = String.format("%" + xByteRepresentationLength + "s", s).replaceAll(" ", "0");
    }

    public void setY(float y) {
        if (y > yRangeEnd || y < yRangeStart) {
            return;
        }
        int yInt = (int) ((y - yRangeStart) / step);
        String s = Integer.toBinaryString(yInt);
        yBytes = String.format("%" + yByteRepresentationLength + "s", s).replaceAll(" ", "0");
    }

    public float getF() {
        return F;
    }

    public void setF(float f) {
        F = f;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "xRangeStart=" + xRangeStart +
                ", xRangeEnd=" + xRangeEnd +
                ", yRangeStart=" + yRangeStart +
                ", yRangeEnd=" + yRangeEnd +
                ", step=" + step +
                ", xMaxAllowed=" + xMaxAllowed +
                ", yMaxAllowed=" + yMaxAllowed +
                ", xByteRepresentationLength=" + xByteRepresentationLength +
                ", yByteRepresentationLength=" + yByteRepresentationLength +
                ", xBytes='" + xBytes + '\'' +
                ", yBytes='" + yBytes + '\'' +
                ", F=" + F +
                '}';
    }
}
