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
    public VisualHanoiSolver solver;
    private int width;
    private int height;
    private String name;

    public JFrame getFrame()
    {
        return frame;
    }

    private JFrame frame;
    public Visualizer(int width, int height, String name, VisualHanoiSolver solver)
    {
        //if width is given as -1, autogenerate a nice size for the width
        if (width == -1)
        {
            //get screen size using the Toolkit class
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = screenSize.width*3/4;
            height = screenSize.height*3/4;
        }//end of autogenerating width and height

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

        //construct south
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
        //this gets pretty complicated, as it will accurately scale for any screen size
        public void paint(Graphics g)
        {
                /////////////////////////////////
                //clear the screen to dark grey//
                /////////////////////////////////
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, width, height);


                ///////////////////////////////////
                //Draw the base of the Hanoi Game//
                ///////////////////////////////////
                g.setColor(Color.BLUE);

                //the top left of the base is calculated as follows:
                int leftBase = width - (int) (width * 0.95);
                int topBase = (int) (height * 0.8);

                //the width and height of the base are calculated as follows:
                int lengthBase = (int) (width * 0.9);
                int heightBase = height / 10;

                //draw the base
                g.fillRect(leftBase, topBase, lengthBase, heightBase);

                //////////////////
                //Draw the poles//
                //////////////////

                g.setColor(Color.lightGray);

                //first, we will calulate the size and position of the poles

                //Width of the pole is 85% the size of the smallest disk that will be on it.
                int widthPole = (int) ((8.5 / 10) * (lengthBase / 3.2) * (1.0 / solver.getDiscs()));

                //height of the pole is 0.55 * the height of the screen
                int heightPole = height * 55 / 100;

                //the first one is 1/6 of a base length away from the left edge of the base
                int firstPoleLocation = leftBase + lengthBase / 6;
                //the second one is in the dead center
                int secondPoleLocation = leftBase + (lengthBase / 2);
                //the third one is 1/6 of a base length away from the right edge of the base
                int thirdPoleLocation = leftBase + (lengthBase / 6) * 5;

                //draw the poles!
                g.fillRect(firstPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);
                g.fillRect(secondPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);
                g.fillRect(thirdPoleLocation - widthPole / 2, topBase - heightPole, widthPole, heightPole);

                //////////////////
                //populate poles//
                //////////////////

                //Calculate the height of a disc ( 1/2 of the screen height / number of discs)
                int discHeight = (height / 2) / solver.getDiscs();
                int heightSpot = discHeight;

                //The largest disc (the bottom disc) is 32/100ths the length of the base
                int maxDiscWidth = (int) (lengthBase / 3.2);

                //populate pole 1

                //get all the discs to draw
                Stack<Integer> reverseDiscStack = new Stack<Integer>();
                synchronized (solver.getStackA())
                {
                    for (int discNumb : solver.getStackA())
                    {
                        reverseDiscStack.push(discNumb);
                    }
                }

                //for each disc
                for (int discNumb : reverseDiscStack)
                {
                    //generate proper color for each disc
                    g.setColor(generateDiskColor(discNumb));
                    //draw disc
                    int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                    g.fillRect(firstPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                    heightSpot += discHeight;
                }

                //populate pole 2

                //reset how high up we are drawing discs
                heightSpot = discHeight;

                //get all the discs to draw
                reverseDiscStack = new Stack<Integer>();
                synchronized (solver.getStackB()){
                for (int discNumb : solver.getStackB())
                {
                    reverseDiscStack.push(discNumb);
                }
                }

                //for each disc
                for (int discNumb : reverseDiscStack)
                {
                    //generate proper color for each disc
                    g.setColor(generateDiskColor(discNumb));
                    //draw disc
                    int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                    g.fillRect(secondPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                    heightSpot += discHeight;
                }

                //populatepole 3

                //reset how high up we are drawing discs
                heightSpot = discHeight;

                //get all the discs to draw
                reverseDiscStack = new Stack<Integer>();
            synchronized (solver.getStackC())
            {
                for (int discNumb : solver.getStackC())
                {
                    reverseDiscStack.push(discNumb);
                }
            }
                //for each disc
                for (int discNumb : reverseDiscStack)
                {
                    //generate proper color for each disc
                    g.setColor(generateDiskColor(discNumb));
                    //draw disc
                    int discSize = (int) (maxDiscWidth * ((double) discNumb / solver.getDiscs()));
                    g.fillRect(thirdPoleLocation - discSize / 2, topBase - heightSpot, discSize, discHeight);
                    heightSpot += discHeight;
                }


                //The end!
                //We:
                // - reset the canvas
                // - drew the base and the poles
                // - drew all the discs
                //That's all!
        }//end of paint

        //given a disc number, what color should it be
        //(chooses from 7 colors in a repeating pattern)
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
