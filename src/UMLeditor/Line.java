package UMLeditor;

import java.awt.Graphics;

public abstract class Line extends Shape {
	public final int portNum = 2; //兩個連接點
	BasicObject startObj = null;
	BasicObject endObj = null;
	
	protected Line() {
		initializePorts();
	}
	
	private void initializePorts() {
        ports = new Port[portNum];
        for (int i = 0; i < ports.length; i++)
        {
            ports[i] = new Port();
        }
    }
	
	protected void createPorts() {
		int[] xpoint = {x1, x2};
		int[] ypoint = {y1, y2};

		for(int i = 0; i < ports.length; i++) {
			Port port = new Port(xpoint[i], ypoint[i]);
			ports[i] = port;
		}
	}
	
	public abstract void paint(Graphics g);
	
}
