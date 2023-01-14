package goodtrailer.whetstone.app.problem.chapter8;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.Point;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;
import goodtrailer.whetstone.framework.util.IResourceUtils;

public class AdjacentAngleProblem extends AbstractFrqProblem
{
    private static final int min_abc = 90;
    private static final int max_abc = 315;
    private static final int variants_count = 3;
    
    private int angleABC;
    private Linear angleABD;
    private Linear angleCBD;
    private Point point;
    
    @Override
    protected void initialize()
    {
        angleABC = IMathUtils.randomInt(min_abc, max_abc);
        
        angleABD = Linear.random();
        while (angleABD.isConstant())
            angleABD = Linear.random();
        
        angleCBD = Linear.random();
        while (angleCBD.isConstant())
            angleCBD = Linear.random();
        
        var angleSum = angleABD.plus(angleCBD);
        double x = angleSum.solution(Linear.constant(angleABC)).point().x();
        point = new Point(angleABD.evaluate(x), angleCBD.evaluate(x));
    }
    
    @Override
    protected String getPrompt()
    {
        String message = "In the diagram below, \u2220ABD and \u2220CBD are adjacent angles. The "
                + "diagram is not to scale.\n\n"
                + "m\u2220ABC = %d\u00B0\n"
                + "m\u2220ABD = a\u00B0 = (%s)\u00B0\n"
                + "m\u2220CBD = c\u00B0 = (%s)\u00B0\n\n"
                + "Find the values of a and c. Give your solution in the form of a point (a, c).";
        return String.format(message, angleABC, angleABD.toString(), angleCBD.toString());
    }

    @Override
    protected void addComponents(List<JComponent> components)
    {
        super.addComponents(components);

        int variant = (int) (Math.random() * variants_count);
        String filename = String.format("AdjacentAngle%d.png", variant);
        
        var label = new JLabel(IResourceUtils.getImage(this, filename));
        components.addAll(components.size() - 2, List.of(label, createFiller()));
    }
    
    @Override
    protected Result checkInput(String input)
    {
        return point.tryParseEquals(input);
    }
}
