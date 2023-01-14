package goodtrailer.whetstone.framework.math;

import goodtrailer.whetstone.framework.problem.Result;

public record Point(double... components)
{
    public static final int DEFAULT_MAX_COMPONENT_VALUE = 12;

    public Point(double... components)
    { this.components = components; }

    public Result equals(Point other, int places)
    {
        if (other == null || components.length != other.components.length)
            return Result.INVALID;

        for (int i = 0; i < components.length; i++)
            if (!IMathUtils.equals(other.components[i], components[i], places))
                return Result.INCORRECT;

        return Result.CORRECT;
    }

    public Result equals(Point other)
    { return equals(other, IMathConstants.DEFAULT_PLACES); }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Point pOther))
            return false;

        return equals(pOther).toBoolean();
    }
    
    public Result tryParseEquals(String other)
    { return tryParseEquals(other, IMathConstants.DEFAULT_PLACES); }
    
    public Result tryParseEquals(String other, int places)
    {
        try
        {
            return equals(parse(other), places);
        }
        catch (NumberFormatException nfe)
        {
            return Result.INVALID;
        }
    }

    public int dimensions()
    { return components.length; }

    public double distance(Point other)
    {
        if (components.length != other.components.length)
            throw new IllegalArgumentException("different dimensions");

        double sum = 0.0;
        for (int i = 0; i < components.length; i++)
        {
            double diff = components[i] - other.components[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    public boolean is2d()
    { return components.length == 2; }

    public String toString(int places)
    {
        if (components.length == 0)
            return "( )";

        String string = "(";
        for (int i = 0; i < components.length - 1; i++)
        {
            string += IMathUtils.toString(components[i], places) + ", ";
        }
        string += IMathUtils.toString(components[components.length - 1], places);
        return string + ")";
    }

    @Override
    public String toString()
    { return toString(IMathConstants.DEFAULT_PLACES); }

    public double x()
    {
        if (components.length < 1)
            throw new IllegalStateException("too few dimensions");

        return components[0];
    }

    public double y()
    {
        if (components.length < 2)
            throw new IllegalStateException("too few dimensions");

        return components[1];
    }

    public static Point random(int dimensions, int maxComponentValue)
    {
        if (dimensions < 0)
            throw new IllegalArgumentException("negative dimensions");

        double[] components = new double[dimensions];
        for (int i = 0; i < components.length; i++)
        {
            components[i] = IMathUtils.randomInt(maxComponentValue);
        }
        return new Point(components);
    }

    public static Point random(int dimensions)
    { return random(dimensions, DEFAULT_MAX_COMPONENT_VALUE); }

    public static Point parse(String string)
    {
        string = string.trim();

        if (string.isEmpty())
            throw new NumberFormatException("blank/empty string");

        if (string.charAt(0) != '(' || string.charAt(string.length() - 1) != ')')
            throw new NumberFormatException("invalid opening/closing parentheses");

        var components = string.substring(1, string.length() - 1).split(",");
        var point = new double[components.length];
        for (var i = 0; i < components.length; i++)
            point[i] = IMathUtils.parseFraction(components[i]);

        return new Point(point);
    }

    public static Point zero(int dimensions)
    { return new Point(new double[dimensions]); }
}
