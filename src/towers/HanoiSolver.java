package towers;

import java.util.Stack;

public class HanoiSolver
{
    private Stack<Integer> stackA;
    private Stack<Integer> stackB;
    private Stack<Integer> stackC;
    private boolean isVisual;

    //constructor
    public HanoiSolver(int discs, boolean isVisual)
    {
        stackA = new Stack<Integer>();
        stackB = new Stack<Integer>();
        stackC = new Stack<Integer>();
        this.isVisual = isVisual;

        for(int i = discs; i > 0; i--)
        {
            stackA.push(i);
        }
    }//end of constructor

    //solves a tower of hanoi
    public void solve(int discs, Peg from, Peg to, Peg hold)
    {
        if (discs == 1)
        {
            System.out.println(from + " ----> " + to);
            move(from,to);
        }
        else//if there are multiple disks
        {
            //move everything but the last to the holding peg
            solve(discs-1,from,hold,to);
            //move the last to the destination peg
            solve(1,from,to,hold);
            //move everything to the last peg
            solve(discs-1,hold,to,from);
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
    public void setVisuality(boolean newVisuality)
    {
        isVisual = newVisuality;
    }//end of setVisuality

    //draws the current state of a tower of hanoi
    public void draw()
    {

    }//end of draw


    //clears the console (makes it blank, as if the program had just started)
    public void clearOut()
    {
        System.out.print("\033[H\033[2J");
    }//end of clearOut
}
