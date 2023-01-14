package goodtrailer.whetstone.framework.problem;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

public abstract class AbstractMcqProblem extends AbstractProblem
{
    private Box buttonBox;
    private ButtonGroup buttonGroup;
    private JRadioButton[] buttons;
    private int correctIndex;

    @Override
    public final Result submit()
    {
        var result = Result.INVALID;
        for (int i = 0; i < buttons.length; i++)
        {
            if (buttons[i].isSelected())
            {
                result = Result.from(i == correctIndex);
                buttons[i].setBackground(result.toColor());
            }

            buttons[i].setOpaque(buttons[i].isSelected());
            buttons[i].repaint();
        }
        buttonBox.setOpaque(result == Result.INVALID);
        buttonBox.repaint();

        return result;
    }

    @Override
    protected void addComponents(List<JComponent> components)
    {
        super.addComponents(components);

        var choices = getChoices();
        correctIndex = choices.correctIndex;
        buttonBox = new Box(BoxLayout.PAGE_AXIS);
        buttonGroup = new ButtonGroup();
        buttons = new JRadioButton[choices.descriptions.size()];
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JRadioButton(choices.descriptions.get(i));
            buttonBox.add(buttons[i]);
            buttonGroup.add(buttons[i]);
        }
        buttonBox.setBackground(Result.INVALID.toColor());

        components.addAll(List.of(buttonBox, createFiller()));
    }

    protected abstract Choices getChoices();

    protected record Choices(List<String> descriptions, int correctIndex)
    {
        public Choices(List<String> descriptions, int correctIndex)
        {
            if (correctIndex < 0 || correctIndex >= descriptions.size())
                throw new IllegalArgumentException("invalid correct index");

            this.descriptions = descriptions;
            this.correctIndex = correctIndex;
        }
    }
}
