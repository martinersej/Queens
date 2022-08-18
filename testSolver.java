
/**
 * Lav en beskrivelse af klassen testSolver her.
 * 
 * @author Martin Pugdal Pedersen
 * @version 2021-11-01
 */
public class testSolver
{
    /**
     * Tester om det passer med det billede fra opgaven.
     */
    public static void legalTest() {
        Solver s = new Solver();
        s.findAllSolutions(1);
        s.findAllSolutions(2);
        s.findAllSolutions(6);
        s.findNoOfSolutions(1, 12);
    }
}
