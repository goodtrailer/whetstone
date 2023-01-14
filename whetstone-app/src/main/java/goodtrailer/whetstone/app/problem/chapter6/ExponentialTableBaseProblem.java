package goodtrailer.whetstone.app.problem.chapter6;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import goodtrailer.whetstone.framework.math.IFunction;
import goodtrailer.whetstone.framework.math.IMathConstants;
import goodtrailer.whetstone.framework.math.IMathUtils;
import goodtrailer.whetstone.framework.math.function.Exponential;
import goodtrailer.whetstone.framework.math.function.Linear;
import goodtrailer.whetstone.framework.problem.AbstractFrqProblem;
import goodtrailer.whetstone.framework.problem.Result;
import goodtrailer.whetstone.framework.util.IBooleanUtils;

class ExponentialTableBaseProblem extends AbstractFrqProblem
{
    private static final int range_x = 2;

    private static final int range_b = 5;
    private static final int range_m = 1;
    private static final int range_c = 3;

    private static final int table_places = 12;

    private IFunction function;
    private double base;

    @Override
    protected void initialize()
    {
        if (IBooleanUtils.random())
        {
            var exponential = Exponential.builder()
                    .rangeB(range_b)
                    .rangeM(range_m)
                    .rangeC(range_c)
                    .zeroChance(0)
                    .nonzeroConstantChance(0)
                    .build();

            function = exponential;
            base = exponential.b();
        }
        else
        {
            function = Linear.random();
            base = -1;
        }
    }

    @Override
    protected void addComponents(List<JComponent> components)
    {
        super.addComponents(components);

        String input = IMathConstants.DEFAULT_VARIABLE;
        String output = String.format("f(%s)", IMathConstants.DEFAULT_VARIABLE);
        var headers = new String[] { input, output };

        var data = new String[2 * range_x + 1][2];
        for (int i = 0, x = -range_x; x <= range_x; i++, x++)
        {
            data[i][0] = IMathUtils.toString(x);
            data[i][1] = IMathUtils.toString(function.evaluate(x), table_places);
        }

        var table = new JTable(data, headers);
        int fontHeight = table.getFontMetrics(table.getFont()).getHeight();
        table.setRowHeight(fontHeight);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        var scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(300, 0));

        components.addAll(components.size() - 2, List.of(scrollPane, createFiller()));
    }

    @Override
    protected String getPrompt()
    {
        String message = "Is the following table's function exponential?\n"
                + "If so, find its base (reciprocals are valid).\n"
                + "Otherwise, write -1.";

        return message;
    }

    @Override
    protected Result checkInput(String string)
    {
        double input;

        try
        {
            input = IMathUtils.parseFraction(string);
        }
        catch (NumberFormatException nfe)
        {
            return Result.INVALID;
        }

        return Result.from(IMathUtils.equals(input, base) || IMathUtils.equals(input, 1. / base));
    }
}
