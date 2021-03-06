package com.harrycodeman.compression.colorspaces;

import static java.lang.String.format;

public class ThreeComponentPixelBlock {
    private byte first;
    private byte second;
    private byte third;

    public ThreeComponentPixelBlock(byte first, byte second, byte third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public ThreeComponentPixelBlock(Integer first, Integer second, Integer third) {
        this.first = first.byteValue();
        this.second = second.byteValue();
        this.third = third.byteValue();
    }

    public byte getFirst() {
        return first;
    }

    public int getFirstAsPositiveInt() {
        return convertToPositiveInt(first);
    }

    private static int convertToPositiveInt(Byte value) {
        final int byteMask = 255;
        return value.intValue() & byteMask;
    }

    public byte getSecond() {
        return second;
    }

    public int getSecondAsPositiveInt() {
        return convertToPositiveInt(second);
    }

    public int getThirdAsPositiveInt() {
        return convertToPositiveInt(third);
    }

    public byte getThird() {
        return third;
    }

    @Override
    public boolean equals(Object other) {
        return other.getClass() == ThreeComponentPixelBlock.class
                && equals((ThreeComponentPixelBlock)other);
    }

    public boolean equals(ThreeComponentPixelBlock other) {
        return first == other.first
                && second == other.second
                && third == other.third;
    }

    @Override
    public String toString() {
        return format("[%01$d; %02$d; %03$d]", first, second, third);
    }
}
