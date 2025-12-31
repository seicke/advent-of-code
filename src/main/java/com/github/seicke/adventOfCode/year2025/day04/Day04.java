package com.github.seicke.adventOfCode.year2025.day04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day04 {

    private static final Logger log = LoggerFactory.getLogger(Day04.class);

    private static final int year = 2025;
    private static final int day = 4;

    private static final int TEST_ANSWER_PART1 = 13;
    private static final int TEST_ANSWER_PART2 = 43;

    private static final char PAPER_ROLL_CHAR = '@';
    private static final char EMPTY_CHAR = '.';

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        char[][] paperRollsGrid = DataLoader.getCharArray(year, day, true);

        int answer_part1 = numberOfMoveableRolls(paperRollsGrid);
        int answer_part2 = numberOfRemovableRolls(paperRollsGrid);

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        char[][] paperRollsGrid = DataLoader.getCharArray(year, day, false);

        int answer_part1 = numberOfMoveableRolls(paperRollsGrid);
        int answer_part2 = numberOfRemovableRolls(paperRollsGrid);

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); // 1395

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); // 8451

    }

    private static int numberOfMoveableRolls(char[][] paperRollsGrid) {

        int answer = 0;

        int paperRollsGridRowsCount = paperRollsGrid.length;
        int paperRollsGridColsCount = paperRollsGrid[0].length;

        for (int row = 0; row < paperRollsGridRowsCount; row++) {
            for (int col = 0; col < paperRollsGridColsCount; col++) {
                answer += isMovable(paperRollsGrid, row, col) ? 1
                        : 0;
            }
        }

        return answer;

    }

    private static int numberOfRemovableRolls(char[][] paperRollsGrid) {

        int answer = 0;

        int paperRollsGridRowsCount = paperRollsGrid.length;
        int paperRollsGridColsCount = paperRollsGrid[0].length;

        boolean rollWasRemoved = true;
        while (rollWasRemoved) {
            rollWasRemoved = false;

            for (int row = 0; row < paperRollsGridRowsCount; row++) {
                for (int col = 0; col < paperRollsGridColsCount; col++) {
                    if (isMovable(paperRollsGrid, row, col)) {

                        // Remove paper roll
                        paperRollsGrid[row][col] = EMPTY_CHAR;
                        rollWasRemoved = true;

                        answer++;

                    }
                }
            }

        }

        return answer;

    }

    private static boolean isMovable(char[][] paperRollsGrid, int row, int col) {

        final int MOVEABLE_COUNTER = 4;

        if (paperRollsGrid[row][col] != PAPER_ROLL_CHAR) {
            return false;
        }

        int counter = 0;
        int paperRollsGridRowsCount = paperRollsGrid.length;
        int paperRollsGridColsCount = paperRollsGrid[0].length;

        // up left
        if (row - 1 >= 0 && col - 1 >= 0 && paperRollsGrid[row - 1][col - 1] == PAPER_ROLL_CHAR)
            counter++;

        // up
        if (row - 1 >= 0 && paperRollsGrid[row - 1][col] == PAPER_ROLL_CHAR)
            counter++;

        // up right
        if (row - 1 >= 0 && col + 1 < paperRollsGridColsCount && paperRollsGrid[row - 1][col + 1] == PAPER_ROLL_CHAR)
            counter++;

        // right
        if (col + 1 < paperRollsGridColsCount && paperRollsGrid[row][col + 1] == PAPER_ROLL_CHAR)
            counter++;

        // down right
        if (row + 1 < paperRollsGridRowsCount && col + 1 < paperRollsGridColsCount
                && paperRollsGrid[row + 1][col + 1] == PAPER_ROLL_CHAR)
            counter++;

        // down
        if (row + 1 < paperRollsGridRowsCount && paperRollsGrid[row + 1][col] == PAPER_ROLL_CHAR)
            counter++;

        // down left
        if (row + 1 < paperRollsGridRowsCount && col - 1 >= 0 && paperRollsGrid[row + 1][col - 1] == PAPER_ROLL_CHAR)
            counter++;

        // left
        if (col - 1 >= 0 && paperRollsGrid[row][col - 1] == PAPER_ROLL_CHAR)
            counter++;

        return counter < MOVEABLE_COUNTER;
    }

}
