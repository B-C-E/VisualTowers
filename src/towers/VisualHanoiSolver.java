package towers;

import java.io.IOException;
import java.util.Stack;

public class VisualHanoiSolver
{
    private Stack<Integer> stackA;
    private Stack<Integer> stackB;
    private Stack<Integer> stackC;
    private int discs;

    public void setFrameWait(int frameWait)
    {
        synchronized ((Integer)frameWait)
        {
            this.frameWait = frameWait;
        }
    }

    private Visualizer visuals = null;
    private Integer frameWait; // how long tow wait between frames

    //constructor
    public VisualHanoiSolver(int discs, int frameWait)
    {
        stackA = new Stack<Integer>();
        stackB = new Stack<Integer>();
        stackC = new Stack<Integer>();

        this.discs = discs;

        this.frameWait = frameWait;

        for (int i = discs; i > 0; i--)
        {
            stackA.push(i);
        }
    }//end of constructor


    //ACCESSORS
    //   |
    //   V
    public Stack<Integer> getStackA()
    {
        return stackA;
    }

    public Stack<Integer> getStackB()
    {
        return stackB;
    }

    public Stack<Integer> getStackC()
    {
        return stackC;
    }

    public int getDiscs()
    {
        return discs;
    }

    //
    //
    //End of Accessors

    //sets if the stack is visual or not
    public void setVisuals(Visualizer newVisuals)
    {
        visuals = newVisuals;
    }//end of setVisuality

    //solves a tower of hanoi. You must tell it the number of discs to move,
    //the Peg that the discs are on, the peg that they are to be moved to, and the extra holding peg
    public void solve(int toMove, Peg from, Peg to, Peg hold)
    {
        //if there is just one disc to move
        if (toMove == 1)
        {

            //if this solver is not solving at max speed,
            //wait a little bit between frames
            if (frameWait > 0)
            {
                try
                {
                    Thread.sleep(frameWait);
                } catch (Exception e)
                {
                }
            }else if (frameWait == -1)//if we are paused
            {
                try
                {
                    synchronized (frameWait)
                    {
                        frameWait.wait();
                    }
                } catch(Exception e){}
            }
            //end of waiting between frames

            move(from, to);
            visuals.panel.repaint();
        } else//if there are multiple disks
        {
            //move everything but the last to the holding peg
            solve(toMove - 1, from, hold, to);
            //move the last to the destination peg
            solve(1, from, to, hold);
            //move everything to the last peg
            solve(toMove - 1, hold, to, from);
        }
    }//end of solve

    //moves a disc from one peg to another
    public void move(Peg from, Peg to)
    {
        Stack<Integer> fromStack = stackFromPeg(from);
        Stack<Integer> toStack = stackFromPeg(to);
        toStack.push(fromStack.pop());
    }//end of move

    //given a Peg, returns a stack
    public Stack<Integer> stackFromPeg(Peg peg)
    {
        if (peg == Peg.A)
        {
            return stackA;
        }
        if (peg == Peg.B)
        {
            return stackB;
        }
        if (peg == Peg.C)
        {
            return stackC;
        }
        //it should be impossible to get here, but if something has really gone awry...
        throw new IllegalArgumentException("Impossible Peg selected");
    }//end of stackFromPeg


}//end of VisualHanoiSolver
