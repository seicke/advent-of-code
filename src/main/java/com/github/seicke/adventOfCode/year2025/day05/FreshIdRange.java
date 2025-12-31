package com.github.seicke.adventOfCode.year2025.day05;

public class FreshIdRange {
    private Long start;
    private Long end;
    private final int id;
    private static int idCount = 0;

    public FreshIdRange(Long start, Long end) {

        this.start = start;
        this.end = end;
        this.id = idCount++;

    }

    public boolean contains(Long id) {

        return id >= start && id <= end;

    }

    public boolean overlap(FreshIdRange testRange) {

        if (testRange.id == this.id)
            return false;

        if (contains(testRange.start) || contains(testRange.end)) {

            if (testRange.start < this.start)
                this.start = testRange.start;
            if (testRange.end > this.end)
                this.end = testRange.end;
            return true;

        }

        return false;
    }

    public Long getSize() {

        return end - start + 1;

    }

    public String toString() {

        return id + ":" + start + "-" + end;

    }

}
