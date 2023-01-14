package goodtrailer.whetstone.app;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import goodtrailer.whetstone.app.problem.ChapterProblemFactory;
import goodtrailer.whetstone.framework.gui.QuizPanel;
import goodtrailer.whetstone.framework.util.IResourceUtils;

public class Main
{
    public static final String NAME = "whetstone-app: Larson, Boswell - Big Ideas Math: Integrated Mathematics I";

    public static final float FONT_SIZE = 18.f;
    public static final FontUIResource UI_FONT;

    public static final Dimension DEFAULT_SIZE = new Dimension(600, 600);
    public static final int PADDING = 5;
    public static final int PROBLEM_COUNT = 30;
    public static final int SCROLL_INCREMENT = 32;

    static
    {
        FontUIResource fontUIResource;
        try
        {
            var file = IResourceUtils.getFile(Main.class, "STIXTwoMath-Regular.otf");
            var font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(FONT_SIZE);
            fontUIResource = new FontUIResource(font);
        }
        catch (FontFormatException | IOException e)
        {
            System.err.print("Failed to set font: ");
            e.printStackTrace();

            fontUIResource = new FontUIResource(Font.SERIF, Font.PLAIN, (int)FONT_SIZE);
        }
        UI_FONT = fontUIResource;
    }

    public static void main(String[] vargs)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.err.print("Failed to set Look & Feel: ");
            e.printStackTrace();
        }

        Iterable<?> keys = () -> UIManager.getDefaults().keys().asIterator();
        for (var key : keys)
            if (UIManager.get(key) instanceof FontUIResource)
                UIManager.put(key, UI_FONT);

        SwingUtilities.invokeLater(Main::run);
    }

    private static void run()
    {
        var frame = new JFrame(NAME);

        var mainPanel = new JPanel();

        var quizPanel = new QuizPanel();
        var bottomBox = new Box(BoxLayout.LINE_AXIS);

        var correctLabel = new JLabel("\u2013/\u2013");
        var chapterCombo = new JComboBox<String>();
        var submitButton = new JButton("Submit");

        var chapters = new ArrayList<Integer>(ChapterProblemFactory.chapters());
        chapters.sort(Integer::compare);
        for (var c : chapters)
            chapterCombo.addItem("Chapter " + c);

        chapterCombo.setSelectedIndex(-1);
        chapterCombo.setMaximumSize(chapterCombo.getPreferredSize());
        chapterCombo.addActionListener((ActionEvent ae) ->
        {
            int chapter = chapters.get(chapterCombo.getSelectedIndex());
            quizPanel.generate(new ChapterProblemFactory(chapter), PROBLEM_COUNT);
            quizPanel.revalidate();
        });
        submitButton.addActionListener((ActionEvent ae) ->
        {
            int correct = quizPanel.submitAll();
            correctLabel.setText(String.format("%d/%d", correct, quizPanel.count()));
        });

        quizPanel.setPreferredSize(DEFAULT_SIZE);
        quizPanel.setScrollableIncrement(SCROLL_INCREMENT);
        bottomBox.add(correctLabel);
        bottomBox.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        bottomBox.add(chapterCombo);
        bottomBox.add(Box.createHorizontalGlue());
        bottomBox.add(submitButton);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        mainPanel.add(quizPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, PADDING)));
        mainPanel.add(bottomBox);

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
