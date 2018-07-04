package com.highradius.partha.howlround;

public class TellFeedbackCount
{
    static  int positive;
    static int negative;

    public static int getPositive() {
        return positive;
    }

    public static void setPositive(int positive) {
        TellFeedbackCount.positive = positive;
    }

    public static int getNegative() {
        return negative;
    }

    public static void setNegative(int negative) {
        TellFeedbackCount.negative = negative;
    }
}
