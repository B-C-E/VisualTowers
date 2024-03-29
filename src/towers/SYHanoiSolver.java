package towers;

//Can be used to solve a game of Hanoi with 3 towers and any number of pegs.
//This class will only display HOW to solve a game, but won't actually do much
//(The game is as follows: there are three pegs, with a number of discs on the first one.
// Each disk is smaller than the disc beneath it, IE:
//  0
// 000
//00000
//You cannot place a disk on top of one smaller than it, and can only move one at a time. Move them all to the third peg)
public class SYHanoiSolver
{
    private int discs;

    //constructor
    public SYHanoiSolver(int discs)
    {
        this.discs = discs;
    }//end of constructor

    //solves a tower of hanoi. You must tell it the number of discs to move,
    //the Peg that the discs are on, the peg that they are to be moved to, and the extra holding peg
    public void solve(int toMove, SYPeg from, SYPeg to, SYPeg hold)
    {
        //if there is but one disc
        if (toMove == 1)
        {
            System.out.println(from + " ----> " + to); //simply move the disc
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

}//end of HanoiSolver
