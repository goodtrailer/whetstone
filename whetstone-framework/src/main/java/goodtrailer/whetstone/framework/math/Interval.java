package goodtrailer.whetstone.framework.math;

import goodtrailer.whetstone.framework.problem.Result;

public record Interval(double lower, double upper, boolean lInclusive, boolean uInclusive)
{

    public static final boolean DEFAULT_LOWER_INCLUSIVE = true;
    public static final boolean DEFAULT_UPPER_INCLUSIVE = false;

    public Interval(double lower, double upper)
    { this(lower, upper, DEFAULT_LOWER_INCLUSIVE, DEFAULT_UPPER_INCLUSIVE); }

    public Interval(double lower, double upper, boolean lInclusive, boolean uInclusive)
    {
        if (lower > upper)
            throw new IllegalArgumentException("lower greater than upper");

        this.lower = lower;
        this.upper = upper;
        this.lInclusive = lInclusive;
        this.uInclusive = uInclusive;
    }

    public Interval withLower(double lower)
    { return new Interval(lower, upper, lInclusive, uInclusive); }

    public Interval withUpper(double upper)
    { return new Interval(lower, upper, lInclusive, uInclusive); }

    public Interval withLInclusive(boolean lInclusive)
    { return new Interval(lower, upper, lInclusive, uInclusive); }

    public Interval withUInclusive(boolean uInclusive)
    { return new Interval(lower, upper, lInclusive, uInclusive); }

    public Result equals(Interval other, int places)
    {
        if (other == null)
            return Result.INVALID;
        
        return Result.from(
                IMathUtils.equals(other.lower, lower, places) 
                && IMathUtils.equals(other.upper, upper, places)
                && lInclusive == other.lInclusive
                && uInclusive == other.uInclusive);
    }

    public Result equals(Interval other)
    { return equals(other, IMathConstants.DEFAULT_PLACES); }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Interval iOther))
            return false;
        
        return equals(iOther).toBoolean();
    }
    
    public int contains(double n, int places)
    {
        if (!Double.isFinite(n))
            throw new IllegalArgumentException("non-finite n");

        if (n < lower)
            return -1;

        if (n > upper)
            return 1;

        if (!lInclusive && IMathUtils.equals(n, lower, places))
            return -1;

        if (!uInclusive && IMathUtils.equals(n, upper, places))
            return 1;

        return 0;
    }

    public String toString(int places)
    {
        return String.format("%s%s, %s%s",
                lInclusive ? "[" : "(",
                IMathUtils.toString(lower, places),
                IMathUtils.toString(upper, places),
                uInclusive ? "]" : ")");
    }

    @Override
    public String toString()
    { return toString(IMathConstants.DEFAULT_PLACES); }

    public static Interval real()
    { return real; }                    // valid because of immutability

    public static Interval point(double n)
    { return new Interval(n, n, true, true); }

    private static final Interval real = new Interval(Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY, false, false);
}
