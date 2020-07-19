import java.awt.*;
import javax.swing.*;

public class EmptyShaft extends JFrame {
	private double refElevation; //reference elevation (ft.)
	private double tcDiameter; //temporary casing inner diameter (in.)
	private double tcLength; //length of temporary casing (ft.)
	private double tsLength; //total shaft length (ft.)
	private double shaftDiameter; //TEMPORARY VARIABLE, will calculate this later on based on volume
	private double[] truckVolumes;
	private double vCasing;
	private double vShaft;
	
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
        g2d.setColor(Color.BLUE); //Sets drawing color to red
        g2d.setStroke(tempCasing); //Sets the stroke to draw the temporary casing
        //g2d.drawRect(500 - (int) (tcDiameter*1.5), 50, (int) (tcDiameter*3), (int) (tcLength*36)); //Draws the temporary casing at (x, y, width, height)
        g2d.drawRect(50, 50, (int) (tcDiameter*3), (int) (tcLength*36)); //Draws the temporary casing at (x, y, width, height)
        
        Stroke shaft = new BasicStroke(6f); //Creates a new BasicStroke object for the shaft
        g2d.setColor(Color.BLACK); //Sets drawing color to black
    	g2d.setStroke(shaft); //Sets the stroke to draw the shaft
        //g2d.drawRect(500 - (int) (shaftDiameter*1.5), 50 + (int) (tcLength*36), (int) (shaftDiameter*3), (int) ((tsLength - tcLength)*36)); //Draws the shaft below the temporary casing
    	g2d.drawRect(50 + (int) ((tcDiameter - shaftDiameter)*1.5), 50 + (int) (tcLength*36), (int) (shaftDiameter*3), (int) ((tsLength - tcLength)*36)); //Draws the shaft below the temporary casing
    	
    	g2d.setFont(new Font("Serif", Font.BOLD, 12)); //Font settings
    	g2d.drawString(new Double(refElevation).toString(), 10, 50); //Displays reference elevation
    	g2d.drawString(new Double(refElevation - tcLength).toString(), 10, (int) (50 + tcLength * 36)); //Displays elevation at bottom of casing
    	g2d.drawString(new Double(refElevation - tsLength).toString(), 10, (int) (50 + tsLength * 36)); //Displays elevations at bottom of shaft
    	
    	g2d.setFont(new Font("Serif", Font.PLAIN, 12)); //Font settings
    	g2d.drawString("Casing Inner Diameter (in): " + new Double(tcDiameter).toString(), (int) (60 + tcDiameter * 3), 60);
    	g2d.drawString("Volume Coefficient (cy/ft): " + new Double(d2vCE(tcDiameter)).toString(), (int) (60 + tcDiameter * 3), 75);
    	g2d.drawString("Theoretical Volume (cy): " + new Double(d2vCE(tcDiameter) * tcLength).toString(), (int) (60 + tcDiameter * 3), 90);
    	g2d.drawString("Shaft Diameter (in): " + new Double(shaftDiameter).toString(), 60 + (int) ((tcDiameter + shaftDiameter)*1.5), 70 + (int) tcLength * 36);
    	g2d.drawString("Volume Coefficient (cy/ft): " + new Double(d2vCE(shaftDiameter)).toString(), 60 + (int) ((tcDiameter + shaftDiameter)*1.5), 85 + (int) tcLength * 36);
    	g2d.drawString("Theoretical Volume (cy): " + new Double(d2vCE(shaftDiameter) * (tsLength - tcLength)).toString(), 60 + (int) ((tcDiameter + shaftDiameter)*1.5), 100 + (int) tcLength * 36);
    	g2d.drawString("Total Volume (cy): " + new Double(d2vCE(tcDiameter) * tcLength + d2vCE(shaftDiameter) * (tsLength - tcLength)).toString(), 60 + (int) ((tcDiameter + shaftDiameter)*1.5), 50 + (int) tsLength * 36); //Displays all requested details
    	
	}
	
	public void paint(Graphics g) {
        super.paint(g); //Calls JFrame paint method
        drawDiagram(g); //Draws the diagram when called
	}
	
	public static void main(String[] args) throws Exception { //main method
		 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //run method
            	JTextField refEl = new JTextField(5);
                JTextField tcDiam = new JTextField(5);
                JTextField tcLen = new JTextField(5);
                JTextField tsLen = new JTextField(5);
                JTextField sDiam = new JTextField(5); //Creates textboxes for each variable

                JPanel myPanel = new JPanel(new GridLayout(0,4)); //Allows placement of textboxes in grid
                myPanel.add(new JLabel("Reference Elevation (ft):")); //Displays prompts in panel
                myPanel.add(refEl); //Adds corresponding textbox
                myPanel.add(new JLabel("Temporary Casing Diameter (in):"));
                myPanel.add(tcDiam);
                myPanel.add(new JLabel("Temporary Casing Length (ft):"));
                myPanel.add(tcLen);
                myPanel.add(new JLabel("Total Shaft Length (ft):"));
                myPanel.add(tsLen);
                myPanel.add(new JLabel("Shaft Diameter (in):"));
                myPanel.add(sDiam);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);//Closes popup when OK is pressed
            	
                if (result == JOptionPane.OK_OPTION) {
                	EmptyShaft es = new EmptyShaft(Double.parseDouble(refEl.getText()), Double.parseDouble(tcDiam.getText()), Double.parseDouble(tcLen.getText()), Double.parseDouble(tsLen.getText()), Double.parseDouble(sDiam.getText())); //Creates object of class
                	es.setVisible(true); //Shows the final drawing
                }
            }
        });
	}
}
