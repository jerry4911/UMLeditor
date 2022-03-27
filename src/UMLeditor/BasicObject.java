package UMLeditor;

import java.awt.Graphics;

public abstract class BasicObject extends Shape {
	private int offset = 6;
	public final int portNum = 4; //四個連接點

	protected BasicObject() {
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
		int[] xpoint = {(x1+x2)/2-offset/2, x2-offset/2, (x1+x2)/2-offset/2, x1-offset/2};
		int[] ypoint = {y1-offset/2, (y1+y2)/2-offset/2, y2-offset/2, (y1+y2)/2-offset/2};
		
		for(int i = 0; i < ports.length; i++) {
			Port port = new Port(xpoint[i], ypoint[i]);
			ports[i] = port;
		}
	}
	
	public abstract void paint(Graphics g);
	public abstract void paintPort(Graphics g);
}
