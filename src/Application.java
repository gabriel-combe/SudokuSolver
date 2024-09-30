import java.io.IOException;

public class Application
{
    final static int DELAY = 1;
    
    public static void main(String[] args) throws IOException
    {
        Sudoku sudoku = new Sudoku("Sudoku", 50, "boards/sudoku-3-difficile-1.txt");
        Backtracking backtracking = new Backtracking(sudoku.cells);

        sudoku.displayGridImage();
        // backtracking.solveFull();
        
        // Main loop for the cellular automata
        while(!backtracking.solveFinish){
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            
            backtracking.solveStep();
            sudoku.cells = backtracking.grid;

            sudoku.updateGridImage();
            sudoku.displayGridImage();
        }
    }

    
}
