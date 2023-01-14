package goodtrailer.whetstone.app.problem;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;

import goodtrailer.whetstone.app.problem.chapter10.Chapter10ProblemFactory;
import goodtrailer.whetstone.app.problem.chapter6.Chapter6ProblemFactory;
import goodtrailer.whetstone.app.problem.chapter8.Chapter8ProblemFactory;
import goodtrailer.whetstone.framework.problem.IProblem;
import goodtrailer.whetstone.framework.problem.IProblemFactory;

public class ChapterProblemFactory implements IProblemFactory
{
    private IProblemFactory factory;
    private static final HashMap<Integer, Supplier<IProblemFactory>> factories;

    static
    {
        factories = new HashMap<Integer, Supplier<IProblemFactory>>();
        factories.put(6, Chapter6ProblemFactory::new);
        factories.put(8, Chapter8ProblemFactory::new);
        factories.put(10, Chapter10ProblemFactory::new);
    }

    public ChapterProblemFactory(int chapter)
    { factory = factories.get(chapter).get(); }

    @Override
    public IProblem get()
    { return factory.get(); }

    public static Set<Integer> chapters()
    { return factories.keySet(); }
}
