package com.github.seicke.adventOfCode;

import java.util.Arrays;

public class PrintHelper {

    public static void print(char[][] charArray) {

        for (char[] charRow : charArray) {
            System.out.println(Arrays.toString(charRow));
        }

    }

}
