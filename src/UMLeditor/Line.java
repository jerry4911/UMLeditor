package UMLeditor;

import java.awt.Graphics;

public abstract class Line {
	private final int portNum = 2; //兩個連接點
	protected boolean selected = false;
	protected Port[] ports;
	BasicObject startObj = null;
	BasicObject endObj = null;
	
	protected Line(Port start, Port end) {
		ports = new Port[portNum];
		ports[0] = start;
		ports[1] = end;
	}
	
	public Port getStart() {
		return ports[0];
	}
	
	public Port getEnd() {
		return ports[1];
	}
	
	public void setSelected() {
		selected = true;
	}
	
	public void setUnselected() {
		selected = false;
	}
	
	public void paintPort(Graphics g) {
		for (int i = 0; i < ports.length; i++) {
			ports[i].paint(g);
        }
	}
	
	public abstract void paint(Graphics g);
	
}
