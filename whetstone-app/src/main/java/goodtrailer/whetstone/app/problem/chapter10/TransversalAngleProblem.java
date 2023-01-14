package goodtrailer.whetstone.app.problem.chapter10;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.Point;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;
import goodtrailer.whetstone.framework.util.IResourceUtils;

class TransversalAngleProblem extends AbstractFrqProblem
{
    private static final int min_angle_c = 1;
    private static final int max_angle_c = 179;
    private static final int variants_count = 4;

    private Linear angleA, angleB;
    private int angleC;
    private int variant;
    private Point point;

    @Override
    protected void initialize()
    {
        angleA = Linear.random();
        while (angleA.isConstant())
            angleA = Linear.random();

        angleB = Linear.random();
        while (angleB.isConstant())
            angleB = Linear.random();

        angleC = IMathUtils.randomInt(min_angle_c, max_angle_c);
        variant = (int) (Math.random() * variants_count);

        double x, y;
        switch (variant)
        {
        // that moment when you realize every transversal problem is the same...
        case 0:
        case 2:
        case 3:
            x = angleA.solution(Linear.constant(180 - angleC)).point().x();
            y = angleB.solution(Linear.constant(angleC)).point().x();
            break;
        case 1:
            x = angleA.solution(Linear.constant(angleC)).point().x();
            y = angleB.solution(Linear.constant(180 - angleC)).point().x();
            break;
        default:
            throw new IllegalStateException("invalid problem variant");
        }
        point = new Point(x, y);
    }

    @Override
    protected String getPrompt()
    {
        String message = "In the diagram below, A, B, and C are angles of a transversal. The "
                + "diagram is not to scale.\n\n"
                + "m\u2220C = %d\u00B0\n"
                + "m\u2220A = (%s)\u00B0\n"
                + "m\u2220B = (%s)\u00B0\n\n"
                + "Find the values of x and y. Give your solution in the form of a point (x, y).";
        return String.format(message, angleC, angleA.toString(), angleB.toString("y"));
    }

    @Override
    protected void addComponents(List<JComponent> components)
    {
        super.addComponents(components);

        String filename = String.format("Transversal%d.png", variant);
        var label = new JLabel(IResourceUtils.getImage(this, filename));
        components.addAll(components.size() - 2, List.of(label, createFiller()));
    }

    @Override
    protected Result checkInput(String input)
    {
        return point.tryParseEquals(input);
    }
}
