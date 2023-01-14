package goodtrailer.whetstone.app.problem.chapter6;

import java.util.ArrayList;
import java.util.List;

import goodtrailer.whetstone.framework.math.Interval;
import goodtrailer.whetstone.framework.math.SolutionType;
import goodtrailer.whetstone.framework.math.function.Exponential;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractMcqPoolProblem;

class ExponentialRangeProblem extends AbstractMcqPoolProblem<Interval>
{
    private Exponential exponential;

    @Override
    protected void initialize()
    { exponential = Exponential.random(); }

    @Override
    protected String getPrompt()
    {
        String message = "Describe the range of the equation:\n\n"
                + "y = %s";

        return String.format(message, exponential.toString());
    }

    @Override
    protected List<Interval> getChoicePool()
    {
        var choices = new ArrayList<Interval>(List.of(
                Interval.real(),
                Interval.real().withLower(0),
                Interval.real().withUpper(0)));
        choices.add(null);

        var exponent = new Linear(exponential.m(), exponential.c());
        var solution = exponent.solution(Linear.constant(1));
        if (solution.type() == SolutionType.EXISTS)
            choices.add(Interval.point(exponential.evaluate(solution.point().x())));

        return choices;
    }

    @Override
    protected Interval getAnswer()
    {
        var range = exponential.range();
        return range.isEmpty() ? null : range.get(0);
    }

    @Override
    protected String getDescription(Interval choice)
    { return choice == null ? SolutionType.DNE.toString() : choice.toString(); }
}
