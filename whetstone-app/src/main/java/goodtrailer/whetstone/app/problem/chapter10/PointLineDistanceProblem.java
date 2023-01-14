package goodtrailer.whetstone.app.problem.chapter10;

import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.Point;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;

class PointLineDistanceProblem extends AbstractFrqProblem
{
    private Linear line;
    private Point point;
    private double distance;

    @Override
    protected void initialize()
    {
        line = Linear.random();
        point = Point.random(2);

        double slope = -1.0 / line.m();
        double yIntercept = -slope * point.x() + point.y();
        var perpendicular = new Linear(slope, yIntercept);
        var intersection = line.solution(perpendicular).point();
        distance = intersection.distance(point);
    }

    @Override
    protected String getPrompt()
    {
        String message = "Find the minimum distance from the point %s to the line { y = %s }.";
        return String.format(message, point.toString(), line.toString());
    }

    @Override
    public Result checkInput(String input)
    {
        return IMathUtils.tryParseFractionEquals(distance, input);
    }
}
