package goodtrailer.whetstone.framework.math;

public enum SolutionType
{
    DNE,
    TRUE,
    EXISTS;
    
    public static SolutionType valueOf(String string, SolutionType fallback)
    {
        try
        {
            return valueOf(string);
        }
        catch (IllegalArgumentException iae)
        {
            return fallback;
        }
    }
}
