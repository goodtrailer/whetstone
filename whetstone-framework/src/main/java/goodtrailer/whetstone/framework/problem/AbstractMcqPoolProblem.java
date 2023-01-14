package goodtrailer.whetstone.framework.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMcqPoolProblem<T> extends AbstractMcqProblem
{
    public static final int DEFAULT_CHOICE_COUNT = 4;
    
    @Override
    protected final Choices getChoices()
    {
        var choices = getChoicePool();
        var answer = getAnswer();
        
        for (int i = 0; i < choices.size(); i++)
        {
            var choice = choices.get(i);

            boolean equal = false;
            if (choice == null)
            {
                if (answer == null)
                    equal = true;
            }
            else if (choice.equals(answer))
            {
                equal = true;
            }
            
            if (equal)
            {
                choices.remove(i);
                break;
            }
        }

        Collections.shuffle(choices);
        
        int choiceCount = getChoiceCount();
        int correctIndex = (int) (Math.random() * choiceCount);
        choices.add(correctIndex, answer);
        
        var descriptions = new ArrayList<String>();
        for (int i = 0; i < choiceCount; i++)
            descriptions.add(getDescription(choices.get(i)));

        return new Choices(descriptions, correctIndex);
    }
    
    protected abstract List<T> getChoicePool();
    
    protected abstract T getAnswer();
    
    protected abstract String getDescription(T choice);
    
    protected int getChoiceCount()
    {
        return DEFAULT_CHOICE_COUNT;
    }
}
