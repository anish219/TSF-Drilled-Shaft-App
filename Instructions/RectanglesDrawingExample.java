import java.awt.*;
import javax.swing.*; //Imports necessary packages for the drawing window and drawing tools

public class RectanglesDrawingExample extends JFrame {
    private int height;
    private int width; //Separate variables so that they can be assigned values based on user input
    
 
    public RectanglesDrawingExample() {
        super("3 Stacked Rectangles for Harmon"); //Titles the drawing window
 
        getContentPane().setBackground(Color.WHITE); //Makes window background white
        setSize(1000, 1000); //Makes drawing window 1000x1000 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finishes running program when the drawing window is closed
        setLocationRelativeTo(null);
    }
 
    void drawRectangles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //Graphics object used to set colors and draw the actual rectangle
	Stroke stroke1 = new BasicStroke(6f); //Creates a BasicStroke object for the 1st rectangle
 	g2d.setColor(Color.RED); //Sets drawing color to red
	g2d.setStroke(stroke1); //Sets the stroke to stroke1
	g2d.drawRect(40, 45, width, height); //Draws the rectangle at (x, y, width, height)

	Stroke stroke2 = new BasicStroke(6f); //Creates a new BasicStroke object for the 2nd rectangle
 	g2d.setColor(Color.GREEN); //Sets drawing color to green
	g2d.setStroke(stroke2); //Sets the stroke to stroke2
        g2d.drawRect(40, 50 + height, width, height); //Draws the 2nd rectangle lower than the first

	Stroke stroke3 = new BasicStroke(6f); //Creates a new BasicStroke object for the 3nd rectangle
 	g2d.setColor(Color.BLUE); //Sets drawing color to blue
	g2d.setStroke(stroke3); //Sets the stroke to stroke3
        g2d.drawRect(40, 55 + 2 * height, width, height); //Draws the 3rd rectangle twice as low as the first
 
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g); //Draws the rectangles when called
    }
 
    public static void main(String[] args) throws Exception { //main method
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //run method
		int h = Integer.parseInt(JOptionPane.showInputDialog("Enter desired height: ")); //Prompts user for height and assigns to h
		int w = Integer.parseInt(JOptionPane.showInputDialog("Enter desired width: ")); //Prompts user for width and assigns to w
		RectanglesDrawingExample rde = new RectanglesDrawingExample(); //Creates object of class
		rde.height = h; //Assigns h to height
		rde.width = w; //Assigns w to width
		rde.setVisible(true); //Shows the final drawing
            }
        }
    }
}
