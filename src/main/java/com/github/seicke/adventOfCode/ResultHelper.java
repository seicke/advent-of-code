package com.github.seicke.adventOfCode;

public class ResultHelper {

	public static String suffix(int actual, int expected) {

		if (actual == expected) {
			return "✓";
		}

		return String.format("(%s, should be %s)", (actual > expected ? "too high" : "too low"), expected);

	}

	public static String suffix(long actual, long expected) {

		if (actual == expected) {
			return "✓";
		}

		return String.format("(%s, should be %s)", (actual > expected ? "too high" : "too low"), expected);

	}

}
