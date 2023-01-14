package goodtrailer.whetstone.framework.math.function;

public enum ExponentialGrowthType
{
    GROWTH,
    DECAY,
    NEITHER;

    @Override
    public String toString()
    {
        var string = super.toString();
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
