package goodtrailer.whetstone.framework.problem;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public abstract class AbstractFrqProblem extends AbstractProblem
{
    private JTextArea inputText;

    @Override
    public final Result submit()
    {
        var result = checkInput(inputText.getText());
        inputText.setBackground(result.toColor());
        return result;
    }

    @Override
    protected void addComponents(List<JComponent> components)
    {
        super.addComponents(components);

        inputText = new JTextArea();
        inputText.setColumns(COLUMNS);
        inputText.setLineWrap(true);

        components.addAll(List.of(inputText, createFiller()));
    }

    protected abstract Result checkInput(String input);
}
