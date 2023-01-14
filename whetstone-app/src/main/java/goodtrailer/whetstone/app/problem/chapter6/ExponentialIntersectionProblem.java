package goodtrailer.whetstone.app.problem.chapter6;

import goodtrailer.whetstone.framework.math.Solution;
import goodtrailer.whetstone.framework.math.SolutionType;
import goodtrailer.whetstone.framework.math.function.Exponential;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;

class ExponentialIntersectionProblem extends AbstractFrqProblem
{
    private Exponential exponential0;
    private Exponential exponential1;
    private Solution solution;

    @Override
    protected void initialize()
    {
        exponential0 = Exponential.builder().minB(1).build();
        exponential1 = Exponential.random().withB(exponential0.b());
        solution = exponential0.solution(exponential1);
    }

    @Override
    protected String getPrompt()
    {
        String message = "Find the point where the curves y\u2080 and y\u2081 intersect.\n\n"
                + "y\u2080 = %s\n"
                + "y\u2081 = %s\n\n"
                + "%s and %s are valid. Give your solution in the form of a point (x, y).";

        return String.format(message, exponential0.toString(), exponential1.toString(),
                SolutionType.DNE, SolutionType.TRUE);
    }

    @Override
    protected Result checkInput(String input)
    { return solution.tryParseEquals(input); }
}
