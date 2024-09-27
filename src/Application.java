import java.io.IOException;

public class Application
{
    final static int DELAY = 100;
    
    public static void main(String[] args) throws IOException
    {
        Sudoku sudoku = new Sudoku("Sudoku", 50, "boards/sudoku-3-difficile-1.txt");

        sudoku.displayGridImage();
        
        // Main loop for the cellular automata
        while(!sudoku.isFinished){
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }

            sudoku.updateGridImage();
            sudoku.displayGridImage();
        }
    }

    
}
