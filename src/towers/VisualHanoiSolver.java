package towers;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VisualHanoiSolver
{
    private Stack<Integer> stackA;
    private Stack<Integer> stackB;
    private Stack<Integer> stackC;
    private volatile AtomicBoolean selfDestruct;//is it time to reset?
    private boolean solvingActively; //are we solving right now?
    private Integer discs;//not an int; an integer
    private Visualizer visuals = null;
    private AtomicInteger frameWait; // how long to wait between frames

    //constructor
    public VisualHanoiSolver(int discs, int frameWait)
    {
        stackA = new Stack<Integer>();
        stackB = new Stack<Integer>();
        stackC = new Stack<Integer>();

        this.discs = discs;
        this.selfDestruct = new AtomicBoolean(false);
        this.solvingActively = false;

        this.frameWait = new AtomicInteger(frameWait);

        for (int i = discs; i > 0; i--)
        {
            stackA.push(i);
        }
    }//end of constructor

    public void changeSolver(int newDiscs)
    {
        setSelfDestruct(true);
        setDiscs(newDiscs);

        setStackB(new Stack<Integer>());
        setStackC(new Stack<Integer>());

        setFrameWait(-1);
        Stack temp = new Stack<Integer>();
        for (int i = newDiscs; i > 0; i--)
        {
            temp.push(i);
        }

        setStackA(temp);
        setSolvingActively(false);
    }

    public boolean isSolvingActively()
    {
        return solvingActively;
    }

    public void setSolvingActively(boolean solvingActively)
    {
        this.solvingActively = solvingActively;
    }

    //ACCESSORS
    //   |
    //   V
    public Stack<Integer> getStackA()
    {
        return stackA;
    }

    public void setStackA(Stack<Integer> stackA)
    {
        synchronized (stackA)
        {
            this.stackA = stackA;
        }
    }

    public Stack<Integer> getStackB()
    {
        return stackB;
    }

    public void setStackB(Stack<Integer> stackB)
    {
        synchronized (stackB)
        {
            this.stackB = stackB;
        }
    }

    public Stack<Integer> getStackC()
    {
        return stackC;
    }

    //
    //
    //End of Accessors

    public void setStackC(Stack<Integer> stackC)
    {
        synchronized (stackC)
        {
            this.stackC = stackC;
        }
    }

    public int getDiscs()
    {
        return discs;
    }

    public void setDiscs(int discs)
    {
        synchronized (this.discs)
        {
            this.discs = discs;
        }
    }

    //sets if the stack is visual or not
    public void setVisuals(Visualizer newVisuals)
    {
        visuals = newVisuals;
    }//end of setVisuality

    public AtomicInteger getFrameWait()
    {
        return frameWait;
    }

    public void setFrameWait(int frameWait)
    {
        synchronized (this.frameWait)
        {
            this.frameWait = new AtomicInteger(frameWait);
        }
    }

    public void setFrameWait(AtomicInteger frameWait)
    {
        synchronized (frameWait)
        {
            this.frameWait = frameWait;
        }
    }

    public void setSelfDestruct(boolean selfDestruct)
    {
        synchronized (this.selfDestruct)
        {
            this.selfDestruct = new AtomicBoolean(selfDestruct);
        }
    }

    //solves a tower of hanoi. You must tell it the number of discs to move,
    //the Peg that the discs are on, the peg that they are to be moved to, and the extra holding peg
    public void solve(int toMove, Peg from, Peg to, Peg hold)
    {
        if (selfDestruct.get())//if we are canceling this solve
        {
            return;
        }


        while (frameWait.get() == -1)//if we are paused
        {
            //do nothing and loop forever
        }

        //if there is just one disc to move
        if (toMove == 1)
        {

            //if this solver is not solving at max speed,
            //wait a little bit between frames
            if (frameWait.get() > 0)
            {
                try
                {
                    Thread.sleep(frameWait.get());
                } catch (Exception e)
                {
                }
            }
            //end of waiting between frames

            move(from, to);
            visuals.getPanel().repaint();
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
