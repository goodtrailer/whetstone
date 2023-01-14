package goodtrailer.whetstone.framework.math;

import java.util.List;

public abstract class AbstractFunction implements IFunction
{
    public final boolean isConstant()
    { return isConstant(IMathConstants.DEFAULT_PLACES); }

    public final boolean isZero()
    { return isZero(IMathConstants.DEFAULT_PLACES); }

    public final List<Interval> domain()
    { return domain(IMathConstants.DEFAULT_PLACES); }

    public final List<Interval> range()
    { return range(IMathConstants.DEFAULT_PLACES); }

    public final String toString(String variable)
    { return toString(variable, IMathConstants.DEFAULT_PLACES); }

    public final String toString(int places)
    { return toString(IMathConstants.DEFAULT_VARIABLE, places); }

    @Override
    public final String toString()
    { return toString(IMathConstants.DEFAULT_VARIABLE, IMathConstants.DEFAULT_PLACES); }
}
