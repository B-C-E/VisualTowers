package towers;

public class Driver
{
    public static void main(String args[])
    {
        HanoiSolver han = new HanoiSolver(12,false);
        han.draw();
        han.solve(12,Peg.A,Peg.C,Peg.B);
    }

}
