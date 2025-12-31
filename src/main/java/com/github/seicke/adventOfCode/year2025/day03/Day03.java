package com.github.seicke.adventOfCode.year2025.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day03 {

        private static final Logger log = LoggerFactory.getLogger(Day03.class);

        private static final int year = 2025;
        private static final int day = 3;

        private static final int TEST_ANSWER_PART1 = 357;
        private static final long TEST_ANSWER_PART2 = 3121910778619L;

        public static void main(String[] args) {

                test();

                System.out.println();

                riddle();

        }

        private static void test() {

                List<String> batteryBanks = DataLoader.getStringLines(year, day, true);

                List<Long> joltages_part1 = determineMaxJoltages(batteryBanks, 2);
                List<Long> joltages_part2 = determineMaxJoltages(batteryBanks, 12);

                long answer_part1 = joltages_part1.stream().filter(Objects::nonNull)
                                .mapToLong(Long::longValue).sum();

                long answer_part2 = joltages_part2.stream().filter(Objects::nonNull)
                                .mapToLong(Long::longValue).sum();

                System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

                System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2))); 
        }

        private static void riddle() {

                List<String> batteryBanks = DataLoader.getStringLines(year, day, false);

                List<Long> joltages_part1 = determineMaxJoltages(batteryBanks, 2);
                List<Long> joltages_part2 = determineMaxJoltages(batteryBanks, 12);

                long answer_part1 = joltages_part1.stream().filter(Objects::nonNull)
                                .mapToLong(Long::longValue).sum();

                long answer_part2 = joltages_part2.stream().filter(Objects::nonNull)
                                .mapToLong(Long::longValue).sum();

                System.out.println(String.format("Riddle Part1: Answer '%s'",
                                answer_part1)); // 17430

                System.out.println(String.format("Riddle Part2: Answer '%s'",
                                answer_part2)); // 171975854269367
        }

        private static List<Long> determineMaxJoltages(List<String> batteryBanks, int joltageDigits) {

                List<Long> joltages = new ArrayList<Long>();

                for (String batteryBank : batteryBanks) {

                        List<Integer> joltage = new ArrayList<Integer>();

                        int indexStart = 0;
                        for (int digitsLeft = joltageDigits; digitsLeft > 0; digitsLeft--) {

                                int joltagePart = findMaxNumber(
                                                batteryBank.substring(indexStart,
                                                                batteryBank.length() - (digitsLeft - 1)));

                                joltage.add(joltagePart);

                                indexStart = indexStart
                                                + batteryBank.substring(indexStart,
                                                                batteryBank.length() - (digitsLeft - 1))
                                                                .indexOf(String
                                                                                .valueOf(joltagePart))
                                                + 1;

                        }

                        joltages.add(Long.parseLong(joltage.stream()
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(""))));

                }

                return joltages;

        }

        private static int findMaxNumber(String string) {

                int maxInt = 0;

                for (int i = 0, len = string.length(); i < len; i++) {

                        int digit = Character.getNumericValue(string.charAt(i));

                        if (digit > maxInt) {

                                maxInt = digit;

                        }

                }

                return maxInt;

        }

}
