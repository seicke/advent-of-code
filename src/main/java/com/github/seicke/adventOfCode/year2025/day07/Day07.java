package com.github.seicke.adventOfCode.year2025.day07;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day07 {

    private static final Logger log = LoggerFactory.getLogger(Day07.class);

    private static final int year = 2025;
    private static final int day = 7;

    private static final int TEST_ANSWER_PART1 = 21;
    private static final long TEST_ANSWER_PART2 = 40L;

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        char[][] charArray = DataLoader.getCharArray(year, day, true);

        int answer_part1 = numberOfBeamSplittings(charArray);
        long answer_part2 = numberOfDifferentTimelines(charArray);

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        char[][] charArray = DataLoader.getCharArray(year, day, false);

        int answer_part1 = numberOfBeamSplittings(charArray);
        long answer_part2 = numberOfDifferentTimelines(charArray);

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); // 1633

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); // 34339203133559

    }

    private static int numberOfBeamSplittings(char[][] charArray) {

        char[][] beamSplittingArray = performBeamSplitting(charArray);

        int answer = 0;

        int rowsCount = charArray.length;
        int colsCount = charArray[0].length;

        for (int row = 0; row < rowsCount; row++) {
            for (int col = 0; col < colsCount; col++) {
                switch (beamSplittingArray[row][col]) {
                    case '^':
                        answer++;
                        break;
                }
            }
        }

        return answer;
    }

    private static long numberOfDifferentTimelines(char[][] charArray) {

        char[][] beamSplittingArray = performBeamSplitting(charArray);

        // Convert char[][] to String[][]
        String[][] stringsArray = new String[beamSplittingArray.length][beamSplittingArray[0].length];
        for (int i = 0; i < beamSplittingArray.length; i++) {
            for (int j = 0; j < beamSplittingArray[i].length; j++) {

                stringsArray[i][j] = String.valueOf(beamSplittingArray[i][j]);

            }
        }

        int rowsCount = stringsArray.length;
        int colsCount = stringsArray[0].length;

        // Solution: Pascal's triangle
        for (int row = 0; row < rowsCount; row++) {
            for (int col = 0; col < colsCount; col++) {
                switch (stringsArray[row][col]) {
                    case "S":
                        if (row + 1 < rowsCount && stringsArray[row + 1][col].equals("|")) {
                            stringsArray[row][col] = "1";
                            stringsArray[row + 1][col] = "1";
                        }
                        break;
                    case "|":

                        Long beamCounter = 0L;

                        String above = stringsArray[row - 1][col];
                        try {
                            Long aboveNumber = Long.parseLong(above);
                            beamCounter += aboveNumber;
                        } catch (Exception e) {
                        }

                        if (col - 1 >= 0 && stringsArray[row][col - 1].equals("^")) {

                            Long aboveSplitterNumber = Long.parseLong(stringsArray[row - 1][col - 1]);
                            beamCounter += aboveSplitterNumber;

                        }

                        if (col + 1 < colsCount && stringsArray[row][col + 1].equals("^")) {

                            Long aboveSplitterNumber = Long.parseLong(stringsArray[row - 1][col + 1]);
                            beamCounter += aboveSplitterNumber;

                        }

                        stringsArray[row][col] = String.valueOf(beamCounter);

                        break;

                }
            }
        }

        long answer = 0L;

        // Add up beam numbers of last row
        for (int col = 0; col < colsCount; col++) {
            try {
                answer += Long.parseLong(stringsArray[rowsCount - 1][col]);
            } catch (Exception e) {
                // try/catch needed cause there are "." strings
            }
        }

        return answer;

    }

    private static char[][] performBeamSplitting(char[][] charArray) {

        char[][] array = Arrays.stream(charArray)
                .map(char[]::clone)
                .toArray(char[][]::new);

        int rowsCount = charArray.length;
        int colsCount = charArray[0].length;

        for (int row = 0; row < rowsCount; row++) {
            cols: for (int col = 0; col < colsCount; col++) {

                // Add splitted beams
                switch (array[row][col]) {
                    case 'S':

                        if (row + 1 < rowsCount && array[row + 1][col] == '.') {
                            array[row + 1][col] = '|';
                        }

                        break;

                    case '|':

                        if (row + 1 < rowsCount && array[row + 1][col] == '.') {
                            array[row + 1][col] = '|';
                        }

                        break;

                    case '^':

                        if (row - 1 >= 0 && array[row - 1][col] != '|') {
                            // Remove unnecessary/unused splitter
                            array[row][col] = '.';
                            continue cols;
                        }

                        if (col - 1 >= 0 && array[row][col - 1] == '.') {
                            array[row][col - 1] = '|';
                            col--;
                            col--;
                            continue cols;
                        }

                        if (col + 1 < colsCount && array[row][col + 1] == '.') {
                            array[row][col + 1] = '|';
                        }

                        break;

                }
            }
        }

        return array;

    }

}
