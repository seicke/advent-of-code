package com.github.seicke.adventOfCode.year2025.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day06 {

    private static final Logger log = LoggerFactory.getLogger(Day06.class);

    private static final int year = 2025;
    private static final int day = 6;

    private static final long TEST_ANSWER_PART1 = 4277556L;
    private static final long TEST_ANSWER_PART2 = 3263827L;

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        List<String> lines = DataLoader.getStringLines(year, day, true);
        char[][] charArray = DataLoader.getCharArray(year, day, true);

        long answer_part1 = cephalopodMath(lines);
        long answer_part2 = cephalopodMathRightToLeft(charArray);

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        List<String> lines = DataLoader.getStringLines(year, day, false);
        char[][] charArray = DataLoader.getCharArray(year, day, false);

        long answer_part1 = cephalopodMath(lines);
        long answer_part2 = cephalopodMathRightToLeft(charArray);

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); // 4878670269096

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); // 8674740488592

    }

    private static long cephalopodMath(List<String> lines) {

        int rowsCount = lines.size();
        int colsCount = lines.get(0).replaceAll("\\s+", " ").split(" ").length;

        // 1. Extract numbers and math operators
        int[][] matrixInts = new int[rowsCount - 1][colsCount];
        String[] mathOperators = new String[colsCount];

        int numberOfLine = 1;
        for (String line : lines) {

            line = line.trim().replaceAll("\\s+", " ");

            if (numberOfLine == rowsCount) {

                mathOperators = Arrays.stream(line.split(" "))
                        .map(String::trim)
                        .toArray(String[]::new);

                break;
            }

            matrixInts[numberOfLine - 1] = Arrays.stream(line.split(" "))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            numberOfLine++;

        }

        // 2. Calculations
        long[] mathResults = new long[colsCount];

        for (int col = 0; col < colsCount; col++) {

            for (int row = 0; row < rowsCount - 1; row++) {

                String mathOperator = mathOperators[col];

                if (row == 0) {

                    mathResults[col] = (long) matrixInts[row][col];

                } else {

                    switch (mathOperator) {

                        case "+":

                            mathResults[col] += matrixInts[row][col];
                            break;

                        case "*":

                            mathResults[col] *= matrixInts[row][col];
                            break;
                    }
                }

            }
        }

        long answer = Arrays.stream(mathResults).sum();

        return answer;
    }

    private static long cephalopodMathRightToLeft(char[][] charArray) {

        int rowsCount = charArray.length;
        int colsCount = charArray[0].length;

        List<Long> mathResults = new ArrayList<Long>();

        List<Integer> numbers = new ArrayList<Integer>();

        char mathOperator = ' ';

        for (int col = colsCount - 1; col >= 0; col--) {

            // 1. Determine numbers and math operator
            String numberString = "";

            for (int row = 0; row <= rowsCount - 1; row++) {
                if (row == rowsCount - 1 && charArray[row][col] != ' ') {

                    mathOperator = charArray[row][col];

                } else {

                    numberString += charArray[row][col];

                }
            }

            numberString = numberString.trim();

            if (numberString.isEmpty())
                continue;

            numbers.add(Integer.parseInt(numberString));

            // 2. Calculate
            if (mathOperator != ' ') {

                Long mathResult = 0L;

                for (Integer number : numbers) {

                    if (mathResult == 0L) {

                        mathResult = Long.parseLong(number.toString());

                    } else {

                        switch (mathOperator) {
                            case '+':

                                mathResult += Long.parseLong(number.toString());
                                break;

                            case '*':

                                mathResult *= Long.parseLong(number.toString());
                                break;
                        }
                    }
                }

                mathResults.add(mathResult);

                // Reset number and math operator
                numbers = new ArrayList<Integer>();
                mathOperator = ' ';

            }
        }

        long answer = mathResults.stream().filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        return answer;
    }

}
