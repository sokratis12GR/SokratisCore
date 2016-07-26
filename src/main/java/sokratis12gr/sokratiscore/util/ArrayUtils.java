package sokratis12gr.sokratiscore.util;

import java.util.Locale;

/**
 * sokratis12gr.sokratiscore.util
 * SokratisCore created by sokratis12GR on 7/26/2016 1:59 PM.
 */
public class ArrayUtils {

    public static String[] arrayToLowercase(String[] array) {
        String[] lowercaseArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            lowercaseArray[i] = array[i].toLowerCase(Locale.ENGLISH);
        }
        return lowercaseArray;
    }

    /**
     * Shift all objects in an array by the number of places specified.
     * <p/>
     * Example:
     * Object[] array = {1, 2, 3, 4, 5}
     * array = arrayShift(array, 1);
     * array now equals {5, 1, 2, 3, 4}
     * Shift can be a positive or negative number
     * <p/>
     * Shift is not restricted to any max or min value so it could for example be linked to a counter that
     * continuously increments.
     */
    public static Object[] arrayShift(Object[] input, int shift) {
        Object[] newArray = new Object[input.length];

        for (int i = 0; i < input.length; i++) {
            int newPos = (i + shift) % input.length;

            if (newPos < 0) {
                newPos += input.length;
            }

            newArray[newPos] = input[i];
        }

        return newArray;
    }
}