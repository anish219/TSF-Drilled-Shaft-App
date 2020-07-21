import java.awt.*;
import javax.swing.*;

public class FillShaft extends JFrame {
	private double vPlaced;
	private double d2Concrete;
	
	public FillShaft() { //constructor
		super("Filling Drilled Shaft"); //Titles the drawing window
        getContentPane().setBackground(Color.WHITE); //Makes window background white
        setSize(Commands.hOffset + (int) (Commands.tcDiameter*Commands.in2pix) + 300, Commands.vOffset + (int) (Commands.tsLength*Commands.ft2pix) + 20); //Makes drawing window 1000x1000 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finishes running program when the drawing window is closed
        setLocationRelativeTo(null);
        
        JTextField volume = new JTextField(0);
        JTextField depth = new JTextField(0);
        JPanel myPanel = new JPanel(new GridLayout(2,0));
        myPanel.add(new JLabel("Volume Placed (cy):")); //Displays prompts in panel
        myPanel.add(volume); //Adds corresponding textbox
        myPanel.add(new JLabel("Depth to Concrete (ft):"));
        myPanel.add(depth);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);//Closes popup when OK is pressed
   	
        if (result == JOptionPane.OK_OPTION) {
		vPlaced = Double.parseDouble(volume.getText());
		//Commands.truckVolumes.add(new Double(vPlaced));
		Commands.currentDepth = Double.parseDouble(depth.getText());
        }
	}
	
	void drawDiagram(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //Graphics object used to set colors and draw the actual rectangle
        Commands.drawCasing(g2d);
        Commands.drawShaft(g2d);
        Commands.drawElevation(g2d);
        Commands.drawLabels(g2d);
        Commands.fillShaft(g2d, Commands.currentDepth);
	}
	
	public void paint(Graphics g) {
        super.paint(g); //Calls JFrame paint method
        drawDiagram(g); //Draws the diagram when called
	}
                
              //new object created for each iteration of input and diagram output for each fill
}