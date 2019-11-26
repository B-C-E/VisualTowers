package towers;

//IMPORTS HERE

import java.time.format.DateTimeFormatter;//these are non essential example imports
import java.time.LocalDateTime;          // they are only used by one example method (subSelection)

//This is a utility class I (B-C-E) made for drivers.
//It is designed to be copied and edited as per a users desires,
//but does have a few handy methods
//Please use in conjunction with the GetInput class
public class BasicDriver
{

    public static void main(String args[])
    {
        //VARIABLES
        boolean b_readyToExit = false;


        //INTRO
        System.out.println("Hello and welcome to the Towers of Hanoi. I present you two options:");

        //LOOP
        while (!b_readyToExit)
        {
            //SELECTIONS
            System.out.println();
            System.out.println("Would you like to:");


            System.out.println("[s] Generate a solution for a number of disks (in boring old text)");
            System.out.println("[v] Enter Visual Mode...");
            System.out.println("[e] Exit");

            //GET INPUT
            char c_input = GetInput.get('s', 'v', 'e');

            //DO ACTION
            switch (c_input)
            {
                //OPTION s - view solutions
                case 's':
                    solveForNumber();
                    break;

                //OPTION v - visual simulation
                case 'v':
                    System.out.println("Exiting text mode... \nLoading...");
                    visualize();
                    return;

                //OPTION E - EXIT
                case 'e':
                    b_readyToExit = true;
                    break;

                //ERROR
                default:
                    throw new IndexOutOfBoundsException("User selected a valid Option with no Implementation");
            }//END OF SWITCH

        }//END OF DRIVER LOOP

        //EXIT MESSAGE
        System.out.println("Have a nice day!");

    }//END OF MAIN


    //Displays the correct solution to a game of Hanoi
    public static void solveForNumber()
    {
        System.out.println("What number of disks would you like to solve for?");
        int discs = GetInput.getRangeInt(1, Integer.MAX_VALUE);
        HanoiSolver solver = new HanoiSolver(discs);
        System.out.println("The correct solution is:");
        solver.solve(discs, Peg.A, Peg.C, Peg.B);

        System.out.println();

        int calculatedNumberOfMoves = (int) (Math.pow(2,discs) - 1);//for n discs, number of moves is always (2^n) - 1

        if (calculatedNumberOfMoves == 1)
        {
            System.out.println("Of course, one disc takes only one move.");
        } else
        {
            System.out.println("That took " + calculatedNumberOfMoves + " moves.");
        }

    }//end of solveorNumber

    //Visualizes a game of hanoi
    public static void visualize()
    {
                        System.out.println("Generating visuals... (Will open in a new window. If it does not open, your IDE" +
                     " may be blocking the opening of new windows)");
             VisualHanoiSolver visHan = new VisualHanoiSolver(4,-1);

             //Since we have said -1 for width and height, it will attempt to autodetermine a good size
             Visualizer myVisuals = new Visualizer(-1,-1,"Visual Hanoi Solver",visHan);
             visHan.setVisuals(myVisuals);

    }//end of subLoop

}//END OF BASICDRIVER
