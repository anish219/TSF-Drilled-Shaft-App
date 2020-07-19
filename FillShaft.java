import java.awt.*;
import javax.swing.*;

public class FillShaft extends JFrame {
	private double refElevation; //reference elevation (ft.)
	private double tcDiameter; //temporary casing inner diameter (in.)
	private double tcLength; //length of temporary casing (ft.)
	private double tsLength; //total shaft length (ft.)
	private double shaftDiameter; //TEMPORARY VARIABLE, will calculate this later on based on volume
	private double[] truckVolumes;
	private double vCasing;
	private double vShaft;