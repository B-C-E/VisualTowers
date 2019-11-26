package towers;

//This driver starts the towers of Hanoi game
public class BasicDriver
{

    //run this one
    public static void main(String args[])
    {
        //VARIABLES
        boolean b_readyToExit = false;//time to exit?

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
                    return;//entering visual mode will quit text mode

                //OPTION E - EXIT
                case 'e':
                    b_readyToExit = true;
                    break;
            }//END OF SWITCH

        }//END OF DRIVER LOOP

        //EXIT MESSAGE
        System.out.println("Have a nice day!");

    }//END OF MAIN


    //Displays the correct solution to a game of Hanoi
    public static void solveForNumber()
    {
        System.out.println("What number of disks would you like to solve for?");
        int discs = GetInput.getRangeInt(1, Integer.MAX_VALUE);//Integer.max_value is very large. Don't try that one
        HanoiSolver solver = new HanoiSolver(discs);
        System.out.println("The correct solution is:");
        solver.solve(discs, Peg.A, Peg.C, Peg.B);

        System.out.println();

        int calculatedNumberOfMoves = (int) (Math.pow(2, discs) - 1);//for n discs, number of moves is always (2^n) - 1

        if (calculatedNumberOfMoves == 1)
        {
            System.out.println("Of course, one disc takes only one move.");
        } else
        {
            System.out.println("That took " + calculatedNumberOfMoves + " moves.");
        }

    }//end of solve for Number

    //starts a simulation of a towers of hanoi game
    public static void visualize()
    {
        System.out.println("Generating visuals... (Will open in a new window. If it does not open, your IDE" +
                " may be blocking the opening of new windows)");


        //make a visual solver, which does the thinking about where to move discs
        VisualHanoiSolver visHan = new VisualHanoiSolver(4, -1);

        //makes a visualizer, which creates the frame, draws to it, and edits the solver
        //Since we have said -1 for width and height, it will attempt to auto-determine a good size
        Visualizer myVisuals = new Visualizer(-1, -1, "Visual Hanoi Solver", visHan);

        //the solver needs access to the visualizer for things to work
        visHan.setVisuals(myVisuals);

    }//end of subLoop

}//END OF BASICDRIVER
