package goodtrailer.whetstone.framework.problem;

public abstract class AbstractRandomProblemFactory implements IProblemFactory
{
    private IProblemFactory[] factories = getFactories();

    @Override
    public final IProblem get()
    { return factories[(int) (Math.random() * factories.length)].get(); }

    protected abstract IProblemFactory[] getFactories();
}
