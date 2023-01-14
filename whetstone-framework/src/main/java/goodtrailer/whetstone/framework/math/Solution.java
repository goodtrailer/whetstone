package goodtrailer.whetstone.framework.math;

import goodtrailer.whetstone.framework.problem.Result;

public record Solution(SolutionType type, Point point)
{
    public Result equals(Solution other)
    { return equals(other, IMathConstants.DEFAULT_PLACES); }
    
    public Result equals(Solution other, int places)
    {
        if (other == null)
            return Result.INVALID;
        
        if (type != other.type)
            return Result.INCORRECT;
        
        return switch (type)
        {
        case DNE -> Result.CORRECT;
        case TRUE -> Result.CORRECT;
        case EXISTS -> point.equals(other.point, places);
        };
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
    
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Solution sOther))
            return false;
        
        return equals(sOther).toBoolean();
    }
    
    public static Solution parse(String string)
    {
        string = string.trim();
        SolutionType type = SolutionType.valueOf(string, SolutionType.EXISTS);
        
        return new Solution(type, type == SolutionType.EXISTS ? Point.parse(string) : null);
    }
}
