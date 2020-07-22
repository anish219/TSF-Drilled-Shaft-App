import java.awt.*;
import javax.swing.*;

public class EmptyShaft extends JFrame {
	
	public EmptyShaft(double refElevation, double tcDiameter, double tcLength, double tsLength, double shaftDiameter) { //constructor
		super("Empty Drilled Shaft"); //Titles the drawing window
        getContentPane().setBackground(Color.WHITE); //Makes window background white
        setSize(Commands.hOffset + (int) (tcDiameter*Commands.in2pix) + 300, Commands.vOffset + (int) (tsLength*Commands.ft2pix) + 20); //Makes drawing window 1000x1000 pixels
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finishes running program when the drawing window is closed
        setLocationRelativeTo(null);
        JButton b = new JButton("Add truck");
        JPanel panel = new JPanel();
        panel.add(b);
        panel.setLayout(null);
        add(panel);
        b.setBounds(Commands.hOffset + (int) (tcDiameter*Commands.in2pix) + 200, Commands.vOffset + (int) (tsLength*Commands.ft2pix) - 40, 80, 20);
        b.addActionListener(new Commands.Action());
        
        
		Commands.refElevation = refElevation; //When DrilledShaft object is created these values have to be filled
		Commands.tcDiameter = tcDiameter;
		Commands.tcLength = tcLength;
		Commands.tsLength = tsLength;
		Commands.shaftDiameter = shaftDiameter;
	}
	
	void drawDiagram(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //Graphics object used to set colors and draw the actual rectangle
        Commands.drawGround(g2d);
        Commands.drawShaft(g2d);
        Commands.drawElevation(g2d);
        Commands.drawLabels(g2d);
	}
	
	public void paint(Graphics g) {
        super.paint(g); //Calls JFrame paint method
        drawDiagram(g); //Draws the diagram when called
	}
} 