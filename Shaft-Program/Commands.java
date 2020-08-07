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
	public static double currentEl;
	public static double previousDepth;
	public static double previousEl;
	public static double vPlaced;
	public static int truckNumber;
	public static double[] truckVolumes = new double[6];
	public static double[] depthRecords = new double[6];
	public static final int ft2pix = 36;
	public static final int in2pix = 3;
	public static final int hOffset = 50;
	public static final int vOffset = 50;
	public static final int rOffset = 300;
	public static final int textOffset = 25;
	public static final double volumeThreshold = 0.5;
	
	static class Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			FillShaft fs = new FillShaft();
        	fs.setVisible(true);
		}
	}
	
	public static boolean input2ErrorChecker(double vol, double depth) {
		boolean error = false;
		double prevTotalVolume = 0;
		for(int i = 0; i<truckNumber; i++) {
			prevTotalVolume+=truckVolumes[i];
		}
		double totalVol = d2vCE(tcDiameter) * tcLength + d2vCE(shaftDiameter) * (tsLength - tcLength);
		if(vol>totalVol-prevTotalVolume+volumeThreshold) {
			error = true;
			JOptionPane.showMessageDialog(new JFrame(), "Volume input will cause overflow. Please enter again.", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}
		if(depth>pDepth()) {
			error = true;
			JOptionPane.showMessageDialog(new JFrame(), "Entered depth is larger than previously entered depth(s). Please enter again.", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}
		return error;
	}
	
	public static boolean inputErrorChecker(double refEl, double tcDiam, double tcLen, double tsLen, double sDiam) {
		boolean error = false;
		if(tcDiam<sDiam) {
			error = true;
			JOptionPane.showMessageDialog(new JFrame(), "Invalid shaft/casing diameter entered.", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}
		if(tcLen>=tsLen) {
			error = true;
			JOptionPane.showMessageDialog(new JFrame(), "Invalid shaft/casing length entered.", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}
		return error;
	}
	
	public static String round2Digits(double value) {
		double newValue = Math.round(value*100.0)/100.0;
		return new Double(newValue).toString();
	}
	
	public static boolean volumeIssueChecker() {
		boolean noIssue = currentDepth>=(pDepth()-vol2Length());
		if(!noIssue) {
			JOptionPane.showMessageDialog(new JFrame(), "There is a volume issue in the shaft.", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}
		return noIssue;
	}
	
	public static double pDepth() {
		if(truckNumber <= 0) {
			return tsLength;
		}
		return depthRecords[truckNumber-1];//trucknumber means after volume is poured
	}
	
	public static double d2vCE(double diameter) { //returns diameter to volume coefficient based on input diameter
		return Math.PI/3 * Math.pow(diameter/72, 2); //diameter (in.), CE (yd^3/ft)
	}
	
	public static double vol2Length() { //ONLY CHANGE IN LENGTH
		double shaftVolume = d2vCE(shaftDiameter)*(tsLength-tcLength);
		double totalVolume = 0;
		for(int i = 0; i<=truckNumber; i++) {
			totalVolume+=truckVolumes[i];
		}
		double prevTotalVolume = 0;
		for(int i = 0; i<truckNumber; i++) {
			prevTotalVolume+=truckVolumes[i];
		}
		System.out.println("Total volume: " + totalVolume);
		if(totalVolume<=shaftVolume) {//Fix to account for multiple pouring
			System.out.println("Less than shaft");
			return truckVolumes[truckNumber]/d2vCE(shaftDiameter);
		}
		else if(prevTotalVolume<shaftVolume){
			System.out.println("More than shaft, but before less than shaft");
			return (shaftVolume-prevTotalVolume)/d2vCE(shaftDiameter) + (totalVolume-shaftVolume)/d2vCE(tcDiameter); 
		}
		else {
			System.out.println("More than shaft, and before more than shaft");
			return truckVolumes[truckNumber]/d2vCE(tcDiameter);
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
			g2d.fillRect(0, vOffset + (int) (refElevation*ft2pix), hOffset + (int) (tcDiameter*in2pix) + rOffset, (int) ((tsLength-refElevation)*ft2pix) + 20);
		}
		else { //fix for little bit less than 0
			g2d.fillRect(0, 0, hOffset + (int) (tcDiameter*in2pix) + rOffset, vOffset + (int) (tsLength*ft2pix) + 20);
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
	    	g2d.drawString(round2Digits(refElevation + i), 10, vOffset + (int) (i*-1*ft2pix)); //Displays reference elevation
		}
		
		g2d.setFont(new Font("Serif", Font.BOLD, 12)); //Font settings
    	g2d.drawString(round2Digits(refElevation), 10, vOffset); //Displays reference elevation
    	g2d.drawString(round2Digits(refElevation - tcLength), 10, (int) (vOffset + tcLength * ft2pix)); //Displays elevation at bottom of casing
    	g2d.drawString(round2Digits(refElevation - tsLength), 10, (int) (vOffset + tsLength * ft2pix)); //Displays elevations at bottom of shaft
	}
	
	public static void drawLabels(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
    	g2d.drawRect((int) (hOffset + tcDiameter * in2pix) + 15, vOffset - 10, rOffset - 30, 180); //Draws the shaft below the temporary casing
        g2d.setColor(Color.WHITE);
    	g2d.fillRect((int) (hOffset + tcDiameter * in2pix) + 15, vOffset - 10, rOffset - 30, 180); //Draws the shaft below the temporary casing
		g2d.setColor(Color.BLACK);
    	g2d.setFont(new Font("Serif", Font.PLAIN, 18)); //Font settings
    	g2d.drawString("Casing Inner Diameter (in): " + round2Digits(tcDiameter), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10);
    	g2d.drawString("Volume Coefficient (cy/ft): " + round2Digits(d2vCE(tcDiameter)), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + textOffset);
    	g2d.drawString("Theoretical Volume (cy): " + round2Digits(d2vCE(tcDiameter) * tcLength), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 2*textOffset);
    	g2d.drawString("Shaft Diameter (in): " + round2Digits(shaftDiameter), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 3*textOffset);
    	g2d.drawString("Volume Coefficient (cy/ft): " + round2Digits(d2vCE(shaftDiameter)), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 4*textOffset);
    	g2d.drawString("Theoretical Volume (cy): " + round2Digits(d2vCE(shaftDiameter) * (tsLength - tcLength)), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 5*textOffset);
    	g2d.drawString("Total Volume (cy): " + round2Digits(d2vCE(tcDiameter) * tcLength + d2vCE(shaftDiameter) * (tsLength - tcLength)), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 6*textOffset); //Displays all requested details
	}
	
	public static void writeValues(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.drawRect((int) (hOffset + tcDiameter * in2pix) + 15, vOffset + 215, rOffset - 30, 80); //Draws the shaft below the temporary casing
        g2d.setColor(Color.WHITE);
    	g2d.fillRect((int) (hOffset + tcDiameter * in2pix) + 15, vOffset + 215, rOffset - 30, 80); //Draws the shaft below the temporary casing
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.PLAIN, 18)); //Font settings
    	g2d.drawString("Length Poured (ft.): " + round2Digits(vol2Length()), (int) (hOffset + 30 + tcDiameter * in2pix), vOffset + 10 + 9*textOffset);
    	g2d.drawString("Theoretical Volume Poured (cy): ", (int) ((hOffset + 30 + tcDiameter * in2pix)), vOffset + 10 + 10*textOffset);
    	//Update for multiple use
    	if(volumeIssueChecker()){
        	g2d.drawString("Acceptable: Yes",(int) ((hOffset + 30 + tcDiameter * in2pix)), vOffset + 10 + 11*textOffset);
    	}
    	else {
        	g2d.drawString("Acceptable: No",(int) ((hOffset + 30 + tcDiameter * in2pix)), vOffset + 10 + 11*textOffset);
    	}
	}
	
}