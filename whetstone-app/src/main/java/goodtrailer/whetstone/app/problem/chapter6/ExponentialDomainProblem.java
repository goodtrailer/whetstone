package goodtrailer.whetstone.app.problem.chapter6;

import java.util.ArrayList;
import java.util.List;

import goodtrailer.whetstone.framework.math.Interval;
import goodtrailer.whetstone.framework.math.SolutionType;
import goodtrailer.whetstone.framework.math.function.Exponential;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractMcqPoolProblem;

class ExponentialDomainProblem extends AbstractMcqPoolProblem<Interval>
{
    private Exponential exponential;

    @Override
    protected void initialize()
    { exponential = Exponential.random(); }

    @Override
    protected String getPrompt()
    {
        String message = "Describe the domain of the exponential equation:\n\n"
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
        var solution = exponent.solution(Linear.constant(0));
        if (solution.type() == SolutionType.EXISTS)
        {
            double bound = solution.point().x();
            choices.addAll(List.of(
                    Interval.real().withLower(bound),
                    Interval.real().withUpper(bound)));
        }

        return choices;
    }

    @Override
    protected Interval getAnswer()
    {
        var domain = exponential.domain();
        return domain.isEmpty() ? null : domain.get(0);
    }

    @Override
    protected String getDescription(Interval choice)
    { return choice == null ? SolutionType.DNE.toString() : choice.toString(); }
}
