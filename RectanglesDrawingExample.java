import java.awt.*;
import javax.swing.*;

public class RectanglesDrawingExample extends JFrame {
    private int height;
    private int width;
    
 
    public RectanglesDrawingExample() {
        super("3 Stacked Rectangles for Harmon");
 
        getContentPane().setBackground(Color.WHITE);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    void drawRectangles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
	Stroke stroke1 = new BasicStroke(6f);
 	g2d.setColor(Color.RED);
	g2d.setStroke(stroke1);
	g2d.drawRect(40, 45, width, height);

	Stroke stroke2 = new BasicStroke(6f);
 	g2d.setColor(Color.GREEN);
	g2d.setStroke(stroke2);
        g2d.drawRect(40, 50 + height, width, height);

	Stroke stroke3 = new BasicStroke(6f);
 	g2d.setColor(Color.BLUE);
	g2d.setStroke(stroke3);
        g2d.drawRect(40, 55 + 2 * height, width, height);
 
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }
 
    public static void main(String[] args) throws Exception {
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		int h = Integer.parseInt(JOptionPane.showInputDialog("Enter desired height: "));
		int w = Integer.parseInt(JOptionPane.showInputDialog("Enter desired width: "));
		RectanglesDrawingExample rde = new RectanglesDrawingExample();
		rde.height = h;
		rde.width = w;
		rde.setVisible(true);
		
                //new RectanglesDrawingExample().setVisible(true);
            }
        });
    }
}