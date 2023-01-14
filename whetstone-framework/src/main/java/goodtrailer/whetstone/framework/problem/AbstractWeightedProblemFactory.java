package goodtrailer.whetstone.framework.problem;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractWeightedProblemFactory implements IProblemFactory
{
    private List<WeightedFactory> wFactories = getWeightedFactories();
    private double[] thresholds = new double[wFactories.size()];
    private double weightSum = 0;

    public AbstractWeightedProblemFactory()
    {
        for (int i = 0; i < wFactories.size(); i++)
        {
            thresholds[i] = weightSum;
            weightSum += wFactories.get(i).weight();
        }
    }

    @Override
    public final IProblem get()
    {
        if (thresholds.length == 0)
            throw new IllegalStateException("no weighted problem factories");

        double x = Math.random() * weightSum;
        int i = Arrays.binarySearch(thresholds, x);
        if (i < 0)
            i = -i - 2;
        return wFactories.get(i).get();
    }

    protected abstract List<WeightedFactory> getWeightedFactories();

    protected record WeightedFactory(IProblemFactory factory, double weight)
            implements IProblemFactory
    {
        public WeightedFactory(IProblemFactory factory, double weight)
        {
            this.factory = factory;
            this.weight = weight;
        }
        
        @Override
        public IProblem get()
        { return factory.get(); }
    };
}
