package UMLeditor;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape { //包含圖案以及線條
	String name = "";
	protected int x1, y1, x2, y2, height, width;
	boolean selected = false;
	protected Port[] ports;
	
	public abstract void paint(Graphics g);
	public abstract void paintPort(Graphics g);
	
	protected Shape() {
		name = "";
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		height = 0;
		width = 0;
	}
	
	public void addOffset(int offset_x, int offset_y) {
		if (ports==null || ports.length==0) return;
		for(int i=0; i<ports.length; i++) {
			ports[i].x += offset_x;
			ports[i].y += offset_y;
		}
	}
}
