package game.utils;

public class Range<T extends Comparable<T>>
{
    public Range( T min, T max ) {
        this.min = min;
        this.max = max;
    }

    public boolean within( T value ) {
        return min.compareTo(value) <= 0 && max.compareTo(value) >= 0;
    }
    
    private final T min;
    private final T max;
}