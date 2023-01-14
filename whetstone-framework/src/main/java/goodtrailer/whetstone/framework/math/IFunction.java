package goodtrailer.whetstone.framework.math;

import java.util.List;

public interface IFunction
{
    double evaluate(double input);

    boolean isConstant(int places);

    boolean isConstant();

    boolean isZero(int places);

    boolean isZero();

    List<Interval> domain(int places);

    List<Interval> domain();

    List<Interval> range(int places);

    List<Interval> range();

    String toString(String variable, int places);

    String toString(String variable);

    String toString(int places);

    public static IFunction zero()
    {
        return new AbstractFunction()
        {

            @Override
            public double evaluate(double input)
            { return 0; }

            @Override
            public boolean isConstant(int places)
            { return true; }

            @Override
            public boolean isZero(int places)
            { return true; }

            @Override
            public List<Interval> domain(int places)
            { return List.of(Interval.real()); }

            @Override
            public List<Interval> range(int places)
            { return List.of(Interval.point(0)); }

            @Override
            public String toString(String variable, int places)
            { return "0"; }
        };
    }
}
