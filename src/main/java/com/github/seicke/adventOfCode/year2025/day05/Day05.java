package com.github.seicke.adventOfCode.year2025.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day05 {

    private static final Logger log = LoggerFactory.getLogger(Day05.class);

    private static final int year = 2025;
    private static final int day = 5;

    private static final int TEST_ANSWER_PART1 = 3;
    private static final long TEST_ANSWER_PART2 = 14L;

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        List<String> lines = DataLoader.getStringLines(year, day, true);

        int answer_part1 = numberOfAvailableFreshIds(lines);
        long answer_part2 = numberOfFreshIds(lines);

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        List<String> lines = DataLoader.getStringLines(year, day, false);

        int answer_part1 = numberOfAvailableFreshIds(lines);
        long answer_part2 = numberOfFreshIds(lines);

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); // 773

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); // 332067203034711

    }

    public static int numberOfAvailableFreshIds(List<String> lines) {

        int answer = 0;

        List<FreshIdRange> freshIdRanges = new ArrayList<FreshIdRange>();

        lines: for (String line : lines) {

            line = line.trim();

            if (line.isEmpty())
                continue;

            if (line.contains("-")) {

                Long[] range = Arrays.stream(line.split("-"))
                        .map(Long::valueOf)
                        .toArray(Long[]::new);

                freshIdRanges.add(new FreshIdRange(range[0], range[1]));

            } else {

                for (FreshIdRange freshIdRange : freshIdRanges) {

                    if (freshIdRange.contains(Long.valueOf(line))) {

                        answer++;
                        continue lines;

                    }

                }

            }

        }

        return answer;

    }

    public static long numberOfFreshIds(List<String> lines) {

        long answer = 0;

        List<FreshIdRange> freshIdRanges = new ArrayList<FreshIdRange>();

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty())
                continue;

            if (line.contains("-")) {

                Long[] range = Arrays.stream(line.split("-"))
                        .map(Long::valueOf)
                        .toArray(Long[]::new);

                freshIdRanges.add(new FreshIdRange(range[0], range[1]));

            }

        }

        boolean change = true;
        // Check id range overlaps and combine ranges
        while (change) {
            change = false;

            Iterator<FreshIdRange> it = freshIdRanges.iterator();

            while (it.hasNext()) {

                FreshIdRange testRange = it.next();
                boolean overlapFound = false;

                for (FreshIdRange fr : freshIdRanges) {

                    if (fr.overlap(testRange)) {

                        overlapFound = true;
                        change = true;

                    }

                }

                if (overlapFound)
                    it.remove();

            }
        }

        for (FreshIdRange freshRange : freshIdRanges)
            answer += freshRange.getSize();

        return answer;

    }

}
