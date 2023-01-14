package goodtrailer.whetstone.framework.gui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.Scrollable;

class ScrollableBox extends Box implements Scrollable
{
    private static final long serialVersionUID = 8642183969100983700L;

    private int increment = 20;
    private boolean tracksWidth;
    private boolean tracksHeight;

    public ScrollableBox(int axis, boolean tracksWidth, boolean tracksHeight)
    {
        super(axis);
        this.tracksWidth = tracksWidth;
        this.tracksHeight = tracksHeight;
    }

    public void setScrollableIncrement(int increment)
    { this.increment = increment; }

    @Override
    public Dimension getPreferredScrollableViewportSize()
    { return getPreferredSize(); }

    @Override
    public int getScrollableUnitIncrement(Rectangle visible, int orientation, int direction)
    { return increment; }

    @Override
    public int getScrollableBlockIncrement(Rectangle visible, int orientation, int direction)
    { return increment; }

    @Override
    public boolean getScrollableTracksViewportWidth()
    { return tracksWidth; }

    @Override
    public boolean getScrollableTracksViewportHeight()
    { return tracksHeight; }
}
