import java.awt.*;
import javax.swing.*;

public class ShaftExecute{
	public static void main(String[] args) throws Exception { //main method
		 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //run method
            	JTextField refEl = new JTextField(0);
                JTextField tcDiam = new JTextField(0);
                JTextField tcLen = new JTextField(0);
                JTextField tsLen = new JTextField(0);
                JTextField sDiam = new JTextField(0);
                //JTextField tNum = new JTextField(0); //Creates textboxes for each variable

                JPanel myPanel = new JPanel(new GridLayout(6,0)); //Allows placement of textboxes in grid
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
                //myPanel.add(new JLabel("Number of Trucks:"));
               // myPanel.add(tNum);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);//Closes popup when OK is pressed
            	
                if (result == JOptionPane.OK_OPTION) {
                	//Commands.truckNumber = Double.parseDouble(tNum.getText());
                	EmptyShaft es = new EmptyShaft(Double.parseDouble(refEl.getText()), Double.parseDouble(tcDiam.getText()), Double.parseDouble(tcLen.getText()), Double.parseDouble(tsLen.getText()), Double.parseDouble(sDiam.getText())); //Creates object of class
                	//for(int i = 0; i < Commands.truckNumber; i++) {
                		FillShaft fs = new FillShaft();
                    	fs.setVisible(true);
                	//}
                }
            }
        });
	}
}
