import java.awt.*;
import javax.swing.*;

public class FillShaft extends JFrame {
	private double vPlaced;
	private double d2Concrete;
	
	public FillShaft(double vPlaced, double d2Concrete) { //constructor
		super("Filling Drilled Shaft"); //Titles the drawing window
        getContentPane().setBackground(Color.WHITE); //Makes window background white
        setSize(Commands.hOffset + (int) (tcDiameter*Commands.in2pix) + 300, Commands.vOffset + (int) (tsLength*Commands.ft2pix) + 20); //Makes drawing window 1000x1000 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finishes running program when the drawing window is closed
        setLocationRelativeTo(null);
        
		this.vPlaced = vPlaced;
		this.d2Concrete = d2Concrete;
	}
	
	void drawDiagram(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //Graphics object used to set colors and draw the actual rectangle
        Commands.drawCasing(g2d);
        Commands.drawShaft(g2d);
        Commands.drawElevation(g2d);
        Commands.drawLabels(g2d);
	}
	
	public void paint(Graphics g) {
        super.paint(g); //Calls JFrame paint method
        drawDiagram(g); //Draws the diagram when called
	}
	
	public static void main(String[] args) throws Exception { //main method
		 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //run method
            	JTextField refEl = new JTextField(0);
                JTextField tcDiam = new JTextField(0);
                JTextField tcLen = new JTextField(0);
                JTextField tsLen = new JTextField(0);
                JTextField sDiam = new JTextField(0); //Creates textboxes for each variable

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
                	FillShaft fs = new FillShaft(Double.parseDouble(refEl.getText()), Double.parseDouble(tcDiam.getText()), Double.parseDouble(tcLen.getText()), Double.parseDouble(tsLen.getText()), Double.parseDouble(sDiam.getText())); //Creates object of class
                	fs.setVisible(true); //Shows the final drawing
                }
                
              //new object created for each iteration of input and diagram output for each fill
            }
        });
	}
}