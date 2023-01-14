package goodtrailer.whetstone.framework.math.function;

import java.util.List;

import goodtrailer.whetstone.framework.math.AbstractFunction;
import goodtrailer.whetstone.framework.math.Interval;
import goodtrailer.whetstone.framework.math.IMathConstants;
import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.Point;
import goodtrailer.whetstone.framework.math.Solution;
import goodtrailer.whetstone.framework.math.SolutionType;

// y = ab^(mx + c)
public class Exponential extends AbstractFunction
{
    private final double a, b, m, c;

    public Exponential(double a, double b, double m, double c)
    {
        if (b < 0)
            throw new IllegalArgumentException("negative base");

        this.a = a;
        this.b = b;
        this.m = m;
        this.c = c;
    }

    public double a()
    { return a; }

    public double b()
    { return b; }

    public double m()
    { return m; }

    public double c()
    { return c; }

    public Exponential withA(double a)
    { return new Exponential(a, b, m, c); }

    public Exponential withB(double b)
    { return new Exponential(a, b, m, c); }

    public Exponential withM(double m)
    { return new Exponential(a, b, m, c); }

    public Exponential withC(double c)
    { return new Exponential(a, b, m, c); }

    // ------------------------------------------------------------------------------------- customs

    public ExponentialGrowthType growthType(int places)
    {
        if (isConstant() || b < 0 || IMathUtils.equals(b, 0, places))
            return ExponentialGrowthType.NEITHER;

        boolean isGrowth = m > 0;
        if (b < 1)
            isGrowth = !isGrowth;

        return isGrowth ? ExponentialGrowthType.GROWTH : ExponentialGrowthType.DECAY;
    }

    public ExponentialGrowthType growthType()
    { return growthType(IMathConstants.DEFAULT_PLACES); }

    public Solution solution(Exponential other)
    { return solution(other, IMathConstants.DEFAULT_PLACES); }

    public Solution solution(Exponential other, int places)
    {
        double lnb0 = Math.log(b);
        double lnb1 = Math.log(other.b);
        double numer = Math.log(other.a / a) + other.c * lnb1 - c * lnb0;
        double denom = m * lnb0 - other.m * lnb1;
        double x = numer / denom;

        var type = Double.isFinite(x) ? SolutionType.EXISTS : SolutionType.DNE;
        var point = new Point(x, evaluate(x));

        boolean constants = isConstant(places) && other.isConstant(places)
                && IMathUtils.equals(evaluate(1), other.evaluate(1), places);
        boolean identical = IMathUtils.equals(a, other.a, places)
                && IMathUtils.equals(b, other.b, places)
                && IMathUtils.equals(m, other.m, places)
                && IMathUtils.equals(c, other.c, places);
        if (constants || identical)
            type = SolutionType.TRUE;

        return new Solution(type, point);
    }

    public Exponential times(double scalar)
    { return new Exponential(scalar * a, b, m, c); }

    // ----------------------------------------------------------------------------------- overrides

    @Override
    public double evaluate(double input)
    { return a * Math.pow(b, m * input + c); }

    @Override
    public boolean isConstant(int places)
    { return IMathUtils.equals(b, 1, places) || IMathUtils.equals(m, 0, places) || isZero(places); }

    @Override
    public boolean isZero(int places)
    { return IMathUtils.equals(a, 0, places) && !IMathUtils.equals(b, 0, places); }

    @Override
    public List<Interval> domain(int places)
    {
        if (IMathUtils.equals(b, 0, places))
        {
            var exponent = new Linear(m, c);

            if (exponent.isZero(places))
                return List.of();

            if (exponent.isConstant(places))
                return exponent.evaluate(0) > 0 ? List.of(Interval.real()) : List.of();

            var solution = exponent.solution(Linear.constant(0), places);

            switch (solution.type())
            {
            case DNE:
                return List.of();

            case TRUE:
                return List.of(Interval.real());

            case EXISTS:
                double bound = solution.point().x();
                return List.of(m < 0
                        ? Interval.real().withUpper(bound)
                        : Interval.real().withLower(bound));
            }

            return List.of(Interval.real());
        }

        return List.of(Interval.real());
    }

    @Override
    public List<Interval> range(int places)
    {
        if (IMathUtils.equals(b, 0, places))
        {
            var exponent = new Linear(m, c);
            if (exponent.isConstant())
            {
                if (exponent.evaluate(0) < 0 || exponent.isZero())
                    return List.of();
            }
            else
            {
                return List.of(Interval.point(0));
            }
        }

        if (isConstant())
            return List.of(Interval.point(evaluate(0)));

        if (a > 0)
            return List.of(Interval.real().withLower(0));
        else
            return List.of(Interval.real().withUpper(0));
    }

    @Override
    public String toString(String variable, int places)
    {
        if (isZero(places))
            return "0";

        String coef = IMathUtils.toString(a, places);

        var line = new Linear(m, c);
        if (line.isZero())
            return coef;

        String operator = " \u22C5 ";

        if (coef.equals("1"))
        {
            coef = "";
            operator = " ";
        }

        String base = String.format(b < 0 ? "(%s)" : "%s", IMathUtils.toString(b, places));

        return String.format("%s%s%s^(%s)", coef, operator, base, line.toString(variable, places));
    }

    // ------------------------------------------------------------------------------------- statics

    public static Exponential constant(double constant)
    { return new Exponential(constant, 1, 0, 0); }

    public static Exponential random()
    { return new Builder().build(); }

    public static Exponential random(int places)
    { return new Builder().build(places); }

    public static Builder builder()
    { return new Builder(); }

    public static class Builder
    {
        public static final int MIN_A = -10;
        public static final int MAX_A = 10;

        public static final int MIN_B = 0;
        public static final int MAX_B = 8;

        public static final int MIN_M = -4;
        public static final int MAX_M = 4;

        public static final int MIN_C = -3;
        public static final int MAX_C = 3;

        public static final double ZERO_CHANCE = 0.08;

        public static final double NONZERO_CONSTANT_CHANCE = 0.12;

        private double minA = MIN_A;
        private double maxA = MAX_A;

        private double minB = MIN_B;
        private double maxB = MAX_B;

        private double minM = MIN_M;
        private double maxM = MAX_M;

        private double minC = MIN_C;
        private double maxC = MAX_C;

        private double zeroChance = ZERO_CHANCE;
        private double constChance = NONZERO_CONSTANT_CHANCE;

        public Builder minA(int minA)
        {
            this.minA = minA;
            return this;
        }

        public Builder maxA(int maxA)
        {
            this.minA = maxA;
            return this;
        }

        public Builder rangeA(int minA, int maxA)
        {
            this.minA = minA;
            this.maxA = maxA;
            return this;
        }

        public Builder rangeA(int rangeA)
        {
            this.minA = -rangeA;
            this.maxA = rangeA;
            return this;
        }

        public Builder a(double a)
        {
            this.minA = a;
            this.maxA = a;
            return this;
        }

        public Builder minB(int minB)
        {
            this.minB = minB;
            return this;
        }

        public Builder maxB(int maxB)
        {
            this.minB = maxB;
            return this;
        }

        public Builder rangeB(int minB, int maxB)
        {
            this.minB = minB;
            this.maxB = maxB;
            return this;
        }

        public Builder rangeB(int rangeB)
        {
            this.minB = 0;
            this.maxB = rangeB;
            return this;
        }

        public Builder b(double b)
        {
            this.minB = b;
            this.maxB = b;
            return this;
        }

        public Builder minM(int minM)
        {
            this.minM = minM;
            return this;
        }

        public Builder maxM(int maxM)
        {
            this.minM = maxM;
            return this;
        }

        public Builder rangeM(int minM, int maxM)
        {
            this.minM = minM;
            this.maxM = maxM;
            return this;
        }

        public Builder rangeM(int rangeM)
        {
            this.minM = -rangeM;
            this.maxM = rangeM;
            return this;
        }

        public Builder m(double m)
        {
            this.minM = m;
            this.maxM = m;
            return this;
        }

        public Builder minC(int minC)
        {
            this.minC = minC;
            return this;
        }

        public Builder maxC(int maxC)
        {
            this.minC = maxC;
            return this;
        }

        public Builder rangeC(int minC, int maxC)
        {
            this.minC = minC;
            this.maxC = maxC;
            return this;
        }

        public Builder rangeC(int rangeC)
        {
            this.minC = -rangeC;
            this.maxC = rangeC;
            return this;
        }

        public Builder c(double c)
        {
            this.minC = c;
            this.maxC = c;
            return this;
        }

        public Builder zeroChance(double zeroChance)
        {
            this.zeroChance = zeroChance;
            return this;
        }

        public Builder nonzeroConstantChance(double constChance)
        {
            this.constChance = constChance;
            return this;
        }

        public Exponential build()
        { return build(IMathConstants.DEFAULT_PLACES); }

        public Exponential build(int places)
        {
            if (zeroChance + constChance > 1)
                throw new IllegalStateException("sum of zero and non-zero const chances exceeds 1");
                
            if (zeroChance > 0)
            {
                if (maxA < 0 || minA > 0)
                    throw new IllegalStateException("0 not in range, but zero chance isn't 0");

                if (IMathUtils.equals(minB, maxB, places) && IMathUtils.equals(maxB, 0, places))
                    throw new IllegalStateException("b = 0, but zero chance isn't 0");
            }

            if (constChance > 0
                    && IMathUtils.equals(minA, maxA, places)
                    && IMathUtils.equals(maxA, 0, places))
            {
                throw new IllegalStateException("a = 0, but non-zero const chance isn't 0");
            }

            if (constChance < 1)
            {
                if (IMathUtils.equals(minB, maxB, places) && IMathUtils.equals(maxB, 1, places))
                    throw new IllegalStateException("b = 1, but non-zero const chance isn't 1");

                if (IMathUtils.equals(minM, maxM, places) && IMathUtils.equals(maxB, 0, places))
                    throw new IllegalStateException("m = 0, but non-zero const chance isn't 1");
            }

            double chance = IMathUtils.randomDouble(0, 1);
            if (chance < zeroChance)
                return Exponential.constant(0);

            if (chance < zeroChance + constChance)
            {
                if (minA == maxA)
                    return Exponential.constant(maxA);

                double a;

                do
                {
                    a = IMathUtils.randomInt((int) minA, (int) maxA);
                } while (IMathUtils.equals(a, 0, places));

                return Exponential.constant(a);
            }

            Exponential exponential;

            do
            {
                double a = minA == maxA ? maxA : IMathUtils.randomInt((int) minA, (int) maxA);
                double b = minB == maxB ? maxB : IMathUtils.randomInt((int) minB, (int) maxB);
                double m = minM == maxM ? maxM : IMathUtils.randomInt((int) minM, (int) maxM);
                double c = minC == maxC ? maxC : IMathUtils.randomInt((int) minC, (int) maxC);
                exponential = new Exponential(a, b, m, c);
            } while (exponential.isConstant(places));

            return exponential;
        }
    }
}
