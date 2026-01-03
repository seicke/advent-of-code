package com.github.seicke.adventOfCode.year2025.day08;

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

public class Day08 {

    private static final Logger log = LoggerFactory.getLogger(Day08.class);

    private static final int year = 2025;
    private static final int day = 8;

    private static final int TEST_ANSWER_PART1 = 40;
    private static final long TEST_ANSWER_PART2 = 25272;

    public static void main(String[] args) {

        puzzle_test();

        System.out.println();

        puzzle();

    }

    private static void puzzle_test() {

        List<String> junctionBoxes = DataLoader.getStringLines(year, day, true);

        int answer_part1 = makingConnections(junctionBoxes, 10, 3);
        long answer_part2 = makeOneLargeCircuit(junctionBoxes);

        System.out.println(String.format("Test Case Part1: Answer '%s' %s", answer_part1,
                ResultHelper.suffix(answer_part1, TEST_ANSWER_PART1)));

        System.out.println(String.format("Test Case Part2: Answer '%s' %s", answer_part2,
                ResultHelper.suffix(answer_part2, TEST_ANSWER_PART2)));

    }

    private static void puzzle() {

        List<String> junctionBoxes = DataLoader.getStringLines(year, day, false);

        int answer_part1 = makingConnections(junctionBoxes, 1000, 3);
        long answer_part2 = makeOneLargeCircuit(junctionBoxes);

        System.out.println(String.format("Riddle Part1: Answer '%s'",
                answer_part1)); // 153328

        System.out.println(String.format("Riddle Part2: Answer '%s'",
                answer_part2)); // 6095621910

    }

    private static int makingConnections(List<String> junctionBoxesStrings, int numberOfConnections,
            int numberOfLargestCircuits) {

        List<JunctionBox> junctionBoxes = getJunctionBoxes(junctionBoxesStrings);

        Map<Double, List<JunctionBox[]>> distancesMap = getDistancesMap(junctionBoxes);
        List<Double> distancesSorted = distancesMap.keySet().stream()
                .sorted(Comparator.comparingDouble(distance -> distance))
                .collect(Collectors.toList());

        Map<Integer, List<JunctionBox>> circuits = new HashMap<Integer, List<JunctionBox>>();

        int circuitCount = 1;
        for (int numberOfConnection = 1; numberOfConnection <= numberOfConnections; numberOfConnection++) {

            double distance = distancesSorted.get(numberOfConnection - 1);

            List<JunctionBox[]> junctionBoxesForDistance = distancesMap.get(distance);

            for (int i = 0; i < junctionBoxesForDistance.size(); i++) {

                JunctionBox[] junctionBoxesForDistanceArray = junctionBoxesForDistance.get(i);

                JunctionBox junctionBoxes1 = junctionBoxesForDistanceArray[0];
                JunctionBox junctionBoxes2 = junctionBoxesForDistanceArray[1];

                if (junctionBoxes1.getCircuit() == -1 &&
                        junctionBoxes2.getCircuit() == -1) {

                    junctionBoxes1.setCircuit(circuitCount);
                    junctionBoxes2.setCircuit(circuitCount);

                    List<JunctionBox> junctionBoxesForDistanceList = new ArrayList<>();
                    junctionBoxesForDistanceList.add(junctionBoxes1);
                    junctionBoxesForDistanceList.add(junctionBoxes2);

                    circuits.put(circuitCount, junctionBoxesForDistanceList);

                    circuitCount++;

                } else if (junctionBoxes1.getCircuit() != -1
                        && junctionBoxes2.getCircuit() == -1) {

                    junctionBoxes2.setCircuit(junctionBoxes1.getCircuit());

                    circuits.get(junctionBoxes1.getCircuit()).add(junctionBoxes2);

                } else if (junctionBoxes1.getCircuit() == -1
                        && junctionBoxes2.getCircuit() != -1) {

                    junctionBoxes1.setCircuit(junctionBoxes2.getCircuit());

                    circuits.get(junctionBoxes2.getCircuit()).add(junctionBoxes1);

                } else if (junctionBoxes1.getCircuit() != -1 &&
                        junctionBoxes2.getCircuit() != -1 &&
                        junctionBoxes1.getCircuit() != junctionBoxes2.getCircuit()) {

                    int oldCircuit = junctionBoxes2.getCircuit();

                    for (JunctionBox jb : circuits.get(oldCircuit)) {

                        jb.setCircuit(junctionBoxes1.getCircuit());
                        circuits.get(junctionBoxes1.getCircuit()).add(jb);

                    }

                    circuits.remove(oldCircuit);

                }

            }

        }

        List<List<JunctionBox>> circuitsSorted = circuits.values().stream().collect(Collectors.toList());
        circuitsSorted.sort(Comparator.comparingInt(List<JunctionBox>::size).reversed());

        int answer = 1;

        for (int i = 0; i < numberOfLargestCircuits && i < circuitsSorted.size(); i++) {

            answer *= circuitsSorted.get(i).size();

        }

        return answer;

    }

    private static long makeOneLargeCircuit(List<String> junctionBoxesStrings) {

        List<JunctionBox> junctionBoxes = getJunctionBoxes(junctionBoxesStrings);

        Map<Double, List<JunctionBox[]>> distancesMap = getDistancesMap(junctionBoxes);

        List<Double> distancesSorted = distancesMap.keySet().stream()
                .sorted(Comparator.comparingDouble(distance -> distance))
                .collect(Collectors.toList());

        Map<Integer, List<JunctionBox>> circuits = new HashMap<Integer, List<JunctionBox>>();

        int circuitCount = 1;
        long answer = 0;
        for (int numberOfConnection = 1; numberOfConnection <= distancesSorted.size(); numberOfConnection++) {

            double distance = distancesSorted.get(numberOfConnection - 1);

            List<JunctionBox[]> junctionBoxesForDistance = distancesMap.get(distance);

            for (int i = 0; i < junctionBoxesForDistance.size(); i++) {

                JunctionBox[] junctionBoxesForDistanceArray = junctionBoxesForDistance.get(i);

                JunctionBox junctionBoxes1 = junctionBoxesForDistanceArray[0];
                JunctionBox junctionBoxes2 = junctionBoxesForDistanceArray[1];

                if (junctionBoxes1.getCircuit() == -1 &&
                        junctionBoxes2.getCircuit() == -1) {

                    junctionBoxes1.setCircuit(circuitCount);
                    junctionBoxes2.setCircuit(circuitCount);

                    List<JunctionBox> junctionBoxesForDistanceList = new ArrayList<>();
                    junctionBoxesForDistanceList.add(junctionBoxes1);
                    junctionBoxesForDistanceList.add(junctionBoxes2);

                    circuits.put(circuitCount, junctionBoxesForDistanceList);

                    circuitCount++;

                } else if (junctionBoxes1.getCircuit() != -1
                        && junctionBoxes2.getCircuit() == -1) {

                    junctionBoxes2.setCircuit(junctionBoxes1.getCircuit());

                    circuits.get(junctionBoxes1.getCircuit()).add(junctionBoxes2);

                } else if (junctionBoxes1.getCircuit() == -1
                        && junctionBoxes2.getCircuit() != -1) {

                    junctionBoxes1.setCircuit(junctionBoxes2.getCircuit());

                    circuits.get(junctionBoxes2.getCircuit()).add(junctionBoxes1);

                } else if (junctionBoxes1.getCircuit() != -1 &&
                        junctionBoxes2.getCircuit() != -1 &&
                        junctionBoxes1.getCircuit() != junctionBoxes2.getCircuit()) {

                    int oldCircuit = junctionBoxes2.getCircuit();

                    for (JunctionBox jb : circuits.get(oldCircuit)) {

                        jb.setCircuit(junctionBoxes1.getCircuit());
                        circuits.get(junctionBoxes1.getCircuit()).add(jb);

                    }

                    circuits.remove(oldCircuit);

                }
                if (circuits.size() == 1
                        && ((ArrayList) circuits.values().toArray()[0]).size() == junctionBoxes.size()) {

                    answer = (long) junctionBoxes1.getX() * (long) junctionBoxes2.getX();
                    return answer;

                }

            }

        }
        return answer;

    }

    private static List<JunctionBox> getJunctionBoxes(List<String> junctionBoxesStrings) {

        List<JunctionBox> junctionBoxes = new ArrayList<JunctionBox>();

        for (int i = 0; i < junctionBoxesStrings.size(); i++) {

            String junctionBoxString = junctionBoxesStrings.get(i);

            int[] junctionBoxCoordinates = Arrays.stream(junctionBoxString.split(",")).mapToInt(Integer::parseInt)
                    .toArray();

            JunctionBox junctionBox = new JunctionBox(junctionBoxCoordinates);

            junctionBoxes.add(junctionBox);

        }

        return junctionBoxes;
    }

    private static Map<Double, List<JunctionBox[]>> getDistancesMap(List<JunctionBox> junctionBoxes) {

        Map<Double, List<JunctionBox[]>> distancesMap = new HashMap<>();
        for (int i = 0; i < junctionBoxes.size(); i++) {

            for (int j = i + 1; j < junctionBoxes.size(); j++) {

                JunctionBox junctionBox1 = junctionBoxes.get(i);
                JunctionBox junctionBox2 = junctionBoxes.get(j);

                double distance = junctionBox1.distanceTo(junctionBox2);

                if (!distancesMap.containsKey(distance)) {
                    distancesMap.put(distance, new ArrayList<>());
                }

                distancesMap.get(distance).add(new JunctionBox[] { junctionBox1, junctionBox2 });

            }

        }

        return distancesMap;

    }

}
