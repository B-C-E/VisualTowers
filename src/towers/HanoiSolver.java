package towers;

import java.io.IOException;
import java.util.Stack;

public class HanoiSolver
{
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

    private Stack<Integer> stackA;
    private Stack<Integer> stackB;
    private Stack<Integer> stackC;
    private int discs;
    private boolean isVisual = false;
    private Visualizer visuals = null;

    //constructor
    public HanoiSolver(int discs)
    {
        stackA = new Stack<Integer>();
        stackB = new Stack<Integer>();
        stackC = new Stack<Integer>();

        this.discs = discs;

        for(int i = discs; i > 0; i--)
        {
            stackA.push(i);
        }
    }//end of constructor

    //solves a tower of hanoi
    public void solve(int toMove, Peg from, Peg to, Peg hold)
    {
        if (toMove == 1)
        {
            System.out.println(from + " ----> " + to);

            if(isVisual)
            {
                try
                {
                    Thread.sleep(1);
                }catch (Exception e) {}
                visuals.panel.repaint();
            }

            move(from,to);
        }
        else//if there are multiple disks
        {
            //move everything but the last to the holding peg
            solve(toMove-1,from,hold,to);
            //move the last to the destination peg
            solve(1,from,to,hold);
            //move everything to the last peg
            solve(toMove-1,hold,to,from);
        }
    }//end of solve

    //moves a disc from one peg to another
    public void move(Peg from, Peg to)
    {
        Stack<Integer> fromStack = stackFromPeg(from);
        Stack<Integer> toStack = stackFromPeg(to);
        toStack.push(fromStack.pop());
        draw();
    }//end of move

    //given a Peg, returns a stack
    public Stack<Integer> stackFromPeg (Peg peg)
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
        throw new IllegalArgumentException();
    }//end of stackFromPeg


    //sets if the stack is visual or not
    public void setVisuality(boolean newVisuality, Visualizer newVisuals)
    {
        isVisual = newVisuality;
        visuals = newVisuals;
    }//end of setVisuality

    //draws the current state of a tower of hanoi
    public void draw()
    {
        System.out.print("\nA: - ");

        for (int i: stackA)
        {
            System.out.print(i + " ");
        }

        System.out.print("\nB: - ");

        for (int i: stackB)
        {
            System.out.print(i + " ");
        }

        System.out.print("\nC: - ");

        for (int i: stackC)
        {
            System.out.print(i+ " ");
        }
        System.out.println();
        System.out.println();
    }//end of draw


    //clears the console (makes it blank, as if the program had just started)
    public void clearOut(){

    }//end of clearOut
}
