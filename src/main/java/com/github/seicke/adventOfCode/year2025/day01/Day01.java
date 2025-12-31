package com.github.seicke.adventOfCode.year2025.day01;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day01 {

	private static final Logger log = LoggerFactory.getLogger(Day01.class);

	private static final int year = 2025;
	private static final int day = 1;

	private static final int TEST_ANSWER_PART1 = 3;
	private static final int TEST_ANSWER_PART2 = 6;

	private static final int DIAL_NUMBERS = 100;
	private static final int DIAL_START_POSITION = 50;

	public static void main(String[] args) {

		test();

		System.out.println();

		riddle();

	}

	private static void test() {

		List<String> rotations = DataLoader.getStringLines(year, day, true);

		int[] rotationResult = runRotations(rotations);

		System.out.println(String.format("Test Case Part1: Answer '%s' %s", rotationResult[0],
				ResultHelper.suffix(rotationResult[0], TEST_ANSWER_PART1)));

		System.out.println(String.format("Test Case Part2: Answer '%s' %s", rotationResult[1],
				ResultHelper.suffix(rotationResult[1], TEST_ANSWER_PART2)));
	}

	private static void riddle() {

		List<String> rotations = DataLoader.getStringLines(year, day, false);

		int[] rotationResult = runRotations(rotations);

		System.out.println(String.format("Riddle Part1: Answer '%s'", rotationResult[0])); // 1120

		System.out.println(String.format("Riddle Part2: Answer '%s'", rotationResult[1])); // 6554
	}

	private static int[] runRotations(List<String> rotations) {

		int dialPosition = DIAL_START_POSITION;

		int part1 = 0;
		int part2 = 0;
		for (String rotation : rotations) {
			int[] rotationResult = rotate(dialPosition, rotation);
			dialPosition = rotationResult[0];
			part1 += rotationResult[1];
			part2 += rotationResult[2];
		}

		return new int[] { part1, part2 };

	}

	private static int[] rotate(int initialPosition, String rotation) {

		rotation = rotation.trim();

		char direction = rotation.charAt(0);
		int rotationCount = Integer.parseInt(rotation.substring(1));

		int cycles = (rotationCount / DIAL_NUMBERS); // ok
		int rotationRemain = rotationCount % DIAL_NUMBERS; // ok

		int landedOnZero = 0;
		int zeroPasses = cycles;

		int newPosition = initialPosition;
		switch (Character.toUpperCase(direction)) {
			// Rotation left
			case 'L':

				newPosition = initialPosition - rotationRemain;

				if (newPosition < 0) {
					newPosition += DIAL_NUMBERS;
					if (initialPosition > 0) {
						zeroPasses++;
					}
				}

				if (newPosition == 0) {
					zeroPasses++;
				}

				break;

			// Rotation right
			case 'R':

				newPosition = initialPosition + rotationRemain;

				if (newPosition > DIAL_NUMBERS) {
					newPosition -= DIAL_NUMBERS;
					if (initialPosition > 0) {
						zeroPasses++;
					}
				}

				if (newPosition == DIAL_NUMBERS) {
					newPosition = 0;
					zeroPasses++;
				}

				break;

			default:
				log.warn("Invalid rotation direction: " + direction);
				break;

		}

		landedOnZero = (newPosition == 0) ? 1 : 0;

		return new int[] { newPosition, landedOnZero, zeroPasses };
	}

}
