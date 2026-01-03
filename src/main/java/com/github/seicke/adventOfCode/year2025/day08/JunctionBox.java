package com.github.seicke.adventOfCode.year2025.day08;

import java.util.Objects;

public class JunctionBox {

    private final int x;
    private final int y;
    private final int z;

    private int circuit = -1;

    public JunctionBox(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public JunctionBox(int[] array) {

        if (array.length == 3) {

            this.x = array[0];
            this.y = array[1];
            this.z = array[2];

        } else {

            this.x = -1;
            this.y = -1;
            this.z = -1;

        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getId() {
        return Objects.hash(x, y, z);
    }

    public int getCircuit() {
        return circuit;
    }

    public void setCircuit(int circuit) {
        this.circuit = circuit;
    }

    public double distanceTo(JunctionBox other) {

        int dx = this.x - other.x;
        int dy = this.y - other.y;
        int dz = this.z - other.z;

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));

    }

    public boolean equals(Object o) {

        if (!(o instanceof JunctionBox))
            return false;

        return x == ((JunctionBox) o).x && y == ((JunctionBox) o).y && z == ((JunctionBox) o).z;

    }

    public String toString() {
        return String.format("%6s: %5d | %5d | %5d", getId(), x, y, z);
    }

}
