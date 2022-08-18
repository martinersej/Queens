import java.util.stream.Stream;
import java.util.Arrays;

/**
 * Denne klasse kan bruges til at finde løsninger til hvor man kan placere sit dronning.
 * 
 * @author Martin Pugdal Pedersen
 * @version 2021-11-01
 */
public class Solver  {
    private int noOfQueens;
    private int[] queens;
    private int noOfSolutions;
    private boolean showSolutions = true;

    /**
     * Her finder den alle løsningerne og udskriver dem.
     * @param noOfQueens  Antallet af dronninger.
     */
    public void findAllSolutions(int noOfQueens) {
        long s = 0;
        if (showSolutions) {
            Stream.generate(() -> "*").limit(75-18).forEach(System.out::print);
            System.out.println("");
            System.out.printf(" Solutions for "+ noOfQueens +" queens:");
            System.out.println("");
            System.out.println("");
            s = System.currentTimeMillis();
        }
        this.noOfQueens = noOfQueens;
        noOfSolutions = 0;
        queens = new int[noOfQueens];
        positionQueens(0);
        if (showSolutions)  {
            long e = System.currentTimeMillis();
            int t = (int)(e-s);
            System.out.println("");
            System.out.println(" A total of "+ noOfSolutions +" solutions were found in "+ t +" ms");
            Stream.generate(() -> "*").limit(75-18).forEach(System.out::print);
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Et stykke kode for hjælpe at tjekke hvad max queens er hvor vi kan finde alle løsningerne på 3 minutter.
     */
    public static void maxQueens() {
        Solver sr = new Solver();
        sr.setShowSolutions(false);
        long s = 0;
        long e = 0;
        int i = 1;
        while (e - s < 1000*60*3) {
            s = System.currentTimeMillis();
            sr.findAllSolutions(i);
            e = System.currentTimeMillis();
            System.out.println(i);
            i++;
        }
        System.out.println((i-1) + " queens is the max where we can found all solutions.");
    }
    
    /**
     * Tjekker om dronningen kan stå på denne colon eller ej og udskriver løsningen hvis row er det samme som antallet af dronninger - 1.
     * @param row  Der hvor dronningen står (x,).
     */
    private void positionQueens(int row)  {
        for (int col = 0; col < noOfQueens; col++) {
            if (legal(row, col)) {
                queens[row] = col;
                if (row == noOfQueens - 1) {
                    noOfSolutions++;
                    if (showSolutions) {
                        printSolution();
                    }
                }
                positionQueens(row + 1);
            }
        }
    }

    /**
     * Tjekker om det er muligt at placere sit dronning ved (row,col).
     * @return Om det er legal at placere ens dronning der.
     */
    private boolean legal(int row, int col) {
        //for løkke til at tjekke nedaf.
        for (int i = 1; i <= row; i++) {
            if (queens[row - i] == col) {
                return false;
            }
        }
        //for løkke til at tjekke diagonal linje, hvor den tjekker den hen af venstre diagonal samtidligt nedaf.
        for (int i = 1; i <= row; i++) {
            if (queens[row - i] == col - i) {
                return false;
            }
        }
        //for løkke til at tjekke diagonal linje, hvor den tjekker den hen af højre diagonal samtidligt nedaf.
        for (int i = 1; i <= row; i++) {
            if (queens[row - i] == col + i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Udskriver løsningerne som skakfelter.
     */
    private void printSolution() {
        for (int i = 0; i < noOfQueens; i++) {
            System.out.printf("%3s ", convert(i, queens[i]));
        }
        System.out.println("");
    }

    /**
     * Converter vores row til et bogstav og laver det om til skakfelt.
     * @param row  Dronningens position (x,) står på.
     * @param col  Dronningens position (,x) står på.
     * @return  Feltet.
     */
    private String convert(int row, int col) {
        return (char) (97 + row) + Integer.toString(col + 1);
    }
    
    /**
     * Gør så om der skal vises løsningerne nu eller ej.
     * @param showSolution  Her aktiverer eller dekativerer man at udskrive løsningerne.
     */
    public void setShowSolutions(boolean showSolutions) {
        this.showSolutions = showSolutions;
    }
    
    /**
     * Finder antallet af løsninger ud fra den mindste og maksimum værdi der skal findes og de udskrives.
     * @param min  Mindste værdi for dronninger der skal findes.
     * @param max  Maksimum værdi for dronninger der skal findes.
     */
    public void findNoOfSolutions(int min, int max) {
        setShowSolutions(false);
        Stream.generate(() -> "*").limit(75-18).forEach(System.out::print);
        System.out.println();
        System.out.println("  Queens      Solutions      Time(ms)      Solutions/ms");
        for (int i = min; i <= max; i++) {
            long s = System.currentTimeMillis();
            findAllSolutions(i);
            long e = System.currentTimeMillis();
            int t = Math.max(1, (int)(e-s));
            System.out.printf("%7d %14d %13d %17d", noOfQueens, noOfSolutions, t, noOfSolutions/t);
            System.out.println();
        }
        setShowSolutions(true);
        Stream.generate(() -> "*").limit(75-18).forEach(System.out::print);
    }

    /**
     * Her udskrives der
     * @param n  Antallet af dronninger.
     * @param pos  Position på skakbrækket, hvor dronningerne er placeret.
     */
    public void testLegal(int n, int... pos) {
        noOfQueens = n;
        queens = Arrays.copyOf(pos, n);
        System.out.print(n + "x" + n + " with queens in: " + Arrays.toString(pos) + " => Legal positions: ");
        for(int i=0; i < n; i++) {
            if (legal(pos.length,i)) { 
                System.out.print(i + " ");
            };
        }
        System.out.println();
    }
}