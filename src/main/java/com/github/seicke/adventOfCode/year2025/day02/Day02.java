package com.github.seicke.adventOfCode.year2025.day02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seicke.adventOfCode.DataLoader;
import com.github.seicke.adventOfCode.ResultHelper;

public class Day02 {

	private static final Logger log = LoggerFactory.getLogger(Day02.class);

	private static final int year = 2025;
	private static final int day = 2;

	private static final long TEST_ANSWER_PART1 = 1227775554L;
	private static final long TEST_ANSWER_PART2 = 4174379265L;

	public static void main(String[] args) {

		test();

		System.out.println();

		riddle();

	}

	private static void test() {

		String[] idRanges = Arrays.stream(DataLoader.getString(year, day, true).split(",")).map(String::trim)
				.toArray(String[]::new);

		long[] result = determineInvalidIds(idRanges);

		System.out.println(String.format("Test Case Part1: Answer '%s' %s", result[0],
				ResultHelper.suffix(result[0], TEST_ANSWER_PART1)));

		System.out.println(String.format("Test Case Part2: Answer '%s' %s", result[1],
				ResultHelper.suffix(result[1], TEST_ANSWER_PART2)));
	}

	private static void riddle() {

		String[] idRanges = Arrays.stream(DataLoader.getString(year, day, false).split(",")).map(String::trim)
				.toArray(String[]::new);

		long[] result = determineInvalidIds(idRanges);

		System.out.println(String.format("Riddle Part1: Answer '%s'",
				result[0])); // 31839939622

		System.out.println(String.format("Riddle Part2: Answer '%s'",
				result[1])); // 41662374059
	}

	private static long[] determineInvalidIds(String[] idRanges) {

		Set<Long> invalidIds_part1 = new HashSet<Long>(); // Only adds distinct elements
		Set<Long> invalidIds_part2 = new HashSet<Long>(); // Only adds distinct elements

		for (String idRange : idRanges) {

			String[] ids = Arrays.stream(idRange.split("-")).map(String::trim).toArray(String[]::new);

			Long firstId = Long.parseLong(ids[0]);
			Long lastId = Long.parseLong(ids[1]);

			for (Long id = firstId; id <= lastId; id++) {

				if (idIsInvalid_part1(id)) {
					invalidIds_part1.add(id);
				}

				if (idIsInvalid_part2(id)) {
					invalidIds_part2.add(id);
				}
			}

		}

		Long answer_part1 = invalidIds_part1.stream().filter(Objects::nonNull)
				.mapToLong(Long::longValue).sum();

		Long answer_part2 = invalidIds_part2.stream().filter(Objects::nonNull)
				.mapToLong(Long::longValue).sum();

		return new long[] { answer_part1, answer_part2 };

	}

	private static boolean idIsInvalid_part1(Long id) {

		String idStr = String.valueOf(id);

		if (idStr.startsWith("0"))
			return true;

		// Only takes into account numbers that are repeated TWICE
		if (idStr.length() % 2 != 0)
			return false;

		String leftHalf = idStr.substring(0, idStr.length() / 2);
		String rightHalf = idStr.substring(idStr.length() / 2);

		return leftHalf.equals(rightHalf) ? true : false;

	}

	private static boolean idIsInvalid_part2(Long id) {

		String idStr = String.valueOf(id);

		if (idStr.startsWith("0"))
			return true;

		// Takes into account numbers that are repeated MULTIPLE times
		for (int i = 2; i <= idStr.length(); i++) {

			// Length of id must be a multiple of the number of numbers
			if (idStr.length() % i == 0) {

				String NumbersConcatenated = "";

				int numberLength = idStr.length() / i;
				String number = idStr.substring(0, numberLength);

				for (int j = 1; j <= idStr.length() / numberLength; j++) {
					NumbersConcatenated += number;
				}

				if (NumbersConcatenated.equals(idStr)) {
					return true;
				}

			}

		}

		return false;

	}

}
