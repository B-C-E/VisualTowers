package towers;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Visualizer
{
    private JFrame frame;
    private Canvas canvas;
    private int width;
    private int height;
    private String name;

    public Visualizer(String name)
    {
        this.name = name;
        frame = new JFrame(name);
        canvas = new Canvas();
        setSize(width,height);

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void setSize(int newWidth, int newHeight)
    {
        width = newWidth;
        height = newHeight;
        canvas.setSize(width,height);
    }

    // Accessors
    // And
    // Mutators

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

}//end of visualizer
