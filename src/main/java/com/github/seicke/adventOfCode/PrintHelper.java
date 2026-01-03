package com.github.seicke.adventOfCode;

import java.util.List;

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

    public static <E> void print(List<E> list) {

        list.forEach(System.out::println);

    }

}
