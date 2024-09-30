import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Sudoku {
    protected int size, scale, borderSize, currentGridRow;

    private BufferedImage scaledImage;
    private Font font;

    private InputStreamReader isr;
    private BufferedReader br;
    
    private String windowName;
    
    JFrame imageFrame;
    ImagePanel imagePanel;
    
    protected int[][] cells;
    
    public boolean isFinished = false;
    
    public Sudoku(String name, int scale, String board) throws IOException{
        this.windowName = name;
        this.scale = scale;
        this.borderSize = 4;
        
        this.isr = new InputStreamReader(System.in);
        this.br = new BufferedReader(this.isr);
        
        this.font = new Font("Serif", Font.PLAIN, this.scale);

        this.load(board);

        this.setupPanel();
    }

    // Load a map from an RLE file format
    public void load(String map) throws IOException{
        // Load RLE file
        BufferedReader br = new BufferedReader(new FileReader(map));

        // Read a line from the file
        String line = br.readLine();
        this.size = processSize(line);
        this.size = this.size * this.size;

        this.cells = new int[this.size][this.size];
        this.currentGridRow = 0;

        // Process the file line by line
        while(this.currentGridRow < this.size){
            line = br.readLine();
            
            // Process each grid line
            processGridLine(line);

            this.currentGridRow++;
        }
        br.close();
    }

    private void processGridLine(String line){
        String[] splitLine = line.split(" ");

        for(int i = 0; i < this.size; i++){
            try {
                this.cells[i][currentGridRow] = Integer.parseInt(splitLine[i]);
            } catch (NumberFormatException e) {
                System.out.println("Your Sudoku file has not the right format: " + e);
            }
        }
            
    }

    private static int processSize(String line){
        int size = 0;

        String[] splitSize = line.split(",");

        try {
            size = Integer.parseInt(splitSize[0]);
        } catch (NumberFormatException e) {
            System.out.println("Your Sudoku file has not the right format: " + e);
        }

        return size;
    }

    private void setupPanel(){
        scaledImage = new BufferedImage((this.scale * this.size) + this.borderSize, (this.scale * this.size) + this.borderSize, BufferedImage.TYPE_INT_RGB);
        
        if(imageFrame != null) imageFrame.dispose();
        imageFrame = new JFrame(this.windowName);

        imagePanel = new ImagePanel(scaledImage);

        imageFrame.add(imagePanel);
        imageFrame.setSize((this.scale * this.size) + this.borderSize + 14, (this.scale * this.size) + this.borderSize + 37);
        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageFrame.setVisible(true);
    }

    // Set the grid
    public void setGrid(int[][] newgrid){
        this.size = newgrid.length;
        
        this.setupPanel();

        this.cells = new int[this.size][this.size];
        
        copyGrid(newgrid, this.cells);
        updateGridImage();
    }
    
    // Update the display of the grid
    protected void updateGridImage(){
        
        Graphics2D scaledImageGraphicsContext = scaledImage.createGraphics();

        // Background
        scaledImageGraphicsContext.setColor(Color.WHITE);
        scaledImageGraphicsContext.fillRect(0, 0, this.scale * this.size, this.scale * this.size);
        
        scaledImageGraphicsContext.setColor(Color.BLACK);
        // Draw Bold lines
        for(int space = 0; space <= (int)Math.sqrt(this.size); space++){
            scaledImageGraphicsContext.fillRect((this.scale * (int)Math.sqrt(this.size)) * space, 0, this.borderSize, this.scale * this.size);
            scaledImageGraphicsContext.fillRect(0, (this.scale * (int)Math.sqrt(this.size)) * space, this.scale * this.size, this.borderSize);
        }

        // Draw Thin lines
        for(int space = 0; space <= this.size; space++){
            scaledImageGraphicsContext.fillRect(this.scale * space, 0, (int)(this.borderSize*0.5), this.scale * this.size);
            scaledImageGraphicsContext.fillRect(0, this.scale * space, this.scale * this.size, (int)(this.borderSize*0.5));
        }
        
        // Set text properties
        scaledImageGraphicsContext.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        scaledImageGraphicsContext.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        scaledImageGraphicsContext.setFont(this.font);
        
        // Draw Digits
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(cells[i][j] == 0) continue;
                scaledImageGraphicsContext.drawString(String.valueOf(cells[i][j]), (this.scale * i) + (int)(this.scale * 0.25), (this.scale * j) + (int)(this.scale * 0.85));
            }
        }
        
        scaledImageGraphicsContext.dispose();
    }
    
    public void displayGridImage(){
        imageFrame.validate();
        imageFrame.repaint();
    }
    
    // Copy the source grid in the destination grid
    protected void copyGrid(int[][] source, int[][] destination){
        for(int i = 0; i < this.size; i++)
            System.arraycopy(source[i], 0, destination[i], 0, this.size);
    }
            
    public int getsize() {
        return this.size;
    }
    
    private class ImagePanel extends JPanel
    {
        private BufferedImage image;
    
        public ImagePanel(BufferedImage image)
        {  
            this.image = image;
        }
    
        public void paintComponent(Graphics g)   
        {  
            super.paintComponent(g);
        
            if (image == null) {
                return;
            }
    
            g.drawImage(image, 0, 0, null);
        }
    }
}
