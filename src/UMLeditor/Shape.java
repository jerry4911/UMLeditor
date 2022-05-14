package UMLeditor;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape { //包含圖案以及線條
	Port start = null;
	final int portNum = 4; //兩個連接點
	int depth = 0;
	protected Port[] ports;
	protected int height, width;
	boolean selected = false;
	public String name = "";
	
	protected Shape(Port start, int width, int height) {
		this.start = start;
		ports = new Port[portNum];
		ports[0] = new Port(this.start.x, start.y);
		ports[1] = new Port(this.start.x+width, start.y);
		ports[2] = new Port(this.start.x, start.y+height);
		ports[3] = new Port(this.start.x+width, start.y+height);
		this.height = height;
		this.width = width;
		depth = 0;
	}
	
	public void addXYOffset(int offset_x, int offset_y) {
		for(int i=0; i<ports.length; i++) {
			int x = ports[i].x;
			int y = ports[i].y;
			ports[i].setPos(x+offset_x, y+offset_y);
		}
		start.setPos(start.x+offset_x, start.y+offset_y);
	}
	
	public void paintPort(Graphics g) {
		for (int i = 0; i < ports.length; i++) {
			ports[i].paint(g);
        }
	}
	
	public void setSelected() {
		selected = true;
	}
	
	public void setUnselected() {
		selected = false;
	}
	
	public abstract void paint(Graphics g);
	
}
