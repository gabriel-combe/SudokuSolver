import java.util.ArrayList;

public class Backtracking extends Solver {

    private int[] currentCell = {0, 0};
    private int currentDepth = 0;

    private ArrayList<int[]> cellsList = new ArrayList<int[]>();
    private ArrayList<Integer> depthValues = new ArrayList<Integer>();

    public Backtracking(int[][] grid) {
        super(grid);
        this.depthValues.add(0);
    }

    @Override
    public ArrayList<int[]> searchFreeCells() {
        ArrayList<int[]> freecells = new ArrayList<int[]>();

        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                if(this.grid[j][i] != 0) continue;

                freecells.add(new int[]{j, i});
            }
        }

        return freecells;
    }

    @Override
    public int[] searchFreeCell() {

        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                if(this.grid[j][i] != 0) continue;

                return new int[]{j, i};
            }
        }

        return new int[]{-1, -1};
    }

    @Override
    public void solveStep() {

        int[] freecell = this.searchFreeCell();
        
        if(freecell[0] == -1){
            this.solveFinish = true;
            return;
        }

        if(this.depthValues.size() < this.currentDepth+1){
            this.depthValues.add(1);
        } else if(this.depthValues.get(this.currentDepth) >= this.size){
            this.depthValues.remove(this.currentDepth);
            int[] pos = this.cellsList.remove(this.currentDepth);
            this.currentDepth -= 1;
            
            this.grid[pos[0]][pos[1]] = 0;
            return;
        } else {
            this.depthValues.set(this.currentDepth, this.depthValues.get(this.currentDepth)+1);
        }

        if(this.cellsList.size() <= this.currentDepth){
            this.cellsList.add(freecell);
        }

        this.currentCell = this.cellsList.get(this.currentDepth);

        if(!this.valide(this.currentCell[0], this.currentCell[1], this.depthValues.get(this.currentDepth))){
            this.solveStep();
            return;
        }

        this.grid[this.currentCell[0]][this.currentCell[1]] = this.depthValues.get(this.currentDepth);
        this.currentDepth += 1;
    }

    @Override
    public void solveFull() {
        while(!this.solveFinish) {
            this.solveStep();
        }
    }

    
}
