package com.sollyw.biginv;

/**
 * A fixed offset for ints.
 * If isAbsolute is true, the offset is simply returned.
 * Otherwise, the offset is added to the input.
 */
public record IntOffset(
        boolean isAbsolute,
        int offset
) {
    public static final IntOffset NONE = new IntOffset(false, 0);
    public static final IntOffset NEGATIVE_ONE = new IntOffset(false, -1);

    /**
     * Offset the given int by the offset stored in this instance
     * @param value the initial value
     * @return the modified value
     */
    public int applyTo(int value) {
        return isAbsolute ? offset : value + offset;
    }

    /**
     * Offset the given float by the offset stored in this instance
     * @param value the initial value
     * @return the modified value
     */
    @SuppressWarnings("unused")
    public float applyTo(float value) {
        return isAbsolute ? offset : value + offset;
    }
}
