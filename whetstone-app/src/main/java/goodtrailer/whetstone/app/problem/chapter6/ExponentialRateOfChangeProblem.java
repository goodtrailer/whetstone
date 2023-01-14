package goodtrailer.whetstone.app.problem.chapter6;

import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.function.Exponential;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;

class ExponentialRateOfChangeProblem extends AbstractFrqProblem
{
    private static final int range_b = 3;

    private Exponential exponential;

    @Override
    protected void initialize()
    {
        exponential = Exponential.builder()
                .rangeB(range_b)
                .m(1)
                .zeroChance(0)
                .nonzeroConstantChance(0)
                .build();
    }

    @Override
    protected String getPrompt()
    {
        String message = "Find the percentage rate of change of the exponential equation:\n\n"
                + "y = %s";

        return String.format(message, exponential.toString());
    }

    @Override
    protected Result checkInput(String input)
    { return IMathUtils.tryParsePercentageEquals(exponential.b() - 1, input); }
}
