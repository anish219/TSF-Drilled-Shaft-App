import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;

public class Commands extends JFrame {
	public static double refElevation; //reference elevation (ft.)
	public static double tcDiameter; //temporary casing inner diameter (in.)
	public static double tcLength; //length of temporary casing (ft.)
	public static double tsLength; //total shaft length (ft.)
	public static double shaftDiameter; //TEMPORARY VARIABLE, will calculate this later on based on volume
	public static double currentDepth;
	public static double previousDepth;
	public static double vPlaced;
	public static int truckNumber;
	public static double[] truckVolumes = new double[6];
	public static double[] depthRecords = new double[6];
	public static final int ft2pix = 36;
	public static final int in2pix = 3;
	public static final int hOffset = 50;
	public static final int vOffset = 50;
	
	static class Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			FillShaft fs = new FillShaft();
        	fs.setVisible(true);
		}
	}
	
	public static double pDepth() {
		if(truckNumber <= 0) {
			return tsLength;
		}
		return depthRecords[truckNumber-1];
	}
	public static double d2vCE(double diameter) { //returns diameter to volume coefficient based on input diameter
		return Math.PI/3 * Math.pow(diameter/72, 2); //diameter (in.), CE (yd^3/ft)
	}
	
	public static double vol2Length(double volume) { //ONLY CHANGE IN LENGTH
		double shaftVolume = d2vCE(shaftDiameter)*(tsLength-tcLength);
		double totalVolume = 0;
		System.out.println("Total volume: " + totalVolume);
		for(int i = 0; i<=truckNumber; i++) {
			totalVolume+=truckVolumes[i];
		}
		if(totalVolume<=shaftVolume) {//Fix to account for previous pouring
			return totalVolume/d2vCE(shaftDiameter);
		}
		else {
			return shaftVolume/d2vCE(shaftDiameter) + (totalVolume-shaftVolume)/d2vCE(tcDiameter);
		}
	}
	
	public static void fillShaft(Graphics2D g2d, double depth) {
		if(depth >= tcLength) {
			g2d.fillRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (depth*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - depth)*ft2pix)); //Draws the shaft below the temporary casing
		}
		else {
			g2d.fillRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (tcLength*ft2pix) - 5, (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)+5); //Draws the shaft below the temporary casing
			g2d.fillRect(hOffset, vOffset + (int) (depth*ft2pix), (int) (tcDiameter*in2pix), (int) ((tcLength - depth)*ft2pix)); //Draws the shaft below the temporary casing
		}
	}
	
	public static void drawGround(Graphics2D g2d) {
		g2d.setColor(new Color(235,179,128));
		if(refElevation>=0) {
			g2d.fillRect(0, vOffset + (int) (refElevation*ft2pix), hOffset + (int) (tcDiameter*in2pix) + 300, (int) ((tsLength-refElevation)*ft2pix) + 20);
		}
		else { //fix for little bit less than 0
			g2d.fillRect(0, 0, hOffset + (int) (tcDiameter*in2pix) + 300, vOffset + (int) (tsLength*ft2pix) + 20);
		}
	}
	
	public static void drawShaft(Graphics2D g2d) {
		Stroke shaft = new BasicStroke(6f); //Creates a new BasicStroke object for the shaft
        g2d.setColor(Color.BLUE); //Sets drawing color to black
    	g2d.setStroke(shaft); //Sets the stroke to draw the shaft
    	g2d.drawRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
    	
    	Stroke tempCasing = new BasicStroke(6f); //Creates a BasicStroke object for the temporary casing
        g2d.setColor(Color.RED); //Sets drawing color to red
        g2d.setStroke(tempCasing); //Sets the stroke to draw the temporary casing
        g2d.drawRect(hOffset, vOffset, (int) (tcDiameter*in2pix), (int) (tcLength*ft2pix)); //Draws the temporary casing at (x, y, width, height)

        g2d.setColor(Color.WHITE);
        g2d.fillRect(hOffset, vOffset, (int) (tcDiameter*in2pix), (int) (tcLength*ft2pix)); //Draws the temporary casing at (x, y, width, height)
    	g2d.fillRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
	}
	
	public static void drawElevation(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.PLAIN, 12));
		for(double i = refElevation; i>tsLength*-1; i--) {
	    	g2d.drawString(new Double(refElevation + i).toString(), 10, vOffset + (int) (i*-1*ft2pix)); //Displays reference elevation
		}
		
		g2d.setFont(new Font("Serif", Font.BOLD, 12)); //Font settings
    	g2d.drawString(new Double(refElevation).toString(), 10, vOffset); //Displays reference elevation
    	g2d.drawString(new Double(refElevation - tcLength).toString(), 10, (int) (vOffset + tcLength * ft2pix)); //Displays elevation at bottom of casing
    	g2d.drawString(new Double(refElevation - tsLength).toString(), 10, (int) (vOffset + tsLength * ft2pix)); //Displays elevations at bottom of shaft
	}
	
	public static void drawLabels(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.PLAIN, 12)); //Font settings
    	g2d.drawString("Casing Inner Diameter (in): " + new Double(tcDiameter).toString(), (int) (hOffset + 10 + tcDiameter * in2pix), vOffset + 10);
    	g2d.drawString("Volume Coefficient (cy/ft): " + new Double(d2vCE(tcDiameter)).toString(), (int) (hOffset + 10 + tcDiameter * in2pix), vOffset + 25);
    	g2d.drawString("Theoretical Volume (cy): " + new Double(d2vCE(tcDiameter) * tcLength).toString(), (int) (hOffset + 10 + tcDiameter * in2pix), vOffset + 40);
    	g2d.drawString("Shaft Diameter (in): " + new Double(shaftDiameter).toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + 20 + (int) tcLength * ft2pix);
    	g2d.drawString("Volume Coefficient (cy/ft): " + new Double(d2vCE(shaftDiameter)).toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + 35 + (int) tcLength * ft2pix);
    	g2d.drawString("Theoretical Volume (cy): " + new Double(d2vCE(shaftDiameter) * (tsLength - tcLength)).toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + 50 + (int) tcLength * ft2pix);
    	g2d.drawString("Total Volume (cy): " + new Double(d2vCE(tcDiameter) * tcLength + d2vCE(shaftDiameter) * (tsLength - tcLength)).toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + (int) tsLength * ft2pix); //Displays all requested details
	}
	
	public static void writeValues(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.PLAIN, 12)); //Font settings
    	g2d.drawString("Length Poured (ft.): " + new Double(vol2Length(vPlaced)).toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + (int) tsLength * ft2pix - 45);
    	//g2d.drawString("Theoretical Volume Poured (cy): " + new Double().toString(), hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + (int) tsLength * ft2pix - 30);
    	//Update for multiple use
    	if(Commands.currentDepth>=Commands.previousDepth-Commands.vol2Length(Commands.vPlaced)){
        	g2d.drawString("Acceptable: Yes", hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + (int) tsLength * ft2pix - 15);
    	}
    	else {
        	g2d.drawString("Acceptable: No", hOffset + 10 + (int) ((tcDiameter + shaftDiameter)*in2pix/2), vOffset + (int) tsLength * ft2pix - 15);
    	}
	}
	
}