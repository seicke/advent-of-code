package com.github.seicke.adventOfCode;

public class PrintHelper {

    public static void print(char[][] charArray) {

        for (char[] charRow : charArray) {
            System.out.println(new String(charRow));
        }

    }

    public static void print(String[][] stringArray) {

        for (String[] stringRow : stringArray) {
            System.out.println(String.join("", stringRow));
        }

    }

}
