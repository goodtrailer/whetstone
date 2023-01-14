package goodtrailer.whetstone.framework.math.function;

import java.util.List;

import goodtrailer.whetstone.framework.math.AbstractFunction;
import goodtrailer.whetstone.framework.math.IMathConstants;
import goodtrailer.whetstone.framework.math.Interval;
import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.Point;
import goodtrailer.whetstone.framework.math.Solution;
import goodtrailer.whetstone.framework.math.SolutionType;

// y = mx + b
public class Linear extends AbstractFunction
{
    public static final int DEFAULT_MAX_M = 12;
    public static final int DEFAULT_MAX_B = 16;

    private final double m, b;

    public Linear(double m, double b)
    {
        this.m = m;
        this.b = b;
    }

    public double m()
    { return m; }

    public double b()
    { return b; }

    public Linear withM(double m)
    { return new Linear(m, b); }

    public Linear withB(double b)
    { return new Linear(m, b); }

    // ------------------------------------------------------------------------------------- customs

    public double distance(Linear other)
    {
        var soln = solution(other);

        return switch (soln.type())
        {
        case DNE -> Math.abs(b - other.b) / Math.sqrt(m * other.m + 1);
        default -> 0;
        };
    }

    public Solution solution(Linear other)
    { return solution(other, IMathConstants.DEFAULT_PLACES); }
    
    public Solution solution(Linear other, int places)
    {
        double x = ((double) other.b - b) / (m - other.m);
        var point = new Point(x, evaluate(x));
        var type = SolutionType.EXISTS;

        if (IMathUtils.equals(m, other.m, places))
        {
            boolean same = IMathUtils.equals(b, other.b, places);
            type = same ? SolutionType.TRUE : SolutionType.DNE;
        }

        return new Solution(type, point);
    }
    
    public Linear plus(Linear other)
    { return new Linear(m + other.m, b + other.b); }
    
    public Linear minus(Linear other)
    { return new Linear(m - other.m, b - other.b); }
    
    public Linear times(double scalar)
    { return new Linear(scalar * m, scalar * b); }

    // ----------------------------------------------------------------------------------- overrides

    @Override
    public double evaluate(double input)
    { return m * input + b; }

    @Override
    public boolean isConstant(int places)
    { return IMathUtils.equals(m, 0, places); }

    @Override
    public boolean isZero(int places)
    { return IMathUtils.equals(m, 0, places) && IMathUtils.equals(b, 0, places); }

    @Override
    public List<Interval> domain(int places)
    { return List.of(Interval.real()); }

    @Override
    public List<Interval> range(int places)
    { return List.of(Interval.real()); }

    @Override
    public String toString(String variable, int places)
    {
        if (isZero())
            return "0";
        
        String coef = IMathUtils.toString(m, places);
        if (coef.equals("1"))
            coef = "";
        else if (coef.equals("-1"))
            coef = "-";
        
        String operator = b > 0 ? "+" : "\u2013";
        String constant = IMathUtils.toString(Math.abs(b), places);
        
        if (coef.equals("0"))
        {
            coef = "";
            variable = "";
            operator = "";
            
            if (b < 0)
                constant = "-" + constant;
        }
        
        if (constant.equals("0"))
        {
            constant = "";
            operator = "";
        }
        
        return String.format("%s%s%s%s", coef, variable, operator, constant);
    }

    // ------------------------------------------------------------------------------------- statics

    public static Linear random(int maxM, int maxB)
    {
        int m = IMathUtils.randomInt(maxM);
        int b = IMathUtils.randomInt(maxB);
        return new Linear(m, b);
    }

    public static Linear random()
    { return random(DEFAULT_MAX_M, DEFAULT_MAX_B); }
    
    public static Linear constant(double constant)
    { return new Linear(0, constant); }
}
