package goodtrailer.whetstone.framework.math;

import java.util.concurrent.ThreadLocalRandom;

import goodtrailer.whetstone.framework.problem.Result;
import goodtrailer.whetstone.framework.util.IBooleanUtils;

import java.text.DecimalFormat;

public interface IMathUtils
{
    static double parseFraction(String string) throws NumberFormatException
    {
        string = string.trim();

        if (string.isEmpty())
            throw new NumberFormatException("empty string");

        if (string.charAt(0) == '/')
            throw new NumberFormatException("starts with fraction bar");

        var subs = string.split("/");
        switch (subs.length)
        {
        case 0:
            throw new NumberFormatException("empty string");
        case 1:
            return Double.parseDouble(subs[0]);
        case 2:
            return Double.parseDouble(subs[0]) / Double.parseDouble(subs[1]);
        default:
            throw new NumberFormatException("multiple fraction bars");
        }
    }
    
    static Result tryParseFractionEquals(double a, String b)
    { return tryParseFractionEquals(a, b, IMathConstants.DEFAULT_PLACES); }
    
    static Result tryParseFractionEquals(double a, String b, int places)
    {
        try
        {
            return Result.from(equals(a, parseFraction(b), places));
        }
        catch (NumberFormatException nfe)
        {
            return Result.INVALID;
        }
    }

    static double parsePercentage(String string) throws NumberFormatException
    {
        string = string.trim();

        if (string.isEmpty())
            throw new NumberFormatException("empty string");

        if (string.charAt(0) == '%')
            throw new NumberFormatException("starts with percentage symbol '%'");

        if (string.charAt(string.length() - 1) != '%')
            throw new NumberFormatException("does not end with percentage symbol '%'");

        return Double.parseDouble(string.substring(0, string.length() - 1)) / 100.;
    }
    
    static Result tryParsePercentageEquals(double a, String b)
    { return tryParsePercentageEquals(a, b, IMathConstants.DEFAULT_PLACES); }
    
    static Result tryParsePercentageEquals(double a, String b, int places)
    {
        try
        {
            return Result.from(equals(a, parsePercentage(b), places));
        }
        catch (NumberFormatException nfe)
        {
            return Result.INVALID;
        }
    }

    static boolean equals(double a, double b, double epsilon)
    {
        if (Double.isNaN(a))
            return false;

        if (!Double.isFinite(a))
        {
            if (Double.isInfinite(a) && Double.isInfinite(b))
                return (a < 0) == (b < 0);
            else
                return a == b;
        }

        return Math.abs(a - b) <= epsilon;
    }

    static boolean equals(double a, double b, int places, double leeway)
    { return equals(a, b, Math.pow(10.0, -places) / (2.0 - leeway)); }

    static boolean equals(double a, double b, int places)
    { return equals(a, b, places, IMathConstants.DEFAULT_LEEWAY); }

    static boolean equals(double a, double b)
    { return equals(a, b, IMathConstants.DEFAULT_PLACES); }

    static String toString(double a, int places)
    {
        if (places < 0)
            throw new IllegalArgumentException("negative places");

        String format = "0";
        if (places > 0)
            format += "." + "#".repeat(places);

        return new DecimalFormat(format).format(a);
    }

    static String toString(double a)
    { return toString(a, IMathConstants.DEFAULT_PLACES); }

    static int randomInt(int inclusiveMax)
    { return ThreadLocalRandom.current().nextInt(-inclusiveMax, inclusiveMax + 1); }

    static int randomInt(int inclusiveMin, int inclusiveMax)
    { return ThreadLocalRandom.current().nextInt(inclusiveMin, inclusiveMax + 1); }

    static double randomDouble(double max)
    { return ThreadLocalRandom.current().nextDouble(-max, max); }

    static double randomDouble(double min, double max)
    { return ThreadLocalRandom.current().nextDouble(min, max); }
    
    static int randomSign()
    { return IBooleanUtils.random() ? 1 : -1; }
}
