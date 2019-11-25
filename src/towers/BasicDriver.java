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
        System.out.println("Hello and welcome to the Towers of Hanoi");

        //LOOP
        while (!b_readyToExit)
        {
            //SELECTIONS
            System.out.println();
            System.out.println("Would you like to:");


            System.out.println("[s] Generate a solution for a number of disks");
            System.out.println("[v] View a visual simulation of a Towers of Hanoi Game");
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
                    visualize();
                    break;

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
            System.out.println("How many discs shall we simulate?");
             int discs = GetInput.getRangeInt(1, Integer.MAX_VALUE);

             System.out.println("\nWould you like to move discs as fast as the computer can process,");
             System.out.println("which is fun to watch,");
             System.out.println("or move discs with a little bit of time between movements,");
             System.out.println("which allows you to better understand what is going on?");
             System.out.println("[i] Infinite Speed");
             System.out.println("[l] Limited Speed");

             int frameWait;//how long to wait between frames, in milliseconds

             if (GetInput.get('i','l') == 'i')//if they want infinite speed
             {
                 frameWait = 0; //wait 0 milliseconds between frames
             }
             else//if they want limited speed
             {
                 System.out.println("\nHow many seconds should we wait between disc movements?");
                 System.out.println("(A number between 0.2 and 1 seconds is recommended)");
                 frameWait = (int)(GetInput.getRangeDouble(0.0,4.0) * 1000);//converted to milliseconds
                 System.out.println("If a wait time of " + frameWait/1000.0 + " turns out to be too long, simply close" +
                         " the application and try again." );
             }

             System.out.println("Generating visuals... (Will open in a new window. If it does not open, your IDE" +
                     " may be blocking the opening of new windows)");
             VisualHanoiSolver visHan = new VisualHanoiSolver(discs,frameWait);

             //Since we have said -1 for width and height, it will attempt to autodetermine a good size
             Visualizer myVisuals = new Visualizer(-1,-1,"Visual Hanoi Solver",visHan);
             visHan.setVisuals(myVisuals);
             visHan.solve(discs,Peg.A,Peg.C,Peg.B);

    }//end of subLoop

}//END OF BASICDRIVER
