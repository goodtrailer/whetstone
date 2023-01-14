package goodtrailer.whetstone.app.problem.chapter10;

import java.util.List;

import goodtrailer.whetstone.framework.problem.AbstractWeightedProblemFactory;

public class Chapter10ProblemFactory extends AbstractWeightedProblemFactory
{
    private static final List<WeightedFactory> weighted_factories = List.of(
            new WeightedFactory(TransversalAngleProblem::new, 1),
            new WeightedFactory(PointLineDistanceProblem::new, 2),
            new WeightedFactory(ParallelLineDistanceProblem::new, 1));

    @Override
    protected List<WeightedFactory> getWeightedFactories()
    { return weighted_factories; }
}
