import java.awt.*;
import javax.swing.*;

public class EmptyShaft extends JFrame{
	private double refElevation; //reference elevation (ft.)
	private double tcDiameter; //temporary casing inner diameter (in.)
	private double tcLength; //length of temporary casing (ft.)
	private double tsLength; //total shaft length (ft.)
	private double shaftDiameter; //TEMPORARY VARIABLE, will calculate this later on based on volume
	
	public EmptyShaft(double refElevation, double tcDiameter, double tcLength, double tsLength, double shaftDiameter) { //constructor
		super("Empty Drilled Shaft"); //Titles the drawing window
        getContentPane().setBackground(Color.WHITE); //Makes window background white
        setSize(1000, 1000); //Makes drawing window 1000x1000 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finishes running program when the drawing window is closed
        setLocationRelativeTo(null);
        
		this.refElevation = refElevation; //When DrilledShaft object is created these values have to be filled
		this.tcDiameter = tcDiameter;
		this.tcLength = tcLength;
		this.tsLength = tsLength;
		this.shaftDiameter = shaftDiameter;
	}
	
	public double d2vCE(double diameter) { //returns diameter to volume coefficient based on input diameter
		return Math.PI/3 * Math.pow(diameter/72, 2); //diameter (in.), CE (yd^3/ft)
	}
	
	void drawDiagram(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //Graphics object used to set colors and draw the actual rectangle
        Stroke tempCasing = new BasicStroke(6f); //Creates a BasicStroke object for the temporary casing
        g2d.setColor(Color.RED); //Sets drawing color to red
        g2d.setStroke(tempCasing); //Sets the stroke to draw the temporary casing
        g2d.drawRect(500 - (int) (tcDiameter*1.5), 50, (int) (tcDiameter*3), (int) (tcLength*36)); //Draws the temporary casing at (x, y, width, height)
        
        Stroke shaft = new BasicStroke(6f); //Creates a new BasicStroke object for the shaft
     	g2d.setColor(Color.GREEN); //Sets drawing color to green
    	g2d.setStroke(shaft); //Sets the stroke to draw the shaft
        g2d.drawRect(500 - (int) (shaftDiameter*1.5), 50 + (int) (tcLength*36), (int) (shaftDiameter*3), (int) ((tsLength - tcLength)*36)); //Draws the shaft below the temporary casing
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        drawDiagram(g); //Draws the diagram when called
	}
	
	public static void main(String[] args) throws Exception { //main method
		 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //run method
            	int refEl = Integer.parseInt(JOptionPane.showInputDialog("Enter reference elevation (ft): "));
            	int tcDiam = Integer.parseInt(JOptionPane.showInputDialog("Enter temporary casing diameter (in): "));
            	int tcLen = Integer.parseInt(JOptionPane.showInputDialog("Enter temporary casing length (ft): "));
            	int tsLen = Integer.parseInt(JOptionPane.showInputDialog("Enter total shaft length (ft): "));
            	int sDiam = Integer.parseInt(JOptionPane.showInputDialog("Enter shaft diameter (in): "));
            	EmptyShaft es = new EmptyShaft(refEl, tcDiam, tcLen, tsLen, sDiam); //Creates object of class
            	es.setVisible(true); //Shows the final drawing
            }
        });
	}
}