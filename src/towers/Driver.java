package towers;

public class Driver
{
    public static void main(String args[])
    {
        HanoiSolver han = new HanoiSolver(15);
        Visualizer myVisuals = new Visualizer(400,400,"Cat",han);
        han.setVisuality(true,myVisuals);
        han.solve(15,Peg.A,Peg.C,Peg.B);
    }

}
