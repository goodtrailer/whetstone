package goodtrailer.whetstone.app.problem.chapter10;

import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;

class ParallelLineDistanceProblem extends AbstractFrqProblem
{
    private Linear line0;
    private Linear line1;
    private double distance;

    @Override
    protected void initialize()
    {
        line0 = Linear.random();
        line1 = Linear.random().withM(line0.m());
        distance = line0.distance(line1);
    }

    @Override
    protected String getPrompt()
    {
        String message = "Find the minimum distance between the lines { y\u2080 = %s } and "
                + "{ y\u2081 = %s }.";
        return String.format(message, line0.toString(), line1.toString());
    }

    @Override
    protected Result checkInput(String input)
    {
        return IMathUtils.tryParseFractionEquals(distance, input);
    }
}
