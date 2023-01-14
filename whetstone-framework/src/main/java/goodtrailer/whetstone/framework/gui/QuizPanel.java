package goodtrailer.whetstone.framework.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import goodtrailer.whetstone.framework.problem.IProblem;
import goodtrailer.whetstone.framework.problem.IProblemFactory;
import goodtrailer.whetstone.framework.problem.Result;

public class QuizPanel extends JPanel
{
    private static final long serialVersionUID = 4126538549451781876L;

    private ScrollableBox problemsBox = new ScrollableBox(BoxLayout.PAGE_AXIS, true, false);
    private JScrollPane scrollPane = new JScrollPane(problemsBox);
    private IProblem[] problems;

    public QuizPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(scrollPane);
    }

    public void generate(IProblemFactory factory, int count)
    {
        problems = new IProblem[count];
        problemsBox.removeAll();

        {
            var p = factory.get();
            problems[count - 1] = p;
            problemsBox.add(p.getRootComponent(), 0);
        }

        for (int i = count - 2; i >= 0; i--)
        {
            var p = factory.get();
            problems[i] = p;
            problemsBox.add(new JSeparator(SwingConstants.HORIZONTAL), 0);
            problemsBox.add(p.getRootComponent(), 0);
        }
    }

    public void setScrollableIncrement(int increment)
    { problemsBox.setScrollableIncrement(increment); }

    public int submitAll()
    {
        int correct = 0;
        for (var question : problems)
            if (question.submit() == Result.CORRECT)
                correct++;
        return correct;
    }

    public int count()
    { return problems.length; }
}
