package towers;

//Can be used to solve a game of Hanoi with 3 towers and any number of pegs.
//(The game is as follows: there are three pegs, with a number of discs on the first one.
// Each disk is smaller than the disc beneath it, IE:
//  0
// 000
//00000
//You cannot place a disk on top of one smaller than it, and can only move one at a time. Move them all to the third peg)
public class HanoiSolver
{
    private int discs;

    //constructor
    public HanoiSolver(int discs)
    {
        this.discs = discs;
    }//end of constructor

    //Accessor
    public int getDiscs()
    {
        return discs;
    }//end of getDiscs

    //solves a tower of hanoi. You must tell it the number of discs to move,
    //the Peg that the discs are on, the peg that they are to be moved to, and the extra holding peg
    public void solve(int toMove, Peg from, Peg to, Peg hold)
    {
        //if there is but one disc
        if (toMove == 1)
        {
            System.out.println(from + " ----> " + to); //simply move the disc
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

}//end of HanoiSolver
