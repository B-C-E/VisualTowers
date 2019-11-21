package towers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Stack;
import javax.swing.*;

public class Visualizer
{
    public HanoiPanel panel;
    public HanoiSolver solver;
    private int width;
    private int height;
    private String name;

    public JFrame getFrame()
    {
        return frame;
    }

    private JFrame frame;
    public Visualizer(int width, int height, String name, HanoiSolver solver)
    {
        this.width = width;
        this.height = height;
        JFrame frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocation(20, 20);

        panel = new HanoiPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        this.solver = solver;
    }

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

    //internal class
    public class HanoiPanel extends JPanel
    {


        //draws a hanoi game
        public void paint(Graphics g)
        {
            //clear
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, width, height);
            //draw & label base
            g.setColor(Color.BLUE);
            int leftBase = width - (int) (width * 0.95);
            int topBase = (int) (height * 0.8);
            int lengthBase = (int) (width * 0.9);
            int heightBase = height / 10;
            g.fillRect(leftBase, topBase, lengthBase, heightBase);

            //draw poles
            g.setColor(Color.lightGray);

            int widthPole = (int) ((8.5 / 10) * (lengthBase / 3.2) * (1.0 / solver.getDiscs()));
            //Width of the pole is 85% the size of the smallest disk that will be on it.
            int heightPole = (height) / 2;
            int firstPoleLocation = leftBase + lengthBase / 6;
            int secondPoleLocation = leftBase + (lengthBase / 2);
            int thirdPoleLocation = leftBase + (lengthBase / 6) * 5;
            g.fillRect(firstPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);
            g.fillRect(secondPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);
            g.fillRect(thirdPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);

            //populate poles

            int discHeight = heightPole / solver.getDiscs();
            int heightSpot = discHeight;
            int maxDiscWidth = (int) (lengthBase / 3.2);

            //pole 1
            Stack<Integer> reverseDiscStack = new Stack<Integer>();
            for (int discNumb : solver.getStackA())
            {
                reverseDiscStack.push(discNumb);
            }

            for (int discNumb : reverseDiscStack)
            {
                //generate proper color for each disc
                g.setColor(generateDiskColor(discNumb));
                //draw disc
                int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                g.fillRect(firstPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                heightSpot += discHeight;
            }

            //pole 2
            heightSpot = discHeight;
            reverseDiscStack = new Stack<Integer>();
            for (int discNumb : solver.getStackB())
            {
                reverseDiscStack.push(discNumb);
            }

            for (int discNumb : reverseDiscStack)
            {
                //generate proper color for each disc
                g.setColor(generateDiskColor(discNumb));
                //draw disc
                int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                g.fillRect(secondPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                heightSpot += discHeight;
            }

            //pole 3
            heightSpot = discHeight;
            reverseDiscStack = new Stack<Integer>();
            for (int discNumb : solver.getStackC())
            {
                reverseDiscStack.push(discNumb);
            }

            for (int discNumb : reverseDiscStack)
            {
                //generate proper color for each disc
                g.setColor(generateDiskColor(discNumb));
                //draw disc
                int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                g.fillRect(thirdPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                heightSpot += discHeight;
            }


        }//end of paint

        //given a disc number, what color should it be
        public Color generateDiskColor(int numb)
        {
            numb = numb % 6;
            switch (numb)
            {
                case 0:
                    return Color.ORANGE;
                case 1:
                    return Color.yellow;
                case 2:
                    return Color.green;
                case 3:
                    return Color.cyan;
                case 4:
                    return Color.MAGENTA;
                case 5:
                    return Color.RED;
            }
            return Color.cyan;
        }//end of generateDiskColor

    }//end of HanoiPanel
}//end of visualizer
