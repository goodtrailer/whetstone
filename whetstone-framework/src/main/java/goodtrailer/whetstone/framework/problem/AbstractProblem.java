package goodtrailer.whetstone.framework.problem;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class AbstractProblem implements IProblem
{
    public static final int COLUMNS = 20;
    public static final int PADDING = 10;

    private JPanel panel = new JPanel();
    private JTextArea promptText = new JTextArea("void");

    public AbstractProblem()
    {
        initialize();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        var components = new ArrayList<JComponent>();
        addComponents(components);
        for (var c : components)
        {
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(c);
        }
    }

    @Override
    public final JComponent getRootComponent()
    { return panel; }

    protected void addComponents(List<JComponent> components)
    {
        promptText.setColumns(COLUMNS);
        promptText.setEditable(false);
        promptText.setLineWrap(true);
        promptText.setOpaque(false);
        promptText.setText(getPrompt());
        promptText.setWrapStyleWord(true);

        components.add(promptText);
        components.add(createFiller());
    }

    protected abstract void initialize();

    protected abstract String getPrompt();

    protected static JComponent createFiller()
    {
        var dim = new Dimension(0, PADDING);
        return new Box.Filler(dim, dim, dim);
    }
}
