public abstract class Solver {

    private int[][] grid;
    private int size, sqrtSize;

    public Solver(int[][] grid){
        this.grid = grid;
        this.size = grid.length;
        this.sqrtSize = (int)Math.sqrt(this.size);
    }

    private int[][] zone(int x, int y){
        int[][] zoneCellsIndex = new int[this.size][2];

        int zoneX = this.sqrtSize * (int)(x/this.sqrtSize);
        int zoneY = this.sqrtSize * (int)(y/this.sqrtSize);

        for(int i = 0; i < this.sqrtSize; i++){
            for(int j = 0; j < this.sqrtSize; j++){
                zoneCellsIndex[i + j*this.sqrtSize][0] = zoneX + i;
                zoneCellsIndex[i + j*this.sqrtSize][1] = zoneY + j;
            }
        }
                
        return zoneCellsIndex;
    }

    private boolean valide(int x, int y, int val){
        for(int i = 0; i < this.size; i++){
            if(i != x && this.grid[i][y] == val) return false;
            if(i != y && this.grid[x][i] == val) return false;
        }

        for(int[] pos : zone(x, y))
            if(pos[0] != x && pos[1] != y && this.grid[pos[0]][pos[1]] == val) return false;

        return true;
    }

    abstract public void solveStep();
    abstract public void solveFull();
}
