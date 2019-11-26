package towers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class SYVisualizer
{
    private HanoiPanel panel;
    private SYVisualHanoiSolver solver;
    private int width;
    private int height;
    private int frameWait;
    private JFrame frame;

    //constructor
    //Needs a width, height, and name (What shall be displayed in the top of the window)
    //and which VisualHanoiSolver will be solving a game of hanoi for it
    public SYVisualizer(int width, int height, String name, SYVisualHanoiSolver solver)
    {
        //if width is given as -1, it will auto-generate a nice size for the width
        if (width == -1)
        {
            //get screen size using the Toolkit class
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = screenSize.width * 3 / 4;
            height = screenSize.height * 3 / 4;
        }//end of autogenerating width and height

        this.width = width;
        this.height = height;

        this.frameWait = 3 + (int) (0.1 * Math.pow(75, 2));//default solving speed. Just a number

        this.frame = new JFrame(name);//the window containing the game
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);//move it on top (later in the code, this is disabled)
        frame.setResizable(true);//Sure, why not resize it
        frame.setVisible(true);
        frame.setLocation(20, 20);//move it over just a little bit, from the top left

        panel = new HanoiPanel();//this is where the game is actually drawn
        frame.getContentPane().add(panel);//add the panel to the frame
        constructSouth();//add the (southern) buttons to the frame

        frame.pack();//pack up the frame
        frame.setAlwaysOnTop(false);//make it so that the frame is not locked on top anymore

        this.solver = solver;//(the SisualHanoiSolver)
    }//end of constructor

    //Accessors!
    //    +
    // Mutators!

    public HanoiPanel getPanel()
    {
        return panel;
    }



    //Creates the buttons on the bottom panel
    private void constructSouth()
    {
        //create a new panel to hold buttons
        JPanel p = new JPanel();

        //slider for discs
        p.add(new JLabel("Discs:"));//label

        //make the disc slider
        final JSlider sliderDiscs = new JSlider(JSlider.HORIZONTAL, 1, 64, 4);
        sliderDiscs.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)//when clicked...
            {
                solver.changeSolver(sliderDiscs.getValue());//change number of discs
                panel.repaint();                           // repaint
            }
        });
        sliderDiscs.setMajorTickSpacing(21);//visual look of the slider
        sliderDiscs.setMinorTickSpacing(7);
        sliderDiscs.setPaintTicks(true);
        sliderDiscs.setPaintLabels(true);

        p.add(sliderDiscs);//add the slider

        //A nice slider for framerate
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 100, 75);
        slider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)//when clicked
            {
                int newWait = 3 + (int) (0.1 * Math.pow(slider.getValue(), 2));//adjusts on a weird exponential scale

                if (solver.isSolvingActively())//if it is already solving
                {
                    solver.setFrameWait(newWait);//update the speed
                }

                //if it has not yet started solving, simply set it so that when it starts it will use the new speed
                frameWait = newWait;//stores last framerate so that we can go back to that rate after pausing
            }
        });
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);

        //add labels, fast, and slow, and the slider between them
        p.add(new JLabel("Fast"));
        p.add(slider);
        p.add(new JLabel("Slow"));

        //end of slider stuff

        //start button
        JButton b1 = new JButton("Start");
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)//when clicked
            {
                //if the solver has not yet started solving, turn it on
                if (!solver.isSolvingActively())
                {
                    solver.setSolvingActively(true);
                    solver.setSelfDestruct(false);//if self destruct happened to be on
                    Thread solveThread = new Thread()
                    {
                        public void run()
                        {
                            try
                            {
                                solver.solve(solver.getDiscs(), SYPeg.A, SYPeg.C, SYPeg.B);
                            } catch (Exception e)
                            {
                            }
                        }
                    };
                    solveThread.start();
                }
                //set the frameWait to the right number
                solver.setFrameWait(frameWait);
            }
        });
        //add the button
        p.add(b1);
        //end of start button

        //Stop button
        JButton b2 = new JButton("Stop");
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)//when clicked
            {
                solver.setFrameWait(-1);//pause it
            }
        });
        p.add(b2);

        //this buttons toggles (on or off) infinite solving speed (as fast as the computer can handle
        JButton b3 = new JButton("Infinite Speed");

        b3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)//when clicked
            {

                //clicking on the infinite speed button, will auto-start the simulation, if it isn't running
                if (!solver.isSolvingActively())
                {
                    //(Clicks the start button)
                    b1.doClick();
                }
                if (solver.getFrameWait().get() == 0)//if currently at infinite speed
                {
                    solver.setFrameWait(frameWait);//turn off infinite speed
                } else//if not yet at infinite speed
                {
                    solver.setFrameWait(0);//turn on infinite speed
                }


            }
        });
        p.add(b3);

        //reset button
        JButton b4 = new JButton("Reset");
        b4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)//when clicked
            {
                solver.changeSolver(sliderDiscs.getValue());
                panel.repaint();
            }
        });
        p.add(b4);

        //add all the buttons to the bottom!
        frame.getContentPane().add(p, BorderLayout.SOUTH);
    }//end of constructSouth

    //internal class for drawing the game
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

            //Width of the pole is 85% the size of the smallest disk that will be on it, or 1/6th the width of the base,
            //whichever is smaller
            int widthPole = Math.min((int)(lengthBase/6.5),(int) ((8.5 / 10) * (lengthBase / 3.2) * (1.0 / solver.getDiscs())));

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
            int discHeight = Math.min((height/4),(height / 2) / solver.getDiscs());
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
            synchronized (solver.getStackB())
            {
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

            //populate pole 3

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
