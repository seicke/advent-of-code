package com.github.seicke.adventOfCode.year2025.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;
import com.github.seicke.adventOfCode.year2025.day08.Day08;
import com.github.seicke.adventOfCode.year2025.day08.JunctionBox;

public class Day09 {

    private static final Logger log = LoggerFactory.getLogger(Day09.class);

    private static final int year = 2025;
    private static final int day = 9;

    private static final long TEST_ANSWER_PART1 = 50;
    private static final long TEST_ANSWER_PART2 = 0;

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        List<String> tiles = DataLoader.getStringLines(year, day, true);

        long answer_part1 = largestTileArea(tiles);
        long answer_part2 = 0L;

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        List<String> tiles = DataLoader.getStringLines(year, day, false);

        long answer_part1 = largestTileArea(tiles);
        long answer_part2 = 0L;

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); //

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); //

    }

    private static long largestTileArea(List<String> tiles) {

        List<Tile> tileList = getTiles(tiles);

        Map<Long, List<Tile[]>> areasMap = getAreasMap(tileList);

        List<Long> areasSorted = areasMap.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return areasSorted.get(0);

    }

    private static List<Tile> getTiles(List<String> tileStrings) {

        List<Tile> tiles = new ArrayList<Tile>();

        for (int i = 0; i < tileStrings.size(); i++) {

            String tileString = tileStrings.get(i);

            int[] tileCoordinates = Arrays.stream(tileString.split(",")).mapToInt(Integer::parseInt)
                    .toArray();

            Tile tile = new Tile(tileCoordinates);

            tiles.add(tile);

        }

        return tiles;
    }

    private static Map<Long, List<Tile[]>> getAreasMap(List<Tile> tiles) {

        Map<Long, List<Tile[]>> areasMap = new HashMap<>();
        for (int i = 0; i < tiles.size(); i++) {

            for (int j = i + 1; j < tiles.size(); j++) {

                Tile tile1 = tiles.get(i);
                Tile tile2 = tiles.get(j);

                long area = tile1.areaWith(tile2);

                if (!areasMap.containsKey(area)) {
                    areasMap.put(area, new ArrayList<>());
                }

                areasMap.get(area).add(new Tile[] { tile1, tile2 });

            }

        }

        return areasMap;

    }

}
