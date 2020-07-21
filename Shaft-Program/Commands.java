import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Commands extends JFrame {
	public static double refElevation; //reference elevation (ft.)
	public static double tcDiameter; //temporary casing inner diameter (in.)
	public static double tcLength; //length of temporary casing (ft.)
	public static double tsLength; //total shaft length (ft.)
	public static double shaftDiameter; //TEMPORARY VARIABLE, will calculate this later on based on volume
	public static double currentDepth;
	public static ArrayList<Double> truckVolumes;
	public static double truckNumber;
	public static final int ft2pix = 36;
	public static final int in2pix = 3;
	public static final int hOffset = 50;
	public static final int vOffset = 50;
	
	public static double d2vCE(double diameter) { //returns diameter to volume coefficient based on input diameter
		return Math.PI/3 * Math.pow(diameter/72, 2); //diameter (in.), CE (yd^3/ft)
	}
	
	public static double vol2Length(double volume, double diameter) {
		return volume/d2vCE(diameter);
	}
	
	public static void drawCasing(Graphics2D g2d) {
		Stroke tempCasing = new BasicStroke(6f); //Creates a BasicStroke object for the temporary casing
        g2d.setColor(Color.RED); //Sets drawing color to red
        g2d.setStroke(tempCasing); //Sets the stroke to draw the temporary casing
        //g2d.drawRect(500 - (int) (tcDiameter*in2pix/2), vOffset, (int) (tcDiameter*in2pix), (int) (tcLength*ft2pix)); //Draws the temporary casing at (x, y, width, height)
        g2d.drawRect(hOffset, vOffset, (int) (tcDiameter*in2pix), (int) (tcLength*ft2pix)); //Draws the temporary casing at (x, y, width, height)
	}
	
	public static void fillShaft(Graphics2D g2d, double depth) {
		Stroke fill = new BasicStroke(6f); //Creates a new BasicStroke object for the shaft
		g2d.setColor(Color.LIGHT_GRAY); //Sets drawing color to black
		g2d.setStroke(fill); //Sets the stroke to draw the shaft
		
		if(depth >= tcLength) {
			//g2d.drawRect(500 - (int) (shaftDiameter*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
			g2d.fillRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (currentDepth*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - currentDepth)*ft2pix)); //Draws the shaft below the temporary casing
		}
		else {
			//g2d.drawRect(500 - (int) (shaftDiameter*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
			g2d.fillRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
			g2d.fillRect(hOffset, vOffset + (int) (currentDepth*ft2pix), (int) (tcDiameter*in2pix), (int) ((tcLength - currentDepth)*ft2pix)); //Draws the shaft below the temporary casing
		}
	}
	
	public static void drawShaft(Graphics2D g2d) {
		Stroke shaft = new BasicStroke(6f); //Creates a new BasicStroke object for the shaft
        g2d.setColor(Color.BLUE); //Sets drawing color to black
    	g2d.setStroke(shaft); //Sets the stroke to draw the shaft
        //g2d.drawRect(500 - (int) (shaftDiameter*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
    	g2d.drawRect(hOffset + (int) ((tcDiameter - shaftDiameter)*in2pix/2), vOffset + (int) (tcLength*ft2pix), (int) (shaftDiameter*in2pix), (int) ((tsLength - tcLength)*ft2pix)); //Draws the shaft below the temporary casing
	}
	
	public static void drawElevation(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
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
	
}