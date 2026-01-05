package com.github.seicke.adventOfCode.year2025.day09;

import java.util.Objects;

public class Tile {

    private final int x;
    private final int y;

    public Tile(int x, int y) {

        this.x = x;
        this.y = y;

    }

    public Tile(int[] array) {

        if (array.length == 2) {

            this.x = array[0];
            this.y = array[1];

        } else {

            this.x = -1;
            this.y = -1;

        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return Objects.hash(x, y);
    }

    public long areaWith(Tile other) {

        int dx = this.x - other.x;
        int dy = this.y - other.y;

        return ((long) Math.abs(dx + 1) * (long) Math.abs(dy + 1));

    }

    public String toString() {
        return String.format("%6s: %5d | %5d", getId(), x, y);
    }

}
